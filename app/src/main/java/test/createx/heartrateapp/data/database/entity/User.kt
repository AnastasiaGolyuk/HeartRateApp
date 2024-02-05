package test.createx.heartrateapp.data.database.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "sex")
    val sex: String,

    @ColumnInfo(name = "age")
    val age: String,

    @ColumnInfo(name = "lifestyle")
    val lifestyle: String,

    @ColumnInfo(name = "units")
    val units: String,

    @ColumnInfo(name = "weight")
    val weight: String,

    @ColumnInfo(name = "height")
    val height: String
) : Parcelable