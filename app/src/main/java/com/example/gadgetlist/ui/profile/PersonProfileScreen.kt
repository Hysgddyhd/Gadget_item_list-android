package com.example.gadgetlist.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gadgetlist.R

@Composable
fun PersonProfileScreen(
    modifier: Modifier = Modifier,
){
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

        Text(
            text ="Name: "+""
        )
        Text(
            text ="Name: "+""
        )
        Column(
             modifier= modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(onClick = {},) { Text("Login")}
            Button(onClick = {},) { Text("SignUp")}

        }
    }

}