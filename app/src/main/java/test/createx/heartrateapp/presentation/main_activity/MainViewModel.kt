package test.createx.heartrateapp.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.delay
import test.createx.heartrateapp.data.datastore.UserPreferencesDataStore
import test.createx.heartrateapp.presentation.navigation.Route
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userPreferencesDataStore: UserPreferencesDataStore
): ViewModel() {

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        userPreferencesDataStore.readAppEntry().onEach { shouldStartFromHomeScreen ->
            _startDestination.value = if(shouldStartFromHomeScreen){
                 Route.AppFunctionsNavigation.route
            }else{
                Route.AppStartNavigation.route
            }
            delay(200)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}