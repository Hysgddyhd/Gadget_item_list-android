package com.example.gadgetlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.ui.search.SearchResult
import com.example.gadgetlist.ui.home.HomeScreen
import com.example.gadgetlist.ui.item.GoodEditDestination
import com.example.gadgetlist.ui.item.GoodEditScreen
import com.example.gadgetlist.ui.item.GoodEntryScreen
import com.example.gadgetlist.ui.item.GoodTradeDestination
import com.example.gadgetlist.ui.item.GoodTradeScreen

enum class ShopScreen {
    lobby,
    addItem,
    search,
    profile,
    menu,
}

@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ShopScreen.lobby.name,
        modifier = modifier
    ) {
        composable(route = ShopScreen.lobby.name) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(ShopScreen.addItem.name)
                },
                navigateToItemUpdate = {
                    navController.navigate("${GoodTradeDestination.route}/${it}")                },
                navigateToSearchResult = {
                    navController.navigate(ShopScreen.search.name)
                },
                modifier = modifier,
            )
        }
        composable(route=ShopScreen.search.name){
            SearchResult(
                navigateToItemEntry = {},
                modifier = modifier,
                navigateToItemUpdate = {
                },
                onNavigateUp = {navController.navigateUp()}

            )
        }
        composable(route=ShopScreen.addItem.name){
                GoodEntryScreen(
                    navigateBack = { navController.navigateUp()},
                    onNavigateUp = { navController.navigate(ShopScreen.lobby.name)},
                    canNavigateBack = navController.previousBackStackEntry != null,

                )
        }
        composable(
            route = GoodEditDestination.routeWithArgs,
            arguments = listOf(navArgument(GoodEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            GoodEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(route= GoodTradeDestination.routeWithArgs,
            arguments = listOf(navArgument(GoodEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ){
            GoodTradeScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToEditEntry = { navController.navigate("${GoodEditDestination.route}/${it}") }
            )
        }
        composable(route = ShopScreen.profile.name){}
        composable(route = ShopScreen.menu.name){}

    }
}

@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    InventoryNavHost(navController = navController)
}
