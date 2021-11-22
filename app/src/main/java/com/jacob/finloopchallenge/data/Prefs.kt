package com.jacob.finloopchallenge.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.jacob.finloopchallenge.AppConstants.SHARED_FAVLIST
import com.jacob.finloopchallenge.AppConstants.SHARED_NAME

class Prefs(private val context: Context) {
    private var gson = Gson()
    val storage = context.getSharedPreferences(SHARED_NAME, 0)
    private var favoriteList: ArrayList<String>? = getFavorites()
    fun saveIDtoFavoritesList(id: Int) {
        Log.d("PREFERENCES", "json before: $favoriteList")
        if (favoriteList == null)
            favoriteList = ArrayList()
        favoriteList!!.add(id.toString())
        Log.d("PREFERENCES", "json added: $favoriteList")
        storage.edit().putString(SHARED_FAVLIST, gson.toJson(favoriteList)).apply()
    }

    fun deleteIDfromFavoriteList(id: Int) {
        Log.d("PREFERENCES", "json before: $favoriteList")
        favoriteList!!.remove(id.toString())
        Log.d("PREFERENCES", "json deleted: $favoriteList")
        storage.edit().putString(SHARED_FAVLIST, gson.toJson(favoriteList)).apply()
    }

    fun getFavorites(): ArrayList<String>? {
        val string = storage.getString(SHARED_FAVLIST, null) ?: return null
        favoriteList = gson.fromJson(string, ArrayList<String>()::class.java)
        return favoriteList
    }

}