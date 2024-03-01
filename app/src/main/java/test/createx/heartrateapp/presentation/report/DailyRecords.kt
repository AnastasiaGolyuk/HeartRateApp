package test.createx.heartrateapp.presentation.report

import test.createx.heartrateapp.data.database.entity.HeartRate
import java.time.OffsetDateTime

data class DailyRecords(val dateTime: OffsetDateTime, val heartRateList: List<HeartRate>)