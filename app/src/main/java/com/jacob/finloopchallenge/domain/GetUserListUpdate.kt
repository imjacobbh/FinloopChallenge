package com.jacob.finloopchallenge.domain

import com.jacob.finloopchallenge.data.UserRepository
import com.jacob.finloopchallenge.domain.model.UserModel
import javax.inject.Inject

class GetUserListUpdate @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke():List<UserModel>?{
        return repository.getUpdateListUsers()
    }
}