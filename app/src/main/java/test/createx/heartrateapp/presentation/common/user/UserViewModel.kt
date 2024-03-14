package test.createx.heartrateapp.presentation.common.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import test.createx.heartrateapp.data.database.entity.User

interface UserViewModel {
    val user: State<User>
    val pronouns: Int
    val age: Int
    val lifestyle: Int

    val units: MutableList<String>

    val weightsList: MutableList<String>
    val heightsList: MutableList<String>

    val isWeightPickerVisible: MutableState<Boolean>
    val isHeightPickerVisible: MutableState<Boolean>

    fun setUnitsList(units: List<String>)

    fun addUser(user: User)

    fun onNameChange(name: String)
    fun onSexChange(sex: String)
    fun onAgeChange(age: String)
    fun onLifestyleChange(lifestyle: String)
    fun onWeightChange(weight: String)
    fun onHeightChange(height: String)
    fun onUnitsChange(units: String)

    fun onToggleWeightVisibility()
    fun onToggleHeightVisibility()

    fun updateLists(units: String)
}