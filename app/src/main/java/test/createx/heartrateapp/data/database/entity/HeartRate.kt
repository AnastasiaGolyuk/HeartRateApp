package test.createx.heartrateapp.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime

@Parcelize
@Entity(tableName = "heart_rate")
data class HeartRate(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "heart_rate_value")
    val heartRateValue: Int,

    @ColumnInfo(name = "user_state")
    val userState: String?,

    @ColumnInfo(name = "heart_rate_status")
    val heartRateStatus: String,

    @ColumnInfo(name = "date_time")
    val dateTime: OffsetDateTime,
) : Parcelable