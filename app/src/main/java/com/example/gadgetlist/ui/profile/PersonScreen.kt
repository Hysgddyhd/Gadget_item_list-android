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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.icons.sharp.Person4
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PersonProfileScreen(
    modifier: Modifier = Modifier,
    isLogOut:() -> Unit,
    personViewModel: PersonViewModel =viewModel(factory = AppViewModelProvider.Factory),
    toDetail:()->Unit,
) {
    val uiState = personViewModel.personUiState
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },

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
                painter = painterResource(R.drawable.person_1),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.widthIn(max = 150.dp),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = uiState.value.email,
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue

                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = uiState.value.name,
                onValueChange = {},
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


            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = toDetail,
            ) { Text("User Detail") }

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