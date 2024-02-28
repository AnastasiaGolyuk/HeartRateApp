package test.createx.heartrateapp.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import test.createx.heartrateapp.data.database.DateTimeTypeConverter
import java.time.OffsetDateTime

@Parcelize
@Entity(tableName = "heart_rate")
@TypeConverters(DateTimeTypeConverter::class)
data class HeartRate(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "heart_rate_value")
    val heartRateValue: String,

    @ColumnInfo(name = "user_state")
    val userState: String,

    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
) : Parcelable