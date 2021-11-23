package com.jacob.finloopchallenge.data.db

import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.domain.model.UserModel

class DbMapper {
    fun convertUserListToDomain(list: List<User>): List<UserModel> {
        return list.map { user ->
            convertUserItemToDomain(user)
        }
    }

    fun convertUserItemToDomain(user: User) = with(user) {
        UserModel(user.createdAt, user.name, user.country, user.id)
    }

    fun converUserDetailListToDomain(list: List<UserDetail>): List<UserDetailsModel> {
        return list.map { userDetail ->
            convertUserDetailItemToDomain(userDetail)
        }
    }

    fun convertUserDetailItemToDomain(userDetail: UserDetail) = with(userDetail) {
        UserDetailsModel(
            userDetail.jobtitle,
            userDetail.birthday,
            userDetail.salary,
            userDetail.userId
        )
    }

    fun convertUserListToEntity(list: List<UserModel>): List<User> {
        return list.map { user ->
            convertUserModelItemToEntity(user)
        }
    }

    fun convertUserModelItemToEntity(user: UserModel) = with(user) {
        User(user.createdAt, user.name, user.country, user.id)
    }

    fun converUserDetailListModelToEntity(list: List<UserDetailsModel>): List<UserDetail> {
        return list.map { user ->
            convertUserDetailModelItemToEntity(user)
        }
    }

    fun convertUserDetailModelItemToEntity(userDetailModel: UserDetailsModel) =
        with(userDetailModel) {
            UserDetail(
                userDetailModel.jobtitle,
                userDetailModel.birthday,
                userDetailModel.salary,
                userDetailModel.userId
            )
        }
}