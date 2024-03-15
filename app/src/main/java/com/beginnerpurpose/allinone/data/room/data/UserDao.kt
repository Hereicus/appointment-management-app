package com.beginnerpurpose.allinone.data.room.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.beginnerpurpose.allinone.data.room.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT username FROM Users")
    fun getAllUsername(): LiveData<List<String>>

    @Query("SELECT id FROM Users WHERE username = :userName")
    fun getUserId(userName: String): Flow<Int>

    @Query("SELECT password FROM Users WHERE username = :userName")
    fun getUserPassword(userName: String): Flow<String>

    @Query("SELECT firstname || ' ' || lastname FROM Users WHERE username=:userName")
    fun getUserFirstNameAndLastName(userName: String): Flow<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM Users")
    suspend fun deleteAllUsers()
}