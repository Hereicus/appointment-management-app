package com.beginnerpurpose.allinone.viewmodels.register

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.beginnerpurpose.allinone.constants.register.SignUp
import com.beginnerpurpose.allinone.data.room.model.User
import com.beginnerpurpose.allinone.data.room.repository.RoomRepository
import com.beginnerpurpose.allinone.events.Event
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: RoomRepository): ViewModel() {
    var mUsername = ObservableField<String>()
    var mPassword = ObservableField<String>()
    var mFirstname = ObservableField<String>()
    var mLastname = ObservableField<String>()

    private var _emptyField = MutableLiveData<Event<String>>()
    val emptyField: LiveData<Event<String>> = _emptyField

    private var _userCreated = MutableLiveData<Event<String>>()
    val userCreated: LiveData<Event<String>> = _userCreated

    fun signPressSignUpButton() {
        if (checkEmptyField()) {
            _emptyField.value = Event(SignUp.emptyBox)
            return
        }
        createUser()
    }

    private fun checkEmptyField(): Boolean{
        return (TextUtils.isEmpty(mFirstname.get()) ||
                TextUtils.isEmpty(mLastname.get()) ||
                TextUtils.isEmpty(mUsername.get()) ||
                TextUtils.isEmpty(mPassword.get()))
    }

    private fun createUser(){
        val user = User(0, mUsername.get().toString(), mPassword.get().toString(), mFirstname.get().toString(), mLastname.get().toString())
        _userCreated.value = Event(SignUp.successful)

        viewModelScope.launch {
            repository.addUser(user)
        }
    }
}

class SignUpVMFactory(private val repository: RoomRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}