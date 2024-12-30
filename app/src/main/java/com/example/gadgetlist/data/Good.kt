package com.example.gadgetlist.data

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import com.example.gadgetlist.R
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goods")
data class Good(
    @PrimaryKey(autoGenerate = true)
    val id:Int ,
     val name: String,
    val category:String,
    val price: Int,
    val quantity: Int =5,
    @ColumnInfo(name = "desc")
    val description:String,
    @ColumnInfo(name = "image")
    val imageResourceId: Int = Resources.getSystem().getIdentifier("no_photo", "drawable", "android")
)

val sample : List<Good> = listOf(
    Good(
        id = 1,
        name = "iPhone 11",
        category = "Phone",
        price = 1300,
        quantity = 10,
        description = "expensive iphone"
    )
)

