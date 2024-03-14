package test.createx.heartrateapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import test.createx.heartrateapp.R
import test.createx.heartrateapp.presentation.common.AlertDialog
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.ExpandablePickerButton
import test.createx.heartrateapp.presentation.onboarding_data.components.dropdown_component.UnitPicker
import test.createx.heartrateapp.presentation.profile.components.ExpandableTextInputButton
import test.createx.heartrateapp.presentation.topAppBar.TopAppBarNavigationState
import test.createx.heartrateapp.ui.theme.GreyBg
import test.createx.heartrateapp.ui.theme.RedAction
import test.createx.heartrateapp.ui.theme.RedMain

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onComposing: (TopAppBarNavigationState) -> Unit,
    navController: NavController
) {

    val units = stringArrayResource(id = R.array.units_array)

    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.setUnitsList(units.asList())
        onComposing(
            TopAppBarNavigationState(
                action = {
                    if (!viewModel.areChangesSaved.value) {
                        openAlertDialog.value = true
                    } else {
                        navController.popBackStack()
                    }
                },
                iconRes = R.drawable.return_icon
            )
        )
    }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreyBg)
    ) {
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
                color = RedAction
            )
        } else {
            if (openAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        navController.popBackStack()
                    },
                    dialogTitle = stringResource(R.string.report_alert_dialog_title),
                    dialogText = stringResource(R.string.report_alert_dialog_text),
                    confirmButtonText = stringResource(R.string.report_alert_dialog_button_text)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                ExpandableTextInputButton(
                    title = stringResource(R.string.report_name_input_title),
                    iconRes = R.drawable.name_icon,
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    isVisible = viewModel.isNameInputVisible.value,
                    onToggleVisibility = {
                        viewModel.onToggleNameVisibility()
                    },
                    onInput = { name ->
                        viewModel.onNameChange(name)
                    },
                    value = viewModel.user.value.name
                )
                Spacer(modifier = Modifier.height(1.dp))
                ExpandablePickerButton(
                    title = stringResource(R.string.report_sex_input_title),
                    iconRes = R.drawable.sex_icon,
                    shape = RoundedCornerShape(0.dp),
                    isVisible = viewModel.isSexPickerVisible.value,
                    onToggleVisibility = {
                        viewModel.onToggleSexVisibility()
                    },
                    items = stringArrayResource(id = viewModel.pronouns).asList(),
                    onSelect = { sex ->
                        viewModel.onSexChange(sex)
                    },
                    value = viewModel.user.value.sex
                )
                Spacer(modifier = Modifier.height(1.dp))
                ExpandablePickerButton(
                    title = stringResource(R.string.report_age_input_title),
                    iconRes = R.drawable.age_icon,
                    shape = RoundedCornerShape(0.dp),
                    isVisible = viewModel.isAgePickerVisible.value,
                    onToggleVisibility = {
                        viewModel.onToggleAgeVisibility()
                    },
                    items = stringArrayResource(id = viewModel.age).asList(),
                    onSelect = { age ->
                        viewModel.onAgeChange(age)
                    },
                    value = viewModel.user.value.age
                )
                Spacer(modifier = Modifier.height(1.dp))
                ExpandablePickerButton(
                    title = stringResource(R.string.report_lifestyle_input_title),
                    iconRes = R.drawable.lifestyle_icon,
                    shape = RoundedCornerShape(0.dp),
                    isVisible = viewModel.isLifestylePickerVisible.value,
                    onToggleVisibility = {
                        viewModel.onToggleLifestyleVisibility()
                    },
                    items = stringArrayResource(id = viewModel.lifestyle).asList(),
                    onSelect = { lifestyle ->
                        viewModel.onLifestyleChange(lifestyle)
                    },
                    value = viewModel.user.value.lifestyle
                )
                Spacer(modifier = Modifier.height(1.dp))
                UnitPicker(
                    iconRes = R.drawable.units_icon,
                    shape = RoundedCornerShape(0.dp),
                    value = viewModel.user.value.units,
                    onChange = { units ->
                        viewModel.onUnitsChange(units)
                    },
                    units = viewModel.units
                )
                Spacer(modifier = Modifier.height(1.dp))
                ExpandablePickerButton(
                    title = stringResource(R.string.report_weight_input_title),
                    iconRes = R.drawable.weight_icon,
                    shape = RoundedCornerShape(0.dp),
                    isVisible = viewModel.isWeightPickerVisible.value,
                    onToggleVisibility = {
                        scope.launch {
                            if (viewModel.isWeightPickerVisible.value) {
                                scrollState.animateScrollTo(300)
                            } else {
                                scrollState.animateScrollTo(0)
                            }
                        }
                        viewModel.onToggleWeightVisibility()
                    },
                    items = viewModel.weightsList,
                    onSelect = { weight ->
                        viewModel.onWeightChange(weight)
                    },
                    value = viewModel.user.value.weight
                )
                Spacer(modifier = Modifier.height(1.dp))
                ExpandablePickerButton(
                    title = stringResource(R.string.report_height_input_title),
                    iconRes = R.drawable.height_icon,
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                    isVisible = viewModel.isHeightPickerVisible.value,
                    onToggleVisibility = {
                        scope.launch {
                            if (viewModel.isHeightPickerVisible.value) {
                                scrollState.animateScrollTo(350)
                            } else {
                                scrollState.animateScrollTo(0)
                            }
                        }
                        viewModel.onToggleHeightVisibility()
                    },
                    items = viewModel.heightsList,
                    onSelect = { height ->
                        viewModel.onHeightChange(height)
                    },
                    value = viewModel.user.value.height
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, GreyBg),
                        )
                    )
            )
            ElevatedButton(
                onClick = {
                    viewModel.onEvent(ProfileEvent.SaveChanges)
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .height(48.dp)
                    .align(Alignment.BottomCenter)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(50.dp),
                        clip = true,
                        ambientColor = Color(0xFFCC0909),
                        spotColor = Color(0xFFCC0909),
                    ),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = RedMain,
                    disabledContainerColor = RedMain.copy(alpha = 0.5f),
                    disabledContentColor = RedMain.copy(alpha = 0.5f),
                )
            ) {
                Text(
                    text = stringResource(R.string.save_changes_button_title),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}
