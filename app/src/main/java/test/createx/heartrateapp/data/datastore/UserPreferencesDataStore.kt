package test.createx.heartrateapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("user_settings")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val userPreferencesDataStore = context.userPreferencesDataStore

    private object PreferencesKeys {
        val APP_ENTRY = booleanPreferencesKey("app_entry")
    }

    suspend fun saveAppEntry() {
        userPreferencesDataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    fun readAppEntry(): Flow<Boolean> {
        return userPreferencesDataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }
}