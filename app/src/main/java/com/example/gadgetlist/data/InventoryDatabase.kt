package com.example.gadgetlist.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Good::class], version = 1, exportSchema = true)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun goodDao(): GoodDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    "item_database")
                    //.createFromAsset("database/goods_start_example.db")
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}

             //