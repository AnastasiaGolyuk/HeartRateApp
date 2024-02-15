package test.createx.heartrateapp.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.database.UserRepositoryImpl
import test.createx.heartrateapp.data.database.entity.User
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    private val usersFlow: Flow<List<User>> = userRepository.getAllUsersStream()

    private val _user = mutableStateOf(User(0, "", "", "", "", "", "", ""))
    val user: State<User> = _user

    private var isExist: Boolean = false

    private val _areChangesSaved = mutableStateOf(true)
    val areChangesSaved: State<Boolean> = _areChangesSaved

    init {
        viewModelScope.launch {
            usersFlow.collect { res ->
                if (res.isNotEmpty()) {
                    _user.value = res[0]
                    isExist = true
                } else {
                    _user.value = _user.value.copy(units = units[0])
                }
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.SaveChanges -> {
                if (isExist) {
                    updateUser(_user.value)
                } else {
                    addUser(_user.value)
                }
                _areChangesSaved.value = true
            }
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    val sex = listOf("She / Her", "He / Him", "They / Them")
    val age = listOf("Less than 20", "20-29", "30-39", "40-49", "50-59", "More than 60")
    val lifestyle = listOf("Active", "Moderate", "Sedentary")

    val units = listOf("kg, cm", "lb, ft")

    private val valuesWeightKg: List<String> = (30..200).map { it.toString() }
    private val valuesHeightCm: List<String> = (90..250).map { it.toString() }
    private val valuesWeightLb: List<String> = (66..440).map { it.toString() }
    private val valuesHeightFt: List<String> = getHeightFtList()

    var isNameInputVisible by mutableStateOf(false)

    var isSexPickerVisible by mutableStateOf(false)

    var isAgePickerVisible by mutableStateOf(false)

    var isLifestylePickerVisible by mutableStateOf(false)

    var isWeightPickerVisible by mutableStateOf(false)

    var isHeightPickerVisible by mutableStateOf(false)

    var weightsList by mutableStateOf(valuesWeightKg)

    var heightsList by mutableStateOf(valuesHeightCm)

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

    fun onToggleNameVisibility() {
        isNameInputVisible = !isNameInputVisible
        isSexPickerVisible = false
        isAgePickerVisible = false
        isLifestylePickerVisible = false
        isWeightPickerVisible = false
        isHeightPickerVisible = false
    }

    fun onToggleSexVisibility() {
        isSexPickerVisible = !isSexPickerVisible
        isNameInputVisible = false
        isAgePickerVisible = false
        isLifestylePickerVisible = false
        isWeightPickerVisible = false
        isHeightPickerVisible = false
    }

    fun onToggleAgeVisibility() {
        isAgePickerVisible = !isAgePickerVisible
        isNameInputVisible = false
        isSexPickerVisible = false
        isLifestylePickerVisible = false
        isWeightPickerVisible = false
        isHeightPickerVisible = false
    }

    fun onToggleLifestyleVisibility() {
        isLifestylePickerVisible = !isLifestylePickerVisible
        isNameInputVisible = false
        isAgePickerVisible = false
        isSexPickerVisible = false
        isWeightPickerVisible = false
        isHeightPickerVisible = false
    }

    fun onToggleWeightVisibility() {
        isWeightPickerVisible = !isWeightPickerVisible
        isNameInputVisible = false
        isAgePickerVisible = false
        isSexPickerVisible = false
        isLifestylePickerVisible = false
        isHeightPickerVisible = false
    }

    fun onToggleHeightVisibility() {
        isHeightPickerVisible = !isHeightPickerVisible
        isNameInputVisible = false
        isAgePickerVisible = false
        isSexPickerVisible = false
        isLifestylePickerVisible = false
        isWeightPickerVisible = false
    }

    fun onNameChange(name: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(name = name)
    }

    fun onSexChange(sex: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(sex = sex)
    }

    fun onAgeChange(age: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(age = age)
    }

    fun onLifestyleChange(lifestyle: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(lifestyle = lifestyle)
    }

    fun onWeightChange(weight: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(weight = weight)
    }

    fun onHeightChange(height: String) {
        _areChangesSaved.value = false
        _user.value = _user.value.copy(height = height)
    }

    fun onUnitsChange(units: String) {
        _areChangesSaved.value = false
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