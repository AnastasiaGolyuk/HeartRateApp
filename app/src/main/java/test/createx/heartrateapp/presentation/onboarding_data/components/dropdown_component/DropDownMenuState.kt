package test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberDropDownMenuState() = remember { DropDownMenuState() }

class DropDownMenuState {

    val units = listOf("kg, cm", "lb, ft")

    private val valuesWeightKg: List<String> = (30..200).map { it.toString() }
    private val valuesHeightCm: List<String> = (90..250).map { it.toString() }
    private val valuesWeightLb: List<String> = (66..440).map { it.toString() }
    private val valuesHeightFt: List<String> = getHeightFtList()

    var isWeightPickerVisible by mutableStateOf(false)

    var isHeightPickerVisible by mutableStateOf(false)

    var isUserToggleUnits by mutableStateOf(false)

    var weightsList by mutableStateOf(valuesWeightKg)

    var heightsList by mutableStateOf(valuesHeightCm)

    var selectedUnit by mutableStateOf(units[0])


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

    fun onUnitChange(unit: String, isUserToggleUnits: Boolean) {
        this.isUserToggleUnits = isUserToggleUnits
        if (unit == units[1]) {
            selectedUnit = units[1]
            weightsList = valuesWeightLb
            heightsList = valuesHeightFt
        } else {
            selectedUnit = units[0]
            weightsList = valuesWeightKg
            heightsList = valuesHeightCm
        }
    }

    fun onToggleWeightVisibility() {
        isWeightPickerVisible = !isWeightPickerVisible
        isHeightPickerVisible = false
    }

    fun onToggleHeightVisibility() {
        isHeightPickerVisible = !isHeightPickerVisible
        isWeightPickerVisible = false
    }
}
