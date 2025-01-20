package com.example.gadgetlist.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.icons.sharp.Person4
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.data.User
import com.example.gadgetlist.ui.AppViewModelProvider
import com.example.gadgetlist.ui.login.PersonProfileViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PersonProfileScreen(
    modifier: Modifier = Modifier,
    isLogOut:() -> Unit,
    personViewModel: PersonViewModel =viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = personViewModel.personUiState
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
    floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Show snackbar") },
            icon = { Icon(Icons.Filled.Image, contentDescription = "") },
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Snackbar")
                }
            }
        )
    }
) { contentPadding ->
        // Screen content

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text("Profile")
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = ""
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = uiState.value.user.email,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = uiState.value.user.name,
                onValueChange = personViewModel::changeName,
                label = { Text(text = "Name") },
                shape = RoundedCornerShape(1.dp),
                trailingIcon = {
                    Icons.Sharp.Person4
                },
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onNext = {}
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.value.user.phone.toString(),
                onValueChange = personViewModel::changePhone,
                label = { Text(text = "Phone Number") },
                shape = RoundedCornerShape(1.dp),
                trailingIcon = {
                    Icons.Sharp.Phone
                },
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onNext = {}
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.value.user.address,
                onValueChange = personViewModel::changeAddress,
                label = { Text(text = "Address") },
                shape = RoundedCornerShape(1.dp),
                trailingIcon = {
                    Icons.Sharp.LocationOn
                },
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onNext = {}
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        personViewModel.updateUser()
                        snackbarHostState.showSnackbar("User updated")
                    }
                },
            ) { Text("Update Profile") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    personViewModel.logOut()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Signed out")
                    }
                    isLogOut()
                },
            ) { Text("SignOut") }

        }


    }
}