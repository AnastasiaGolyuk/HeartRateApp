package test.createx.heartrateapp.presentation.navigation

sealed class Route(
    val route: String
) {
    object OnboardingScreen : Route(route = "onboardingScreen")

    object HeartRateScreen : Route(route = "heartRateScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object AppFunctionsNavigation : Route(route = "appFunctionsNavigation")

}