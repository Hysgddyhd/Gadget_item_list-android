package com.example.gadgetlist.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.gadgetlist.data.GoodsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GoodTradeViewModel(
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository

) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var goodUiState by mutableStateOf(GoodUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[GoodEditDestination.itemIdArg])
    //SELL BUTTON ENABLE OR NOT
    var isItemAvailable : Boolean = false
    //local good


     init {
        viewModelScope.launch {
            goodUiState = goodsRepository.getGoodById(itemId)
                .filterNotNull()
                .first()
                .toGoodUiState(true)
        }
    }


    suspend fun sellItem(){
        var good1=goodUiState.goodDetails.toGood()
        good1=good1.copy(quantity = good1.quantity)
        goodsRepository.updateGood(good1)

    }
    fun updateUiState(goodDetails: GoodDetails) {
        goodUiState =
            GoodUiState(goodDetails = goodDetails)
    }
     fun checkItemStatus(){
         if(goodUiState.goodDetails.quantity.equals("0")){
             isItemAvailable = false
         }else{
             isItemAvailable=true
         }

     }

}
