package com.example.gadgetlist.ui.profile

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gadgetlist.data.GoodsRepository
import com.example.gadgetlist.data.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            initialValue = PersonUiState(
                auth = auth,
                email = auth.currentUser?.email ?: "?@?.com",
                )
        )
    init {
        Log.d("room",username.value)
    }


    fun updateName(name: String) {
        username.value = name
        Log.d("room",username.value)
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

    suspend fun resetProfile(){
        val userLatest: User =goodsRepository.getUserById(personUiState.value.auth.uid ?:"").first()
        Log.d("room",userLatest.name+userLatest.address)
        username.value=userLatest.name
        Log.d("room",username.value)
        userAddress.value=userLatest.address
        userSex.value= userLatest.sex
        userPhone.value=userLatest.phone
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