package com.jacob.finloopchallenge.domain.commands

import com.jacob.finloopchallenge.data.UserRepository
import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import javax.inject.Inject


class GetUserDetailUseCase @Inject constructor(
    private val repository : UserRepository
    ){

    suspend operator fun invoke(id : Int):List<UserDetailsModel>?{
        return repository.getUserDetail(id)
    }

}