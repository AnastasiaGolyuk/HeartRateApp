package test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import test.createx.heartrateapp.presentation.common.picker.rememberPickerState
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.ui.theme.White

@Composable
fun DropDownMenuComponent(
    onUnitsChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    unitsValue:String,
    weightValue: String,
    heightValue:String,
    screenViewModel: OnboardingDataViewModel
) {

    val dropDownMenuState = rememberDropDownMenuState()
    val weightPickerState = rememberPickerState()
    val heightPickerState = rememberPickerState()

    val selectedWeight = weightPickerState.selectedItem
    val selectedHeight = heightPickerState.selectedItem
    val selectedUnit = dropDownMenuState.selectedUnit

    LaunchedEffect(selectedWeight) {
        if (selectedWeight.isNotEmpty()) {
            onWeightChange(selectedWeight)
//            screenViewModel.onWeightChange(selectedWeight)
        }
    }

    LaunchedEffect(selectedHeight) {
        if (selectedHeight.isNotEmpty()) {
            onHeightChange(selectedHeight)
//            screenViewModel.onHeightChange(selectedHeight)
        }
    }

    LaunchedEffect(selectedUnit) {
        with(dropDownMenuState) {
            if (isUserToggleUnits) {
                weightPickerState.selectedItem = ""
                onWeightChange("")
//                screenViewModel.onWeightChange("")

                heightPickerState.selectedItem = ""
                onHeightChange("")
//                screenViewModel.onHeightChange("")

                onUnitsChange(selectedUnit)
//                screenViewModel.onUnitsChange(selectedUnit)
            }
            if (isHeightPickerVisible) onToggleHeightVisibility()
            if (isWeightPickerVisible) onToggleWeightVisibility()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(White),
        ) {
            UnitPicker(
                dropDownMenuState = dropDownMenuState,
                initialValue = screenViewModel.user.value.units
            )
        }

        ExpandablePickerButton(
            title = "Weight",
            isVisible = dropDownMenuState.isWeightPickerVisible,
            onToggleVisibility = {
                dropDownMenuState.onToggleWeightVisibility()
            },
            pickerState = weightPickerState,
            items = dropDownMenuState.weightsList,
            startIndex = dropDownMenuState.weightsList.indexOf(screenViewModel.user.value.weight),
            dropDownMenuState = dropDownMenuState
        )

        ExpandablePickerButton(
            title = "Height",
            isVisible = dropDownMenuState.isHeightPickerVisible,
            onToggleVisibility = {
                dropDownMenuState.onToggleHeightVisibility()
            },
            pickerState = heightPickerState,
            items = dropDownMenuState.heightsList,
            startIndex = dropDownMenuState.heightsList.indexOf(screenViewModel.user.value.height),
            dropDownMenuState = dropDownMenuState
        )
    }
}