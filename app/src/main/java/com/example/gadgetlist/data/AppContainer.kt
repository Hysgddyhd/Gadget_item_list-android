package com.example.gadgetlist.data

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val goodsRepository: GoodsRepository
    val auth:FirebaseAuth
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context,auth: FirebaseAuth) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val goodsRepository: GoodsRepository by lazy {
        OfflineGoodsRepository(InventoryDatabase.getDatabase(context).goodDao())
    }

    override val auth: FirebaseAuth=auth

}
