package com.example.gadgetlist.ui.navigation

interface NavigationDestination {

    val route: String

    /**
     * String resource id to that contains title to be displayed for the screen.
     */
    val titleRes: Int
}
