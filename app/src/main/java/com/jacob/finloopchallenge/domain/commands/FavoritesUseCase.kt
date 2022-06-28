package com.jacob.finloopchallenge.domain.commands

import com.jacob.finloopchallenge.data.Prefs
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val prefs: Prefs
) {
     fun saveIDtoFavoritesList(id: Int) {
        prefs.saveIDtoFavoritesList(id)
    }
     fun deleteIdFromFavoriteList(id: Int){
        prefs.deleteIDfromFavoriteList(id)
    }
     fun getFavorites():ArrayList<String>? =prefs.getFavorites()

}