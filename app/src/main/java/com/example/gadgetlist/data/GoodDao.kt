package com.example.gadgetlist.data

import android.graphics.ColorSpace.Named
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGood(good: Good)

    @Update
    suspend fun updateGood(good: Good)

    @Delete
    suspend fun deleteGood(good: Good)

    @Query("select * from goods order by id asc")
    fun getAllGoods() : Flow<List<Good>>

    @Query("select * from goods " +
            "where category=:category " +
            "order by id " +
            "limit 5 offset 5 ")
    fun getGoodByCate(category: String) : Flow<List<Good>>

    @Query("Select * from goods " +
            "where name like :name " +
            "or category like :name " +
            "or `desc` like :name " +
            " order by id")
    fun getGoodByName(name: String) : Flow<List<Good>>

    @Query("Select * from goods " +
            "where id = :id "
            )
    fun getGoodById(id:Int) : Flow<Good>

    //create user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createUser(user: User)

    //update user info
    @Update
    suspend fun updateUser(user: User)

    //get user info
    @Query("select * from users where uid =:uid")
    fun getUserById(uid:String) :Flow<User>

}