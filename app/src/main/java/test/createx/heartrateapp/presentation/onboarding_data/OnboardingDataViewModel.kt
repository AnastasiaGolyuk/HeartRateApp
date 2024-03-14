package test.createx.heartrateapp.presentation.onboarding_data

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl
import test.createx.heartrateapp.data.preferences.UserPreferencesDataStore
import test.createx.heartrateapp.presentation.common.user.BaseUserViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingDataViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    userRepository: UserRepositoryImpl,
) : BaseUserViewModel(userRepository) {

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnboardingCompleted -> {
                saveAppEntry()
                addUser(_user.value)
            }
            OnboardingEvent.OnboardingSkipped -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            userPreferencesDataStore.saveAppEntry()
        }
    }
}
