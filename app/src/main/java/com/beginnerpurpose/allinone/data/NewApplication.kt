package com.beginnerpurpose.allinone.data

import android.app.Application
import com.beginnerpurpose.allinone.data.room.data.AppDatabase
import com.beginnerpurpose.allinone.data.room.repository.RoomRepository

class NewApplication: Application() {

    private val database by lazy { AppDatabase.getDataBase(this) }
    val repository by lazy { RoomRepository(
        database.userDao()
    ) }
}