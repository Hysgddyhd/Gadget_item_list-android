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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.icons.sharp.Person4
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun PersonDetailScreen (
    modifier: Modifier = Modifier,
    personViewModel: PersonViewModel =viewModel(factory = AppViewModelProvider.Factory),
    toProfile:()->Unit,
){
    val uiState = personViewModel.personUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isEditEnable=remember { mutableStateOf(false)}
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isEditEnable.value = !isEditEnable.value },

            ){
                if (!isEditEnable.value)
                {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.item_edit_title)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.EditOff,
                        contentDescription = stringResource(R.string.item_edit_title)
                    )
                }
            }
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
            OutlinedTextField(
                value = uiState.value.name,
                onValueChange = personViewModel::updateName,
                readOnly = !isEditEnable.value,
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
                value = uiState.value.phone.toString(),
                onValueChange = personViewModel::updatePhone,
                readOnly = !isEditEnable.value,
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
                value = uiState.value.address,
                onValueChange = personViewModel::updateAddress,
                readOnly = !isEditEnable.value,
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
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ){
                Text(
                    "Sex: "+if (uiState.value.sex){
                        "Man"
                    } else {
                        "Woman"
                    },
                )
                Spacer(Modifier.widthIn(min=200.dp))
                Switch(
                    checked = uiState.value.sex,
                    enabled = isEditEnable.value,
                    onCheckedChange = personViewModel::updateSex,

                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                enabled = isEditEnable.value,
                onClick = {
                    coroutineScope.launch {
                        if (personViewModel.saveUserDetail()){
                            snackbarHostState.showSnackbar("Detail saved")
                            isEditEnable.value=false
                        }else{
                            snackbarHostState.showSnackbar("Incomplete info")
                        }
                    }
                },
            ) { Text("Save Detail") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = toProfile,
            ) { Text("SignOut") }

        }


    }
}