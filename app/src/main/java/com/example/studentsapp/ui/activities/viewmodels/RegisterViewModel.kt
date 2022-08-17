package com.example.studentsapp.ui.activities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsapp.StudentApplication
import com.example.studentsapp.data.model.User
import com.example.studentsapp.data.preferences.PreferencesManager
import com.example.studentsapp.data.repos.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: Repository,
    private val preferenceManager: PreferencesManager,
) : ViewModel() {

    private val eventsChannel = Channel<CreateAccountEvent>()
    val eventsFlow = eventsChannel.receiveAsFlow()

    fun initiateTeacherAccountRequest(user: User) = viewModelScope.launch {
        viewModelScope.launch {
            val user = repo.requestTeacherAccountAsync(user).await()
            if (user != null)
                //preferenceManager.updateUserPref(user)
                eventsChannel.send(CreateAccountEvent.InitiateTeacherAccountRequestEvent(user.id))
            else
                eventsChannel.send(CreateAccountEvent.InitiateTeacherAccountRequestEvent(null))
        }
    }

    fun createStudentAccount(user: User) = viewModelScope.launch {
        val user = repo.createStudentAccountAsync(user).await()
        if (user != null)
            //preferenceManager.updateUserPref(user)
            eventsChannel.send(CreateAccountEvent.CreateStudentAccountEvent(user.id))
        else
            eventsChannel.send(CreateAccountEvent.CreateStudentAccountEvent(null))
    }

    sealed class CreateAccountEvent {
        data class InitiateTeacherAccountRequestEvent(val id: Long?) : CreateAccountEvent()
        data class CreateStudentAccountEvent(val id: Long?) : CreateAccountEvent()
    }
}