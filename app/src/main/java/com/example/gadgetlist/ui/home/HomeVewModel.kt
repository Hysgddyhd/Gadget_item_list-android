package com.example.gadgetlist.ui.home

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


class HomeViewModel(private val goodsRepository: GoodsRepository) : ViewModel() {


    val homeUiState: StateFlow<HomeUiState> = goodsRepository.getAllItemsStream().map { HomeUiState(itemList = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
    companion object {
        private const val TIMEOUT_MILLIS = 50000_000L
    }


}


data class HomeUiState(
    val itemList: List<Good> = listOf(),

)

