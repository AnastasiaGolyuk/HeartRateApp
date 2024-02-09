package test.createx.heartrateapp.presentation.onboarding_data

sealed class OnboardingEvent {

    data object OnboardingCompleted: OnboardingEvent()

    data object OnboardingSkipped: OnboardingEvent()
}