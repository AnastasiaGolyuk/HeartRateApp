package test.createx.heartrateapp.presentation.topAppBar

import androidx.annotation.DrawableRes

data class TopAppBarNavigationState(
    val action: ()->Unit = {  },
    @DrawableRes val iconRes: Int = -1
)