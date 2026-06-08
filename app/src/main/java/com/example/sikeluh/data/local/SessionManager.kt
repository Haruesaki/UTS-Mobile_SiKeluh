package com.example.sikeluh.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

    companion object {
        val USER_NIK = stringPreferencesKey("user_nik")
    }

    suspend fun saveSession(nik: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NIK] = nik
        }
    }

    fun getSession(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NIK]
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_NIK)
        }
    }
}
