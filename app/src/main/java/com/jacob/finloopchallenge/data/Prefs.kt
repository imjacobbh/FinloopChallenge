package com.jacob.finloopchallenge.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.jacob.finloopchallenge.AppConstants.SHARED_FAV_LIST
import javax.inject.Inject

class Prefs @Inject constructor(var storage: SharedPreferences) {
    private var gson = Gson()
    private var favoriteList: ArrayList<String>? = null
    fun saveIDtoFavoritesList(id: Int) {
        if (favoriteList == null)
            favoriteList = ArrayList()
        favoriteList!!.add(id.toString())
        storage.edit().putString(SHARED_FAV_LIST, gson.toJson(favoriteList)).apply()
    }

    fun deleteIDfromFavoriteList(id: Int) {
        favoriteList!!.remove(id.toString())
        storage.edit().putString(SHARED_FAV_LIST, gson.toJson(favoriteList)).apply()
    }

    fun getFavorites(): ArrayList<String>? {
        val string = storage.getString(SHARED_FAV_LIST, null) ?: return null
        favoriteList = gson.fromJson(string, ArrayList<String>()::class.java)
        return favoriteList
    }

}