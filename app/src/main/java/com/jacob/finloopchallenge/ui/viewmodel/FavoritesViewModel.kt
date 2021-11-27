package com.jacob.finloopchallenge.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacob.finloopchallenge.domain.commands.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(var favorites: FavoritesUseCase) : ViewModel() {
    val favoriteList = MutableLiveData<List<String>?>()

    init {
        getFavoriteList()
    }

    fun getFavoriteList() {
        viewModelScope.launch {
            val result = favorites.getFavorites()
            favoriteList.postValue(result)
        }
    }

    fun onUpdateFavoriteList(id: Int) {

        viewModelScope.launch {
            val result = favorites.getFavorites()
            if (result.isNullOrEmpty()) {
                favorites.saveIDtoFavoritesList(id)
            } else if (result.contains(id.toString())) {
                favorites.deleteIDfromFavoriteList(id)
            } else {
                favorites.saveIDtoFavoritesList(id)
            }
            getFavoriteList()
        }
    }
}