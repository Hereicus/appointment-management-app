package com.beginnerpurpose.allinone.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val userId: Int,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "password") val userPassword: String,
    @ColumnInfo(name = "firstname") val userFirstName: String,
    @ColumnInfo(name = "lastname") val userLastName: String,
)
