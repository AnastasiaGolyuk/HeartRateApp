package test.createx.heartrateapp.presentation.statistics.components

data class DailyAverageMeasurements(
    val dayOfPeriod: Int,
    val normalHeartRate: Int,
    val lowestHeartRate: Int,
    val highestHeartRate: Int
)