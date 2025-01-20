package com.example.gadgetlist.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gadgetlist.R
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.data.GoodDao
import com.example.gadgetlist.ui.AppViewModelProvider
import com.example.gadgetlist.ui.GadgetList
import com.example.gadgetlist.ui.TopRow
import com.example.gadgetlist.ui.item.GoodDetails
import com.example.gadgetlist.ui.theme.GadgetListTheme




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateToSearchResult: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var input by remember { mutableStateOf("") }

    GadgetListTheme {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(24.dp))
            //Title
            Text(
                text = "Welcome to Lab 6",
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
            //searchbar

            //recommendation
                Text(
                    text = "Database:",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            //

            Scaffold(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                    itemList = homeUiState.itemList,
                    onItemClick = navigateToItemUpdate,
                    contentPadding = innerPadding,
                    modifier = modifier
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun itemSearchBar(
    input:String,
    onClickAction : () -> Unit,
    onValueChange : (String) ->Unit,
    modifier: Modifier=Modifier
){
  Row(
      modifier = modifier
          .padding(8.dp)
          .fillMaxWidth()
          .height(48.dp),
      horizontalArrangement = Arrangement.Center,


  ) {
      OutlinedTextField(
            onValueChange =  onValueChange ,
            textStyle = TextStyle(
                fontSize = 18.sp,

            ),
            singleLine = true,
            value = input,
          enabled = true,
          modifier  =Modifier.
                height(48.dp),
            placeholder = {
             Text("Input item name,",
                 fontSize = 12.sp
             )
          },


    )
      OutlinedIconButton(
          onClick = onClickAction,

      ) {
          Image(
              painter = painterResource(R.drawable.search_icon),
              contentDescription = null,
              contentScale = ContentScale.Fit
          )
      }
  }

}


@Preview
@Composable
fun HomeScreenPreview(){
    GadgetListTheme {
        HomeScreen(
            navigateToItemEntry = {},
            navigateToItemUpdate = {},
            modifier = Modifier,
            viewModel = viewModel(factory = AppViewModelProvider.Factory),
            navigateToSearchResult = {}
        )
    }

}