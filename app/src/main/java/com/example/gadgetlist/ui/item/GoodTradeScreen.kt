package com.example.gadgetlist.ui.item

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight


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
    val uiState = viewModel.goodUiState.collectAsState()
    //check item status

        val coroutine = rememberCoroutineScope()
    Scaffold(
        topBar = {
            GadgetTopAppBar(
                title = stringResource(GoodTradeDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navigateToEditEntry(viewModel.goodUiState.value.goodDetails.id)},
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
            goodUiState = uiState.value,
            onSellClick = {
                    viewModel.sellItem()
            },
            isItemSellable = !uiState.value.outOfStock,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()),

        )
    }
}

@Composable
fun GoodTradeBody(
    isItemSellable:Boolean,
    goodUiState: GoodTradeUiState,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
        
         GoodDetailsCard(
            good = goodUiState.goodDetails.toGood(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = onSellClick,
            enabled = isItemSellable,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.sell))
        }

    }

}


@Composable
fun GoodDetailsCard(
    good: Good, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ItemDetailsRow(
                labelResID = R.string.good,
                itemDetail = good.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.quantity_in_stock,
                itemDetail = good.quantity.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.price,
                itemDetail = good.formatedPrice(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
        }

    }
}

@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
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