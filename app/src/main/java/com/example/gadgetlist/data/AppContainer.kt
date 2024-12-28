package com.example.gadgetlist.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val goodsRepository: GoodsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val goodsRepository: GoodsRepository by lazy {
        OfflineGoodsRepository(InventoryDatabase.getDatabase(context).goodDao())
    }
}
