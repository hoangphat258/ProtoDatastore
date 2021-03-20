package com.example.protodatastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.protodatastore.data.User
import com.example.protodatastore.service.UserPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private var userPreferenceManager: UserPreferenceManager = UserPreferenceManager(context)
    var user: MutableLiveData<User> = MutableLiveData()

    fun updateUserPreference(username: String, firstName: String, lastName: String, phoneNumber: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            userPreferenceManager.updateUserPreference(
                username = username,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            userPreferenceManager.userPreferenceFlow.collect {
                    value -> user.postValue(value)
            }
        }
    }
}