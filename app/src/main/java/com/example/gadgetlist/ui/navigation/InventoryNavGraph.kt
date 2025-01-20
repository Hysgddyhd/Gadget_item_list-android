package com.example.gadgetlist.ui.navigation

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gadgetlist.ui.GadgetTopAppBar
import com.example.gadgetlist.ui.home.HomeScreen
import com.example.gadgetlist.ui.item.GoodEditDestination
import com.example.gadgetlist.ui.item.GoodEditScreen
import com.example.gadgetlist.ui.item.GoodEntryScreen
import com.example.gadgetlist.ui.item.GoodTradeDestination
import com.example.gadgetlist.ui.item.GoodTradeScreen
import com.example.gadgetlist.ui.login.PersonLoginScreen
import com.example.gadgetlist.ui.profile.PersonProfileScreen
import com.example.gadgetlist.ui.login.PersonSignUpScreen
import com.example.gadgetlist.ui.search.SearchResult

enum class ShopScreen {
    lobby,
    addItem,
    search,
    profile,
    login,
    signup,
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
    val currentScreen = navController.currentDestination?.route
    val topLevelRoutes = listOf(
        TopLevelRoute("Main", ShopScreen.lobby.name, Icons.Filled.Home),
        TopLevelRoute("Search", ShopScreen.search.name,Icons.Filled.Search),
        TopLevelRoute("Profile", ShopScreen.login.name, Icons.Filled.Person),
    )
    Scaffold(
        modifier=Modifier
            .heightIn(max=858.dp),
                topBar = {
            GadgetTopAppBar(
                title = currentScreen ?:"Gadget Store",
                canNavigateBack = navController.previousBackStackEntry != null,
                modifier = modifier,
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                navigateUp = {
                    navController.navigateUp()
                }
            )
                 },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = navBackStackEntry?.destination?.route == topLevelRoute.route,
                        // selected = currentDestination?.hierarchy?.any { it.hasRoute(
                        //      topLevelRoute.name::class,
                        //  ) } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        modifier=Modifier
                    )
                }
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
                    toSignUp = { navController.navigate(ShopScreen.signup.name) },
                    isLoginSuccess = {navController.navigate(ShopScreen.profile.name)}
                )
            }
            composable(route=ShopScreen.signup.name){
                PersonSignUpScreen(
                    toSign ={ navController.navigate(ShopScreen.login.name) }
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


data class TopLevelRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)
