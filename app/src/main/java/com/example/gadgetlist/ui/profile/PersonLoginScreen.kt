package com.example.gadgetlist.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider


@Composable
fun PersonLoginScreen(
    modifier: Modifier = Modifier,
    isLoginSuccess:() -> Unit,
    personProfileViewModel: PersonProfileViewModel =viewModel(factory = AppViewModelProvider.Factory)
){

    // Creating firebaseAuth object

    val personProfileUiState=personProfileViewModel.personProfileUiState

    Column(
        modifier= modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = ""
        )

        TextField(
            value = personProfileUiState.email,
            onValueChange = {
                personProfileViewModel.updateEmail(it)
            },
            label = {
                Text("Email")
            }
        )
        TextField(
            value = personProfileUiState.password,
            onValueChange = {
                personProfileViewModel.updatePassword(it)
            },
            label = {
                Text("Password")
            }
        )

        Column(
             modifier= modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(onClick = {
                    personProfileViewModel.login()

                 },
                ) {
                Text("Login")
            }
            Button(onClick = {personProfileViewModel.signUp()},) { Text("SignUp")}
            Button(onClick = {personProfileViewModel.trial_anony()},) { Text("Try anonymous mode ")}

        }
    }
    if(personProfileUiState.isSuccess)
        isLoginSuccess()

}

