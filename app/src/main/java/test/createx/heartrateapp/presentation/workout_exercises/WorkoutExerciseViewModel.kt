package test.createx.heartrateapp.presentation.workout_exercises

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutExerciseViewModel @Inject constructor() : ViewModel() {

    val fullCycle = 10f

    private val _timeLeft = mutableFloatStateOf(fullCycle)
    val timeLeft: State<Float> = _timeLeft

    private val _isPaused = mutableStateOf(true)
    var isPaused: State<Boolean> = _isPaused

    fun startMeasurement() {
        viewModelScope.launch {
            while (!_isPaused.value) {
                delay(100)
                _timeLeft.floatValue -= 0.1f
                if (_timeLeft.floatValue <= 0) {
                    return@launch
                }
            }
        }
    }

    fun pauseMeasurement(){
        _isPaused.value=true
    }

    fun startExercise(){
        _isPaused.value=false
    }

    fun restartTimer(){
        _isPaused.value=true
        _timeLeft.floatValue=fullCycle
    }
}