package test.createx.heartrateapp.presentation.heart_rate

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import test.createx.heartrateapp.R

data class Hint(
    @StringRes val hint: Int,
    @DrawableRes val image: Int? = null,
) {
    companion object {
        fun get() = listOf(
            Hint(hint = R.string.hint_intro_text),
            Hint(hint = R.string.hint_first_text),
            Hint(hint = R.string.hint_second_text),
            Hint(
                hint = R.string.hint_measurements_text,
                image = R.drawable.hint_img
            )
        )
    }
}
