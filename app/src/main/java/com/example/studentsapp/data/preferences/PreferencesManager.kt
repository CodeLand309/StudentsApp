package com.example.studentsapp.data.preferences


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.studentsapp.data.model.User
import com.example.studentsapp.dependency.AppModule
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

data class UserPreference(val name: String, val id: Long, val dob: String, val username: String, val gender: String, val userType: AppModule.UserType)
//data class SettingsPreference(val theme: Boolean)

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
){

    enum class UserLoginStatus{
        SUCCESS,
        FAILED
    }

    companion object{
        private const val TAG = "PreferencesManager"
        val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "User Details")
        //val Context.settingsPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = "Settings")
    }

    private object UserPrefKeys {
        val NAME = stringPreferencesKey("Name")
        val ID = longPreferencesKey("ID")
        val USER_NAME = stringPreferencesKey("User Name")
        val DOB = stringPreferencesKey("DOB")
        val GENDER = stringPreferencesKey("Gender")
        val USER_TYPE = stringPreferencesKey("Type")
    }

//    private object SettingsPrefKeys{
//        val THEME = intPreferencesKey("Theme")
//    }

    suspend fun updateUserPref(user: User){
        context.userPreferencesDataStore.edit {
            it[UserPrefKeys.NAME] = user.name
            it[UserPrefKeys.DOB] = user.dob
            it[UserPrefKeys.ID] = user.id
            it[UserPrefKeys.USER_NAME] = user.username
            it[UserPrefKeys.GENDER] = user.gender
            it[UserPrefKeys.USER_TYPE] = user.userType
        }
    }

//    suspend fun updateUserPrefUserType(param: String){
//        context.userPreferencesDataStore.edit {
//            it[UserPrefKeys.USER_TYPE] = AppModule.UserType.Teacher.name
//        }
//    }

    val userPrefFlow = context.userPreferencesDataStore.data.catch{ exception ->
            if(exception is IOException){
                Log.e(TAG, "Error reading preferences ", exception)
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }.map {
            val name = it[UserPrefKeys.NAME]?: ""
            val id = it[UserPrefKeys.ID]?: -1
            val dob = it[UserPrefKeys.DOB]?: ""
            val username = it[UserPrefKeys.USER_NAME]?: ""
            val gender = it[UserPrefKeys.GENDER]?: ""
            val userType = AppModule.UserType.valueOf(
                it[UserPrefKeys.USER_TYPE]?: AppModule.UserType.Student.name
            )
            UserPreference(name, id, dob, username, gender, userType)
        }
}