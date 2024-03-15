package com.beginnerpurpose.allinone.data.room.repository

import androidx.lifecycle.LiveData
import com.beginnerpurpose.allinone.data.room.data.UserDao
import com.beginnerpurpose.allinone.data.room.model.User
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val userDao: UserDao) {

    // User

    val getAllUser: LiveData<List<User>> = userDao.getAllUser()
    val getAllUsername: LiveData<List<String>> = userDao.getAllUsername()

    fun getUserId(username: String): Flow<Int> {
        return userDao.getUserId(username)
    }

    fun getUserPassword(username: String): Flow<String> {
        return userDao.getUserPassword(username)
    }

    fun getUserFirstNameAndLastName(username: String): Flow<String> {
        return userDao.getUserFirstNameAndLastName(username)
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }
}