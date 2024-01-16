package test.createx.heartrateapp.presentation.navigation

sealed class Route(
    val route: String
) {
    object OnboardingScreen : Route(route = "onboardingScreen")

    object PaywallScreen : Route(route = "paywallScreen")

    object OnboardingDataScreen : Route(route = "onboardingDataScreen")

    object HeartRateScreen : Route(route = "heartRateScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object AppFunctionsNavigation : Route(route = "appFunctionsNavigation")

}