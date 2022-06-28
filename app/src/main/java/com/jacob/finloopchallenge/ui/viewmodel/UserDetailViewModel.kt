package com.jacob.finloopchallenge.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacob.finloopchallenge.domain.commands.GetUserDetailUseCase
import androidx.lifecycle.viewModelScope
import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {
    val userDetailModel = MutableLiveData<List<UserDetailsModel>>()
    var isLoading = MutableLiveData<Boolean>()

    fun onCreate(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getUserDetailUseCase(id)
            result?.let {
                userDetailModel.postValue(it)
                isLoading.postValue(false)
                return@launch
            }
            userDetailModel.postValue(emptyList())
            isLoading.postValue(false)
        }
    }

    fun getUserListModelObserver(): MutableLiveData<List<UserDetailsModel>> {
        return userDetailModel
    }
}