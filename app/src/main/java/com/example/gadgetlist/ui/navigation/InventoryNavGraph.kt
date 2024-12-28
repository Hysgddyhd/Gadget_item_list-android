package com.example.gadgetlist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gadgetlist.ui.search.SearchResult
import com.example.gadgetlist.ui.home.HomeScreen
import com.example.gadgetlist.ui.item.GoodEntryScreen

enum class ShopScreen {
    lobby,
    addIten,
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
                    navController.navigate(ShopScreen.addIten.name)
                },
                navigateToItemUpdate = {
                    // navController.navigate("${ItemDetailsDestination.route}/${it}")
                },
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
                navigateToItemUpdate = {},
                onNavigateUp = {navController.navigateUp()}

            )
        }
        composable(route=ShopScreen.addIten.name){
                GoodEntryScreen(
                    navigateBack = { navController.navigateUp()},
                    onNavigateUp = { navController.navigate(ShopScreen.lobby.name)},
                    canNavigateBack = navController.previousBackStackEntry != null,

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