package test.createx.heartrateapp.presentation.common.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.data.database.entity.User
import test.createx.heartrateapp.data.database.repository.UserRepositoryImpl


open class BaseUserViewModel(private val userRepository: UserRepositoryImpl) : ViewModel(),
    UserViewModel {

    protected val _user = mutableStateOf(User(0, "", "", "", "", "", "", ""))
    override val user: State<User> = _user
    override val pronouns: Int = R.array.pronouns_array
    override val age: Int = R.array.age_array
    override val lifestyle: Int = R.array.lifestyle_array

    override val units: MutableList<String> = mutableListOf()

    private val valuesWeightKg: List<String> = (30..200).map { it.toString() }
    private val valuesHeightCm: List<String> = (90..250).map { it.toString() }
    private val valuesWeightLb: List<String> = (66..440).map { it.toString() }
    private val valuesHeightFt: List<String> = getHeightFtList()

    override var weightsList : MutableList<String> = mutableListOf()

    override var heightsList : MutableList<String> = mutableListOf()

    protected val _isWeightPickerVisible = mutableStateOf(false)
    override val isWeightPickerVisible = _isWeightPickerVisible

    protected val _isHeightPickerVisible = mutableStateOf(false)
    override val isHeightPickerVisible = _isHeightPickerVisible

    override fun setUnitsList(units: List<String>) {
        this.units.clear()
        this.units.addAll(units)
        if (_user.value.units.isEmpty()) {
            onUnitsChange(units[0])
        } else {
            updateLists(_user.value.units)
        }
    }

    override fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
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

    override fun onNameChange(name: String) {
        _user.value = _user.value.copy(name = name)
    }

    override fun onSexChange(sex: String) {
        _user.value = _user.value.copy(sex = sex)
    }

    override fun onAgeChange(age: String) {
        _user.value = _user.value.copy(age = age)
    }

    override fun onLifestyleChange(lifestyle: String) {
        _user.value = _user.value.copy(lifestyle = lifestyle)
    }

    override fun onWeightChange(weight: String) {
        _user.value = _user.value.copy(weight = weight)
    }

    override fun onHeightChange(height: String) {
        _user.value = _user.value.copy(height = height)
    }

    override fun onUnitsChange(units: String) {
        _user.value = _user.value.copy(units = units)
        updateLists(units)
        onWeightChange("")
        onHeightChange("")
        if (_isHeightPickerVisible.value) onToggleHeightVisibility()
        if (_isWeightPickerVisible.value) onToggleWeightVisibility()
    }

    override fun onToggleWeightVisibility() {
        _isWeightPickerVisible.value = !_isWeightPickerVisible.value
        _isHeightPickerVisible.value = false
    }

    override fun onToggleHeightVisibility() {
        _isHeightPickerVisible.value = !_isHeightPickerVisible.value
        _isWeightPickerVisible.value = false
    }

    override fun updateLists(units: String) {
        weightsList.clear()
        heightsList.clear()
        if (units == this.units[1]) {
            weightsList.addAll(valuesWeightLb)
            heightsList.addAll(valuesHeightFt)
        } else {
            weightsList.addAll(valuesWeightKg)
            heightsList.addAll(valuesHeightCm)
        }
    }
}