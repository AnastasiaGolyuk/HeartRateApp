package test.createx.heartrateapp.presentation.onboarding_data

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val userRepository: UserRepositoryImpl,
) : ViewModel() {

    private val _user = mutableStateOf(User(0, "", "", "", "", "", "", ""))
    val user: State<User> = _user

    val pronouns = listOf("She / Her", "He / Him", "They / Them")
    val age = listOf("Less than 20", "20-29", "30-39", "40-49", "50-59", "More than 60")
    val lifestyle = listOf("Active", "Moderate", "Sedentary")

    val units = listOf("kg, cm", "lb, ft")

    private val valuesWeightKg: List<String> = (30..200).map { it.toString() }
    private val valuesHeightCm: List<String> = (90..250).map { it.toString() }
    private val valuesWeightLb: List<String> = (66..440).map { it.toString() }
    private val valuesHeightFt: List<String> = getHeightFtList()

    var isWeightPickerVisible by mutableStateOf(false)

    var isHeightPickerVisible by mutableStateOf(false)

    var weightsList by mutableStateOf(valuesWeightKg)

    var heightsList by mutableStateOf(valuesHeightCm)

    init {
        onUnitsChange(units[0])
    }

    private fun getHeightFtList(): List<String> {
        val list = ArrayList<String>()
        for (a in 3..8) {
            for (i in 0..9) {
                list.add("${a}\'${i}\"")
                if (a == 8 && i == 3) {
                    break
                }
            }
        }
        return list
    }

    fun onToggleWeightVisibility() {
        isWeightPickerVisible = !isWeightPickerVisible
        isHeightPickerVisible = false
    }

    fun onToggleHeightVisibility() {
        isHeightPickerVisible = !isHeightPickerVisible
        isWeightPickerVisible = false
    }


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
        if (units == this.units[1]) {
            weightsList = valuesWeightLb
            heightsList = valuesHeightFt
        } else {
            weightsList = valuesWeightKg
            heightsList = valuesHeightCm
        }
        onWeightChange("")
        onHeightChange("")
        if (isHeightPickerVisible) onToggleHeightVisibility()
        if (isWeightPickerVisible) onToggleWeightVisibility()
    }
}
