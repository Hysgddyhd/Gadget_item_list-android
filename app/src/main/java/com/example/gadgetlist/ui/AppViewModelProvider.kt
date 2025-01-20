package com.example.gadgetlist.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gadgetlist.GadgetListApplication
import com.example.gadgetlist.ui.home.HomeViewModel
import com.example.gadgetlist.ui.item.GoodEditViewModel
import com.example.gadgetlist.ui.item.GoodEntryViewModel
import com.example.gadgetlist.ui.item.GoodTradeViewModel
import com.example.gadgetlist.ui.login.PersonProfileViewModel
import com.example.gadgetlist.ui.profile.PersonViewModel
import com.example.gadgetlist.ui.search.GoodSearchViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            GoodEntryViewModel(
                goodsRepository = inventoryApplication().container.goodsRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                inventoryApplication().container.goodsRepository
            )

        }
        //initial search viewmodel
        initializer {
            GoodSearchViewModel(
                inventoryApplication().container.goodsRepository

            )
        }
        //initial edit viewmodel
        initializer {
            GoodEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.goodsRepository
            )
        }
        //initisal GoodTradeViewModel
        initializer {
            GoodTradeViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.goodsRepository
            )
        }

        //initial profile view model
        initializer {
            PersonProfileViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.goodsRepository,
                inventoryApplication().container.auth
            )
        }
        //initial PersonViewModel
        initializer {
            PersonViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.goodsRepository,
                inventoryApplication().container.auth
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): GadgetListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GadgetListApplication)
