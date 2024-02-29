package test.createx.heartrateapp.presentation.heart_rate_measurement

sealed class HeartRateEvent {
    data object SaveChanges: HeartRateEvent()

}