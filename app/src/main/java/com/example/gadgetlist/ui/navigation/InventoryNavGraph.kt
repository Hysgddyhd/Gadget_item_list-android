package com.example.gadgetlist.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gadgetlist.R
import com.example.gadgetlist.ui.GadgetTopAppBar
import com.example.gadgetlist.ui.home.HomeScreen
import com.example.gadgetlist.ui.item.GoodEditDestination
import com.example.gadgetlist.ui.item.GoodEditScreen
import com.example.gadgetlist.ui.item.GoodEntryScreen
import com.example.gadgetlist.ui.item.GoodTradeDestination
import com.example.gadgetlist.ui.item.GoodTradeScreen
import com.example.gadgetlist.ui.profile.PersonLoginScreen
import com.example.gadgetlist.ui.profile.PersonProfileScreen
import com.example.gadgetlist.ui.search.SearchResult

enum class ShopScreen {
    lobby,
    addItem,
    search,
    profile,
    login,
    menu,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
//use currentScreen here will cause error , because some screens don't have name
    val currentScreen = "Gadget Store"
    Scaffold(
        topBar = {
            GadgetTopAppBar(
                title = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                modifier = modifier,
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                navigateUp = {
                    navController.navigateUp()
                }
            )
                 },
        bottomBar = {
            NavigationBar(
                tonalElevation = 5.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.popBackStack(
                        ShopScreen.lobby.name,
                        inclusive = false,
                        )
                        navController.navigate(ShopScreen.lobby.name)

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.item_edit_title)
                        )
                    },
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.popBackStack(
                        ShopScreen.login.name,
                        inclusive = false
                        )
                        navController.navigate(ShopScreen.login.name)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.item_edit_title)
                        )
                    },
                )
            }
        }

    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ShopScreen.lobby.name,
            modifier = modifier
                .padding(innerPadding)
        ) {
            composable(route = ShopScreen.lobby.name) {
                HomeScreen(
                    navigateToItemEntry = {
                        navController.navigate(ShopScreen.addItem.name)
                    },
                    navigateToItemUpdate = {
                        navController.navigate("${GoodTradeDestination.route}/${it}")
                    },
                    navigateToSearchResult = {
                        navController.navigate(ShopScreen.search.name)
                    },
                    modifier = modifier,
                )
            }
            composable(route = ShopScreen.search.name) {
                SearchResult(
                    navigateToItemEntry = {},
                    modifier = modifier,
                    navigateToItemUpdate = {
                    },
                    onNavigateUp = { navController.navigateUp() }

                )
            }
            composable(route = ShopScreen.addItem.name) {
                GoodEntryScreen(
                    navigateBack = { navController.navigateUp() },
                    onNavigateUp = { navController.navigate(ShopScreen.lobby.name) },
                    canNavigateBack = navController.previousBackStackEntry != null,

                    )
            }
            composable(
                route = GoodEditDestination.routeWithArgs,
                arguments = listOf(navArgument(GoodEditDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                GoodEditScreen(navigateBack = {
                    navController.popBackStack(
                        ShopScreen.lobby.name,
                        inclusive = false
                    )
                },
                    onNavigateUp = { navController.navigateUp() })
            }
            composable(
                route = GoodTradeDestination.routeWithArgs,
                arguments = listOf(navArgument(GoodEditDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                GoodTradeScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() },
                    navigateToEditEntry = { navController.navigate("${GoodEditDestination.route}/${it}") }
                )
            }
            composable(route = ShopScreen.login.name) {
                PersonLoginScreen(
                    modifier=modifier,
                    isLoginSuccess = {navController.navigate(ShopScreen.profile.name)}
                )
            }
            composable(route = ShopScreen.profile.name) {
                PersonProfileScreen(
                    modifier=modifier,
                    isLogOut = {
                        navController.navigate(ShopScreen.login.name)
                    },

                )
            }
            composable(route = ShopScreen.menu.name) {}

        }
    }
}



@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    InventoryNavHost(navController = navController)
}
