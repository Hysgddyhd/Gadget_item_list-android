package com.example.gadgetlist.ui.login



import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.ui.AppViewModelProvider
import kotlinx.coroutines.launch


@Composable
fun PersonSignUpScreen(
    toSign:()->Unit,
    viewModel: PersonProfileViewModel =viewModel(factory = AppViewModelProvider.Factory),
) {
    var confirmPass by remember { mutableStateOf("") }
    //deteled address and
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val conPasswordErrorState = remember { mutableStateOf(false) }
    val nameErrorState = remember { mutableStateOf(false) }

    val animate = remember { mutableStateOf(true) }

    val personProfileUiState = viewModel.personProfileUiState
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
) { contentPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(0.7f)) {
                animate.value = !animate.value

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Register Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Create an account to continue\nwith your shopping.",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        //name field
        OutlinedTextField(
            value = personProfileUiState.name,
            isError = nameErrorState.value,
            onValueChange = { viewModel.updateName(it.trim()) },
            trailingIcon = { Icons.Filled.Person3 },
            label = { Text("Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            visualTransformation = VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        //email field
        OutlinedTextField(
            value = personProfileUiState.email,
            isError = emailErrorState.value,
            onValueChange = { viewModel.updateEmail(it.trim()) },
            trailingIcon = { Icons.Filled.Email },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            visualTransformation = VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        //password field
        OutlinedTextField(
            value = personProfileUiState.password,
            isError = passwordErrorState.value,
            onValueChange = { viewModel.updatePassword(it.trim()) },
            trailingIcon = { Icons.Filled.Password },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        //confirm password field
        OutlinedTextField(
            value = confirmPass,
            isError = conPasswordErrorState.value,
            onValueChange = { confirmPass = it.trim() },
            trailingIcon = { Icons.Filled.Password },
            label = { Text("Confirm Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = VisualTransformation.None,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(10.dp))

        when {
            nameErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid name.")
            }

            emailErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid email address.")
            }

            passwordErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid password.")
            }

            conPasswordErrorState.value -> {
                ErrorSuggestion(message = "Confirm Password miss matched.")
            }
        }

        val isNameValid =
            personProfileUiState.name.isEmpty() || personProfileUiState.name.length < 3
        //email pattern
        val pattern = Patterns.EMAIL_ADDRESS
        val isEmailValid = pattern.matcher(personProfileUiState.email).matches()
        val isPassValid = personProfileUiState.password.length >= 6
        val conPassMatch = personProfileUiState.password == confirmPass
        nameErrorState.value = isNameValid
        emailErrorState.value = !isEmailValid
        passwordErrorState.value = !isPassValid
        conPasswordErrorState.value = !conPassMatch

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Have an Account? ")
            Text(
                text = "Sign In",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .clickable {
                        toSign()
                    }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (!nameErrorState.value && !emailErrorState.value
                    && !passwordErrorState.value && !conPasswordErrorState.value
                ) {
                    viewModel.signUp(toSign)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Created User")
                        viewModel.saveUser()

                    }

                } else {

                }

            },
        ) {
            Text("SignUp")
        }
    }
    }
}



@Composable
fun ErrorSuggestion(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icons.Filled.Error
        Text(text = message, fontSize = 14.sp)
    }

}
