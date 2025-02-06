package com.example.gadgetlist.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gadgetlist.data.GoodsRepository
import com.example.gadgetlist.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class PersonViewModel(
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository,
    auth: FirebaseAuth
):ViewModel() {
    val user = User(uid = auth.uid ?: "null")
    private val username = MutableStateFlow("")
    private val userAddress = MutableStateFlow("")
    private val userPhone = MutableStateFlow(0)
    private val userSex = MutableStateFlow(false)
    val personUiState: StateFlow<PersonUiState> = combine(
        username,
        userAddress,
        userPhone,
        userSex,
    ) { username, userAddress, userPhone, userSex ->
        PersonUiState(
            auth = auth,
            name = username,
            email = auth.currentUser?.email ?: "?@?.com",
            address = userAddress,
            phone = userPhone,
            sex = userSex
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PersonUiState(auth = auth)
        )

    fun updateName(name: String) {
        username.value = name
    }

    fun updateAddress(address: String) {
        userAddress.value = address
    }

    fun updateSex(sex: Boolean) {
        userSex.value = sex
    }

    fun updatePhone(phone: String) {
        val num: Int = phone.toIntOrNull() ?: 0
        userPhone.value = num
    }

    suspend fun saveUserDetail(): Boolean {
        if (username.value != "" && userPhone.value != 0 && userAddress.value != "") {
            goodsRepository.createUser(User(
                uid=personUiState.value.auth.uid.toString(),
                name = username.value,
                email=personUiState.value.email,
                address = userAddress.value,
                phone = userPhone.value,

            ))
            return true
        }
            return false

    }

    fun logOut(){
        personUiState.value.auth.signOut()
    }
}
data class PersonUiState(
    val auth: FirebaseAuth,
    var name:String="",
    var email:String="?",
    var address:String="",
    var sex: Boolean=false,
    var phone:Int=0,
)