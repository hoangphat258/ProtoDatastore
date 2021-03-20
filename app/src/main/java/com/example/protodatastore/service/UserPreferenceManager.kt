package com.example.protodatastore.service

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.codelab.android.datastore.UserPreference
import com.example.protodatastore.data.User
import com.example.protodatastore.data.UserPreferencesSerializer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val TAG = "UserPreferenceManager"

class UserPreferenceManager(context: Context) {
    private val dataStore: DataStore<UserPreference> =
        context.createDataStore(fileName = "user_prefs.pb", serializer = UserPreferencesSerializer)

    val userPreferenceFlow = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading sort order preferences.", it)
            emit(UserPreference.getDefaultInstance())
        } else {
            throw it
        }
    }.map { User(it.username, it.firstName, it.lastName, it.phoneNumber) }

    suspend fun updateUserPreference(
        username: String,
        firstName: String,
        lastName: String,
        phoneNumber: Int
    ) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setUsername(username)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhoneNumber(phoneNumber)
                .build()
        }
    }
}