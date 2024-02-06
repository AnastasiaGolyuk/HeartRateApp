package test.createx.heartrateapp.presentation.navigation

sealed class Route(
    val route: String
) {
    data object OnboardingScreen : Route(route = "onboardingScreen")

    data object PaywallScreen : Route(route = "paywallScreen")

    data object OnboardingDataScreen : Route(route = "onboardingDataScreen")

    data object HeartRateScreen : Route(route = "heartRateScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")

    data object AppFunctionsNavigation : Route(route = "appFunctionsNavigation")

}