package com.example.gadgetlist.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.R
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.ui.AppViewModelProvider
import com.example.gadgetlist.ui.GadgetTopAppBar
import com.example.gadgetlist.ui.GoodDetailList
import com.example.gadgetlist.ui.navigation.NavigationDestination
import com.example.gadgetlist.ui.theme.GadgetListTheme
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon



object GoodTradeDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.sell
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoodTradeScreen (
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToEditEntry: (Int)->Unit,
    canNavigateBack: Boolean = true,
    viewModel: GoodTradeViewModel = viewModel(factory = AppViewModelProvider.Factory),

    ){
    //check item status
        viewModel.checkItemStatus()
        val coroutine = rememberCoroutineScope()
    Scaffold(
        topBar = {
            GadgetTopAppBar(
                title = stringResource(GoodEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToEditEntry(viewModel.goodUiState.goodDetails.id)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.item_edit_title)
                )
            }
        },
        modifier = Modifier
    ) { innerPadding ->
        GoodTradeBody(
            goodUiState = viewModel.goodUiState,
            onItemValueChange = viewModel::updateUiState,
            onSellClick = {
                coroutine.launch {
                    viewModel.sellItem()
                }
            },
            isButtonEnable = viewModel.isItemAvailable,
            onDelete = {

            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun GoodTradeBody(
    goodUiState: GoodUiState,
    onItemValueChange: (GoodDetails) -> Unit,
    onSellClick: () -> Unit,
    onDelete: ()-> Unit,
    isButtonEnable:Boolean,
    modifier: Modifier = Modifier
){

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
        GoodDetailList(
            goodDetails = goodUiState.goodDetails,
            onValueChange = onItemValueChange,
            enabled = false
        )
        Button(
            onClick = onSellClick,
            enabled = isButtonEnable,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.sell))
        }

    }

}

@Preview
@Composable
fun GoodTradeScreenPreview(

){
    GadgetListTheme {
        GoodTradeScreen(
            navigateBack = {},
            onNavigateUp ={},
            navigateToEditEntry = {},
            canNavigateBack = false,
            viewModel= viewModel(factory = AppViewModelProvider.Factory),
        )
    }
}