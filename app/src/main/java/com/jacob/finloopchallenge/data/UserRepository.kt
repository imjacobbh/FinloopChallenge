package com.jacob.finloopchallenge.data

import com.jacob.finloopchallenge.data.db.*
import com.jacob.finloopchallenge.data.network.UsersService
import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.domain.model.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UsersService,
    private val userDao: UserDao,
    private val userDetailDao: UserDetailDao
) {

    suspend fun getAllUsers(): List<UserModel> {
        return if (userDao.getAll().isEmpty()) {//aqui falto añadir condicion de refresco
            val response = api.getUsersFromServer()
            userDao.insert(DbMapper().convertUserListToEntity(response))
            response
        } else {
            DbMapper().convertUserListToDomain(userDao.getAll())
        }
    }
    //usado para refrescar datos
    suspend fun getUpdateListUsers():List<UserModel>{
        val response = api.getUsersFromServer()
        if(!response.isNullOrEmpty()){
            userDao.delete()
            userDao.insert(DbMapper().convertUserListToEntity(response))
            userDetailDao.delete()
            return response
        }
        return if(userDao.getAll().isEmpty())
            response
        else DbMapper().convertUserListToDomain(userDao.getAll())
    }
    suspend fun getUserDetail(id: Int): List<UserDetailsModel> {
        val userDList: MutableList<UserDetailsModel> = mutableListOf<UserDetailsModel>()
        val response =userDetailDao.getUserbyId(id) //DbMapper().convertUserDetailItemToDomain(userDetailDao.getUserbyId(id))
        if(response?.userId !=null){//aqui falto añadir condicion de refresco
            userDList.add(DbMapper().convertUserDetailItemToDomain(response))
            return userDList
        }else{
            val requestRes = api.getUserDetailsFromServer(id)
            if(!requestRes.isNullOrEmpty()){
                userDList.add(api.getUserDetailsFromServer(id)[0])
                userDetailDao.insert(DbMapper().converUserDetailListModelToEntity(userDList))
                return userDList
            }
            return  requestRes
        }
    }
}