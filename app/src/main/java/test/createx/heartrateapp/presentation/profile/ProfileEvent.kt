package test.createx.heartrateapp.presentation.profile

sealed class ProfileEvent {
    data object SaveChanges: ProfileEvent()

}
