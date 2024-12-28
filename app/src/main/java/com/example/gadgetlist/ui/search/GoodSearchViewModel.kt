package com.example.gadgetlist.ui.search

import android.util.Log
import com.example.gadgetlist.data.Good
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.getColumnIndexOrThrow
import com.example.gadgetlist.data.GoodsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class GoodSearchViewModel(private val goodsRepository: GoodsRepository) : ViewModel() {

 //   private val input: String = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    val searchUiState: StateFlow<SearchUiState> = goodsRepository.getAllItemsStream().map { SearchUiState(itemList = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SearchUiState()
        )
    companion object {
        private const val TIMEOUT_MILLIS = 50000_000L
    }


}


data class SearchUiState(
    val itemList: List<Good> = listOf(),

)


