package test.createx.heartrateapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import test.createx.heartrateapp.presentation.home.HomeScreen
import test.createx.heartrateapp.presentation.onboarding.OnboardingScreen
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataScreen
import test.createx.heartrateapp.presentation.onboarding_data.OnboardingDataViewModel
import test.createx.heartrateapp.presentation.paywall.PaywallScreen
import test.createx.heartrateapp.presentation.splash_screen.SplashScreen
import test.createx.heartrateapp.presentation.splash_screen.SplashViewModel

@Composable
fun EnteringNavGraph(
//    startDestination: String,
    navController: NavHostController,
//    onComposing: (TopAppBarNavigationState) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route
    ) {

        composable(route = Route.SplashScreen.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Route.OnboardingScreen.route) {
            OnboardingScreen(navController = navController)
        }
        composable(route = Route.PaywallScreen.route) {
            PaywallScreen(navController = navController)
        }
        composable(route = Route.OnboardingDataScreen.route) {
            val viewModel: OnboardingDataViewModel = hiltViewModel()
            OnboardingDataScreen(viewModel = viewModel,navController = navController)
        }
        composable(route = Route.HomeScreen.route) {
            HomeScreen()
        }
    }
}