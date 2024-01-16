package test.createx.heartrateapp.presentation.onboarding_data.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DropDownMenuViewModel @Inject constructor() : ViewModel() {

    private val _isWeightPickerVisible = mutableStateOf(false)
    val isWeightPickerVisible: State<Boolean> = _isWeightPickerVisible

    private val _isHeightPickerVisible = mutableStateOf(false)
    val isHeightPickerVisible: State<Boolean> = _isHeightPickerVisible

    private val _weightsList = mutableStateOf(listOf<String>())
    val weightsList: State<List<String>> = _weightsList

    private val _heightsList = mutableStateOf(listOf<String>())
    val heightsList: State<List<String>> = _heightsList

    val units = listOf(
        "kg, cm",
        "lb, ft",
    )

    private val _selectedUnit = mutableStateOf(units[0])
    val selectedUnit: State<String> = _selectedUnit

    private val valuesWeightKg: List<String> = (30..200).map { it.toString() }
    private val valuesHeightCm: List<String> = (90..250).map { it.toString() }
    private val valuesWeightLb: List<String> = (66..440).map { it.toString() }
    private val valuesHeightFt: List<String>

    init {
        valuesHeightFt = getHeightFtList()
        _weightsList.value=valuesWeightKg
        _heightsList.value=valuesHeightCm
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

    fun onUnitChange() {
        if(_selectedUnit.value == units[0]) {
            _selectedUnit.value = units[1]
            _weightsList.value=valuesWeightLb
            _heightsList.value=valuesHeightFt
        } else {
            _selectedUnit.value = units[0]
            _weightsList.value=valuesWeightKg
            _heightsList.value=valuesHeightCm
        }
    }

    fun onToggleWeightVisibility(){
        _isWeightPickerVisible.value = !_isWeightPickerVisible.value
        _isHeightPickerVisible.value = false
    }

    fun onToggleHeightVisibility(){
        _isHeightPickerVisible.value = !_isHeightPickerVisible.value
        _isWeightPickerVisible.value = false
    }
}
