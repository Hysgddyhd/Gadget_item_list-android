package com.example.gadgetlist.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gadgetlist.data.GoodsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GoodEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val goodsRepository: GoodsRepository

) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var goodUiState by mutableStateOf(GoodUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[GoodEditDestination.itemIdArg])

     init {
        viewModelScope.launch {
            goodUiState = goodsRepository.getGoodById(itemId)
                .filterNotNull()
                .first()
                .toGoodUiState(true)
        }
    }

    private fun validateInput(uiState: GoodDetails = goodUiState.goodDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
    fun updateUiState(itemDetails: GoodDetails){
           goodUiState= GoodUiState(goodDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun updateItem(){
        goodsRepository.updateGood(goodUiState.goodDetails.toGood())
    }

    suspend fun deleteItem(){
        goodsRepository.deleteGood(goodUiState.goodDetails.toGood())
    }

}
