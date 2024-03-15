package com.beginnerpurpose.allinone.viewmodels.register

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.beginnerpurpose.allinone.constants.register.Login
import com.beginnerpurpose.allinone.data.SharedInfo
import com.beginnerpurpose.allinone.data.room.repository.RoomRepository
import com.beginnerpurpose.allinone.events.Event
import com.beginnerpurpose.allinone.events.VoidEvent
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: RoomRepository) : ViewModel() {

    var mUsername = ObservableField<String>()
        private set
    var mPassword = ObservableField<String>()
        private set
    var usernameList = emptyList<String>()
    private val sharedInfo = SharedInfo

    private var _emptyField = MutableLiveData<Event<String>>()
    val emptyField: LiveData<Event<String>> = _emptyField

    private var _startDialog = MutableLiveData<VoidEvent>()
    val startDialog: LiveData<VoidEvent> = _startDialog

    private var _correctInfo = MutableLiveData<Event<String>>()
    val correctInfo: LiveData<Event<String>> = _correctInfo

    private var _incorrectInfo = MutableLiveData<Event<String>>()
    val incorrectInfo: LiveData<Event<String>> = _incorrectInfo

    private var _goToSignUp = MutableLiveData<VoidEvent>()
    val goToSignUp: LiveData<VoidEvent> = _goToSignUp

    fun pressSignUpButton(){
        _goToSignUp.value = VoidEvent()
    }

    fun getUsernameList() = repository.getAllUsername

    fun pressLoginButton(){
        if (isFieldEmpty()) {
            _emptyField.value = Event(Login.emptyBox)
            return
        }

        if (doesUsernameExist()) return
        getUserPassword()
    }

    private fun isFieldEmpty(): Boolean {
        return (TextUtils.isEmpty(mUsername.get()) || TextUtils.isEmpty(mPassword.get()))
    }

    private fun doesUsernameExist(): Boolean {
        return !(usernameList.contains(mUsername.get()))
    }

    private fun getUserPassword(){
        _startDialog.value = VoidEvent()

        viewModelScope.launch {
            repository.getUserPassword(mUsername.get()!!)
                .zip(repository.getUserId(mUsername.get()!!))
                {password, userId -> isPasswordCorrect(password, mPassword.get()!!, userId)}
                .collect{control -> makeDecision(control) }
        }
    }

    private fun isPasswordCorrect(fetchedPassword: String, bindedPassword: String, userId: Int): Boolean {
        return if (fetchedPassword == bindedPassword){
            sharedInfo.setUserId(userId)
            true
        } else
            false
    }

    private fun makeDecision(decision: Boolean){
        if (decision) {
            _correctInfo.value = Event(Login.successful)
        } else {
            _incorrectInfo.value = Event(Login.unsuccessful)
        }
    }
}

class LoginVMFactory(private val repository: RoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}