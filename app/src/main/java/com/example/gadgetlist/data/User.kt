package com.example.gadgetlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val uid:String="",
    val name:String="",
    val email:String="",
    val address:String="",
    val phone:Int=0,
    val sex:Boolean=false,
)
