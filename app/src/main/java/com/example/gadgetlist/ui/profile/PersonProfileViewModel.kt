package com.example.gadgetlist.ui.profile


import androidx.lifecycle.SavedStateHandle
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential


class PersonProfileViewModel(
    savedStateHandle: SavedStateHandle,
    auth: FirebaseAuth
    ): ViewModel(){
var personProfileUiState by mutableStateOf(  PersonProfileUiState(
    auth=auth,
))
        private set
    fun updateEmail(email: String) {
        personProfileUiState=personProfileUiState .copy(
            email = email
        )
    }
    fun updatePassword(pass: String) {
        personProfileUiState=personProfileUiState .copy(
            password = pass
        )
    }
    fun trial_anony(){
        personProfileUiState.auth.signInAnonymously()
    }
    fun signUp(){
        personProfileUiState.auth.createUserWithEmailAndPassword(personProfileUiState.email, personProfileUiState.password)
        .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User is signed in
                        val user = personProfileUiState.auth.currentUser
                        Log.d("Login","Signup Success")


                        // ...
                    } else {
                        // User is not signed in
                        val error = task.exception
                        Log.d("Login","Signup failed")

                        // ...
                    }
                }
    }
    fun login() {
        if (personProfileUiState.email.equals("") || personProfileUiState.password.equals("")) {

        } else {
            personProfileUiState.auth.signInWithEmailAndPassword(
                personProfileUiState.email,
                personProfileUiState.password
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // User is signed in
                        val user = personProfileUiState.auth.currentUser
                        Log.d("Login", "Login success")
                        // ...
                    } else {
                        // User is not signed in
                        val error = task.exception
                        Log.d("Login", "Lo gin failed")

                        // ...
                    }
                }
        }
    }

    fun logOut(){
        personProfileUiState.auth.signOut()
    }

    // Password reset
        fun resetPassword(email: String) {
           personProfileUiState.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password reset email sent
                        val user =personProfileUiState.auth.currentUser
                        // ...
                    } else {
                        // Password reset failed
                        val error = task.exception
                        // ...
                    }
                }
        }

        // Account linking
            fun linkAccount(credential: AuthCredential) {
                personProfileUiState.auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Account linked
                            val user = personProfileUiState.auth.currentUser
                            // ...
                        } else {
                            // Account linking failed
                            val error = task.exception
                            // ...
                        }
                    }
            }
}

data class PersonProfileUiState(
    val auth: FirebaseAuth ,
    val email: String="",
    val password: String="",
    val reenter_pass:String ="",

)

