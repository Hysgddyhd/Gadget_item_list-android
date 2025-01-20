package com.example.gadgetlist.data


import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface GoodsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream() : Flow<List<Good>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getGoodByName(name: String) : Flow<List<Good>>

    /**
     * Retrieve an item from the given category that matches
     * */
    fun getGoodByCate(category: String) : Flow<List<Good>>

    fun getGoodById(od:Int) : Flow<Good>

    //return sample data
    fun getSample(): Flow<List<Good>>
    /**
     * Insert item in the data source
     */
    suspend fun insertGood(good: Good)

    /**
     * Delete item from the data source
     */
    suspend fun deleteGood(good: Good)

    /**
     * Update item in the data source
     */
    suspend fun updateGood(good: Good)

    //create user
    suspend fun createUser(user: User)
    //update user info
    suspend fun updateUser(user: User)
    //get user info
    fun getUserById(uid:String): Flow<User>


}

