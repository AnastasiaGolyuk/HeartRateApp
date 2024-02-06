package test.createx.heartrateapp.presentation.onboarding_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.UserRepositoryImpl
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.preferences.UserPreferencesDataStore
import javax.inject.Inject

@HiltViewModel
class OnboardingDataViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    private val _user = mutableStateOf(User(1, "", "", "", "", "", "", ""))
    val user: State<User> = _user

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.OnboardingCompleted -> {
                saveAppEntry()
                addUser(_user.value)
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            userPreferencesDataStore.saveAppEntry()
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun onNameChange(name: String) {
        _user.value = _user.value.copy(name = name)
    }

    fun onSexChange(sex: String) {
        _user.value = _user.value.copy(sex = sex)
    }

    fun onAgeChange(age: String) {
        _user.value = _user.value.copy(age = age)
    }

    fun onLifestyleChange(lifestyle: String) {
        _user.value = _user.value.copy(lifestyle = lifestyle)
    }

    fun onWeightChange(weight: String) {
        _user.value = _user.value.copy(weight = weight)
    }

    fun onHeightChange(height: String) {
        _user.value = _user.value.copy(height = height)
    }

    fun onUnitsChange(units: String) {
        _user.value = _user.value.copy(units = units)
    }
}
