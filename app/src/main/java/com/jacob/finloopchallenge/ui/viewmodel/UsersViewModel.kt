package com.jacob.finloopchallenge.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jacob.finloopchallenge.data.network.UsersService
import com.jacob.finloopchallenge.domain.model.UserModel
import com.jacob.finloopchallenge.domain.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel  @Inject constructor(private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    val userListModel = MutableLiveData<List<UserModel>>()
    var isLoading = MutableLiveData<Boolean>()



    fun onCreate(boolean: Boolean = false) {

        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserListUseCase()
            if (!result.isNullOrEmpty()) {
                userListModel.postValue(result!!)
                isLoading.postValue(false)
            } else {
                userListModel.postValue(emptyList())
                isLoading.postValue(false)
            }
        }
    }




}