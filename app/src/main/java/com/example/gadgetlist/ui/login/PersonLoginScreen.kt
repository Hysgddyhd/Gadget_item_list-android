package com.example.gadgetlist.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun PersonLoginScreen(
    modifier: Modifier = Modifier,
    isLoginSuccess:() -> Unit,
    toSignUp:()->Unit,
    personProfileViewModel: PersonProfileViewModel =viewModel(factory = AppViewModelProvider.Factory)
){

    // Creating firebaseAuth object

    val personProfileUiState=personProfileViewModel.personProfileUiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
            },
        )
    {   contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(R.drawable.person),
                contentScale = ContentScale.Fit,
                contentDescription = ""
            )
            Spacer(Modifier.height(20.dp))
            TextField(
                value = personProfileUiState.email,
                onValueChange = {
                    personProfileViewModel.updateEmail(it)
                },
                singleLine = true,
                label = {
                    Text("Email")
                }
            )
            TextField(
                value = personProfileUiState.password,
                onValueChange = {
                    personProfileViewModel.updatePassword(it)
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text("Password")
                }
            )

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                        onClick = {
                            var success:Boolean=false
                            coroutineScope.launch {
                                launch { success= personProfileViewModel.login() }
                                if (success)
                                    snackbarHostState.showSnackbar("Login Successfully")
                                else
                                    snackbarHostState.showSnackbar("invaild email or password")
                            }
                    },
                ) {
                    Text("Login")
                }

                Column(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Don't have an account? ")
                        Text(
                            text = "Sign Up",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            modifier = Modifier.clickable {
                                toSignUp()
                            }
                        )
                    }
                }
                Button(onClick = { personProfileViewModel.trial_anony() }) { Text("Try anonymous mode ") }

            }
        }
    }
    if(personProfileUiState.auth.currentUser!=null)
        isLoginSuccess()

}

