package com.example.studentsapp.ui.fragments.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsapp.data.model.User
import com.example.studentsapp.data.preferences.PreferencesManager
import com.example.studentsapp.data.repos.Repository
import com.example.studentsapp.dependency.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: Repository,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    lateinit var studentsLiveData: MutableLiveData<List<User>>;
    lateinit var teachersLiveData: MutableLiveData<List<User>>;

    fun getStudents(): LiveData<List<User>>{
        viewModelScope.launch {
            val result = repository.getUserAsync(userType = AppModule.UserType.Student.name)
            studentsLiveData = MutableLiveData<List<User>>(result.await())
        }
        return studentsLiveData
    }

    fun getTeacher(): LiveData<List<User>>{
        viewModelScope.launch {
            val result = repository.getUserAsync(userType = AppModule.UserType.Teacher.name)
            teachersLiveData = MutableLiveData<List<User>>(result.await())
        }
        return teachersLiveData
    }

}