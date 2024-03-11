package test.createx.heartrateapp.presentation.heart_rate_measurement

import android.view.SurfaceView
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.kibotu.heartrateometer.HeartRateOmeter
import test.createx.heartrateapp.presentation.heart_rate.Hint
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class HeartRateMeasurementViewModel @Inject constructor() : ViewModel() {

    private val _subscription: MutableState<CompositeDisposable?> = mutableStateOf(null)

    private val _bpmUpdates: MutableState<Disposable?> = mutableStateOf(null)

    private val _isPaused = mutableStateOf(true)
    var isPaused: State<Boolean> = _isPaused

    private val _rate = mutableStateOf("--")
    val rate: State<String> = _rate

    private val _hints = Hint.get()

    private val _hint = mutableStateOf(_hints[0])
    val hint: State<Hint> = _hint

    val fullCycle = 30f

    private val _timeLeft = mutableFloatStateOf(fullCycle)
    val timeLeft: State<Float> = _timeLeft

    fun startMeasurement() {
        viewModelScope.launch {
            while (!_isPaused.value) {
                delay(100)
                getHintsOnTime()
                _timeLeft.floatValue -= 0.1f
                if (_timeLeft.floatValue <= 0) {
                    disposeSubscription()
                    _isPaused.value=true
                    return@launch
                }
            }
            if (_isPaused.value) {
                setHintOnPause()
            }
        }
    }

    private fun getHintsOnTime() {
        if (_hint.value==_hints[3]){
            _hint.value = _hints[1]
            return
        }
        val roundedTimeLeft = round(_timeLeft.floatValue * 10 / 10f)
        if (roundedTimeLeft % 10 == 0f) {
            _hint.value = _hints[1]
        } else if (roundedTimeLeft % 5 == 0f) {
            _hint.value = _hints[2]
        }
    }

    fun disposeSubscription() {
        if (_subscription.value?.isDisposed == false) {
            _subscription.value?.clear()
            _subscription.value?.dispose()
        }
    }

    fun initBpmUpdates(surfaceView: SurfaceView) {
        _subscription.value = CompositeDisposable()
        _bpmUpdates.value = HeartRateOmeter()
            .withAverageAfterSeconds(3)
            .setFingerDetectionListener { isFingerDetected ->
                _isPaused.value = !isFingerDetected
            }.bpmUpdates(surfaceView = surfaceView)
            .subscribe(
                { bpm: HeartRateOmeter.Bpm? -> _rate.value = bpm?.value.toString() },
                { it -> println("${it.message}") }
            )
        _subscription.value?.add(_bpmUpdates.value!!)
    }

    fun areBpmUpdatesInited(): Boolean {
        return if (_subscription.value != null) {
            _subscription.value!!.size() > 0
        } else {
            false
        }
    }

    private fun setHintOnPause() {
        _hint.value = _hints[3]
    }
}