package test.createx.heartrateapp.presentation.heart_rate

sealed class HeartRateEvent {
    data object SaveChanges: HeartRateEvent()

}