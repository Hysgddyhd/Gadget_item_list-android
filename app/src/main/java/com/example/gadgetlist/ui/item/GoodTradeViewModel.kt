package com.example.gadgetlist.ui.item

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.gadgetlist.data.Good
import com.example.gadgetlist.data.GoodsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoodTradeViewModel(
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository

) : ViewModel() {

    /**
     * Holds current item ui state
     */
        private val itemId: Int = checkNotNull(savedStateHandle[GoodEditDestination.itemIdArg])

        val goodUiState: StateFlow<GoodTradeUiState> =
            goodsRepository.getGoodById(itemId)
                .filterNotNull()
                .map {
                    GoodTradeUiState(outOfStock = it.quantity <= 0, goodDetails = it.toGoodDetails())
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = GoodTradeUiState()
                )
        companion object {
            private const val TIMEOUT_MILLIS = 5_000L
        }

    //SELL BUTTON ENABLE OR NOT
    //local good



     fun sellItem(){
        viewModelScope.launch{
            val currentItem=goodUiState.value.goodDetails.toGood()
            if (currentItem.quantity>0){
                goodsRepository.updateGood(
                    currentItem.copy(quantity = currentItem.quantity.dec())
                )
            } else {
                //nothing
            }
        }}


}
data class GoodTradeUiState(
    val outOfStock: Boolean = true,
    val goodDetails: GoodDetails = GoodDetails()
)