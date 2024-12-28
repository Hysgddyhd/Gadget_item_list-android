package com.example.gadgetlist.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.data.GoodsRepository
import java.text.NumberFormat


class GoodEntryViewModel(private val goodsRepository: GoodsRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var goodUiState by mutableStateOf(GoodUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(goodDetails: GoodDetails) {
        goodUiState =
            GoodUiState(goodDetails = goodDetails, isEntryValid = validateInput(goodDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            goodsRepository.insertGood(goodUiState.goodDetails.toGood())
        }
    }

    private fun validateInput(uiState: GoodDetails = goodUiState.goodDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

}

/**
 * Represents Ui State for an Item.
 */
data class GoodUiState(
    val goodDetails: GoodDetails = GoodDetails(),
    val isEntryValid: Boolean = false
)

data class GoodDetails(
    val id: Int = 0,
    val name: String = "",
    val category:String="",
    val price: String = "",
    val quantity: String = "",
    val description:String = "",
    val imageResourceId:Int= 2130968589
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun GoodDetails.toGood(): Good = Good(
    id = id,
    name = name,
    category=category,
    price = price.toIntOrNull() ?: 0,
    quantity = quantity.toIntOrNull() ?: 0,
    description = description,
    imageResourceId=imageResourceId
)

fun Good.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Good.toGoodUiState(isEntryValid: Boolean = false): GoodUiState = GoodUiState(
    goodDetails = this.toGoodDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Good.toGoodDetails(): GoodDetails = GoodDetails(
    id = id,
    name = name,
    category=category,
    price = price.toString(),
    quantity = quantity.toString(),
    description = description,
    imageResourceId=imageResourceId
)


