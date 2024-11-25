package com.example.gadgetlist

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Good(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val price: Int,
    @StringRes val hobbies: Int,
)

val goods = listOf(
    Good(R.drawable.samsung_1, R.string.samsung_1, 400, R.string.dog_description_1),
    Good(R.drawable.samsung_2, R.string.samsung_1, 3000, R.string.dog_description_1),
    Good(R.drawable.iphone_1, R.string.samsung_1, 800, R.string.dog_description_1),
    Good(R.drawable.iphone_2, R.string.samsung_1, 1800, R.string.dog_description_1),
    Good(R.drawable.samsung_3, R.string.samsung_1, 450, R.string.dog_description_1),
)
