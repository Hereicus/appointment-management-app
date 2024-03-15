package com.beginnerpurpose.allinone.data

object SharedInfo{
    private var userId: Int? = null

    fun setUserId(id: Int){
        userId = id
    }

    fun getUserId(): Int{
        return userId!!
    }
}