package com.example.gadgetlist.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider

@Composable
fun PersonProfileScreen(
    modifier: Modifier = Modifier,
    isLogOut:() -> Unit,
    personProfileViewModel: PersonProfileViewModel =viewModel(factory = AppViewModelProvider.Factory)
) {
    val personProfileUiState = personProfileViewModel.personProfileUiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = ""
        )

         Text(
            text = personProfileUiState.auth.currentUser?.email.toString(),


        )

        Button(onClick = {
            personProfileViewModel.logOut()
                    isLogOut()
                         },) { Text("SignOut")}

    }
}