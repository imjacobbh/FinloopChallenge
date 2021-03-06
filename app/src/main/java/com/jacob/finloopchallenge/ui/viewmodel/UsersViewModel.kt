package com.jacob.finloopchallenge.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacob.finloopchallenge.domain.commands.GetUserListUpdate
import com.jacob.finloopchallenge.domain.model.UserModel
import com.jacob.finloopchallenge.domain.commands.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserListUpdate: GetUserListUpdate
) : ViewModel() {
    val userListModel = MutableLiveData<List<UserModel>>()
    var isLoading = MutableLiveData<Boolean>()

    init {
        onCreate()
    }

    fun onCreate() {

        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserListUseCase()
            result?.let {
                userListModel.postValue(it)
                isLoading.postValue(false)
                return@launch
            }
            userListModel.postValue(emptyList())
            isLoading.postValue(false)

        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserListUpdate()
            result?.let {
                userListModel.postValue(it)
                isLoading.postValue(false)
                return@launch
            }
            userListModel.postValue(emptyList())
            isLoading.postValue(false)
        }
    }


}