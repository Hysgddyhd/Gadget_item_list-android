package com.example.gadgetlist.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class OfflineGoodsRepository(private val goodDao: GoodDao): GoodsRepository {
    override fun getAllItemsStream(): Flow<List<Good>>{
        Log.d("lifecycle","call goodDao method getallGood")
        val allGood= goodDao.getAllGoods()
        if (allGood==null){
            return flowOf(listOf(
                Good(
                    id = 1,
                    name = "iPhone 11",
                    category = "Phone",
                    price = 1300,
                    quantity = 10,
                    description = "expensive iphone"
                )
            )
        )
        }else
            return allGood
    }

    override fun getGoodByName(name: String): Flow<List<Good>> =goodDao.getGoodByName(name)

    override fun getGoodByCate(category: String): Flow<List<Good>> =goodDao.getGoodByCate(category)

    override fun getSample(): Flow<List<Good>> {
        return flowOf(
            listOf(
                Good(
                    id = 1,
                    name = "iPhone 11",
                    category = "Phone",
                    price = 1300,
                    quantity = 10,
                    description = "expensive iphone"
                )
            )
        )
    }

    override suspend fun insertGood(good: Good) =goodDao.insertGood(good)

    override suspend fun deleteGood(good: Good) =goodDao.deleteGood(good)

    override suspend fun updateGood(good: Good) =goodDao.updateGood(good)



}