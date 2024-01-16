package test.createx.heartrateapp.presentation.onboarding_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.createx.heartrateapp.data.datastore.UserPreferencesDataStore
import javax.inject.Inject

@HiltViewModel
class OnboardingDataViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    fun onEvent(event: OnboardingEvent){
        when(event){
            is OnboardingEvent.OnboardingCompleted ->{
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            userPreferencesDataStore.saveAppEntry()
        }
    }
}
