package com.example.gadgetlist.ui.item

import android.view.Menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.AppViewModelProvider
import com.example.gadgetlist.ui.theme.GadgetListTheme

@Composable
fun GoodCategoryEditScreen(
    navigateBack: () -> Unit,
    NavigateToHome: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: GoodEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
        var state by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(Modifier.selectableGroup()) {
        RadioButton(selected = state, onClick = { state = true })
        RadioButton(selected = !state, onClick = { state = false })
    }

        Image(
            painter = painterResource(R.drawable.iphone_1),
            contentDescription = ""
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Button(onClick = navigateBack,) { Text("Cancel", color = Color.Red)}
            Button(onClick = NavigateToHome,) { Text("Save", color = Color.Blue)}
        }
    }
}



@Preview
@Composable
fun GoodCategoryEditScreenPreview(){
    GadgetListTheme {
        GoodCategoryEditScreen(
            navigateBack = {},
            NavigateToHome = {}
        )
    }

}