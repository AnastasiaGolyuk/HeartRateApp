package test.createx.heartrateapp.presentation.heart_rate

import androidx.annotation.DrawableRes
import test.createx.heartrateapp.R

data class Hint(
    val hint: String,
    @DrawableRes val image: Int? = null,
) {

    companion object {
        fun get() = listOf(
            Hint(hint = "Relax, don't control your breathing. Press the button to start the measurement"),
            Hint(hint = "Relax your hand and fingers, don't control your breathing"),
            Hint(hint = "We recommend taking measurements daily to get a more complete picture of your condition"),
            Hint(
                hint = "Put your finger on the camera lens and flash at the same time and continue measuring",
                image = R.drawable.hint_img
            )
        )
    }
}
