package com.example.gadgetlist.ui.search

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gadgetlist.data.sample
import com.example.gadgetlist.ui.AppViewModelProvider
import com.example.gadgetlist.ui.GadgetList
import com.example.gadgetlist.ui.theme.GadgetListTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.res.stringResource
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.GadgetTopAppBar
import com.example.gadgetlist.ui.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    onNavigateUp : ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)


    ) {

    val homeUiState by viewModel.homeUiState.collectAsState()

    val goods = sample
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GadgetTopAppBar(
                title = "Add New Item",
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }

        },

    ) { innerPadding ->
        GadgetList(
            itemList = goods,
            onItemClick = navigateToItemUpdate,
            contentPadding = innerPadding,
            modifier = modifier
        )
    }
}
@Preview
@Composable
fun SearchResultPreview(){
    GadgetListTheme {
        SearchResult(

            navigateToItemEntry = {},
            modifier = Modifier,
            navigateToItemUpdate = {},
            onNavigateUp = {}

        )
    }
}