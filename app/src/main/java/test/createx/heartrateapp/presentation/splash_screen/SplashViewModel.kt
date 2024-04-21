package test.createx.heartrateapp.presentation.splash_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import test.createx.heartrateapp.data.preferences.UserPreferencesDataStore
import test.createx.heartrateapp.presentation.navigation.Route
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _startDestination = mutableStateOf(Route.OnboardingScreen.route)
    val startDestination: State<String> = _startDestination

    private val isFirstEnter = true

    init {
        userPreferencesDataStore.readAppEntry().onEach { shouldStartFromHomeScreen ->
            _startDestination.value = if (shouldStartFromHomeScreen) {
                "${Route.HomeScreen.route}?isFirstEnter=${isFirstEnter}"
            } else {
                Route.OnboardingScreen.route
            }
        }.launchIn(viewModelScope)
    }
}