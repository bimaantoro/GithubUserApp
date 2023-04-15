package com.academy.bangkit.mygithubuser.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val THEME_KEY = booleanPreferencesKey("theme_setting")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var instance: SettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences =
            instance ?: synchronized(this) {
                instance ?: SettingPreferences(dataStore)
            }.also { instance = it }
    }
}