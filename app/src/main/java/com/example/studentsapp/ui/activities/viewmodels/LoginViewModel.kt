package com.example.studentsapp.ui.activities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsapp.StudentApplication
import com.example.studentsapp.data.model.User
import com.example.studentsapp.data.preferences.PreferencesManager
import com.example.studentsapp.data.repos.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository,
    private val preferenceManager: PreferencesManager,
) : ViewModel() {

    private val eventsChannel = Channel<LoginPageEvent>()
    val eventsFlow = eventsChannel.receiveAsFlow()
    var user: User? =null

    fun authenticateUser(username: String, password: String){
        viewModelScope.launch {
            user = repo.authenticateUserAsync(username, password).await()
            if (user != null) {
                //preferenceManager.updateUserPref(user)
                eventsChannel.send(LoginPageEvent.UserLogin(user?.id, PreferencesManager.UserLoginStatus.SUCCESS))
            } else {
                eventsChannel.send(LoginPageEvent.UserLogin(null, PreferencesManager.UserLoginStatus.FAILED))
            }
        }
    }

    sealed class LoginPageEvent {
        data class UserLogin(val id: Long?, val status: PreferencesManager.UserLoginStatus): LoginPageEvent()
    }
}