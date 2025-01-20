package com.example.gadgetlist.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gadgetlist.data.GoodsRepository
import com.example.gadgetlist.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PersonViewModel(
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
    auth: FirebaseAuth
):ViewModel() {
    val user=User(uid = auth.uid?:"null")
    val personUiState: StateFlow<PersonUiState> =
            goodsRepository.getUserById(auth.currentUser?.uid?:"null")
                .filterNotNull()
                .map {
                    PersonUiState(auth=auth, user = it)
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PersonUiState(auth=auth)
                )


    fun changeName(name:String){
        personUiState.value.user=personUiState.value.user.copy(name=name)
    }
    fun changeAddress(address:String){
        personUiState.value.user=personUiState.value.user.copy(address=address)
    }
    fun changePhone(phone:String){
        val a=phone.toIntOrNull()?:1234567
        personUiState.value.user=personUiState.value.user.copy(phone=a)
    }

    fun logOut(){
        personUiState.value.auth.signOut()
    }
    suspend fun updateUser(){
        goodsRepository.updateUser(personUiState.value.user)
    }
}
data class PersonUiState(
    val auth: FirebaseAuth,
    var user: User=User()
)