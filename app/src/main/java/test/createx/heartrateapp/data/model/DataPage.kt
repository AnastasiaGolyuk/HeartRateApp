package test.createx.heartrateapp.data.model

import androidx.compose.runtime.Composable
import test.createx.heartrateapp.presentation.onboarding_data.components.DropDownMenuComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.TextInputComponent
import test.createx.heartrateapp.presentation.onboarding_data.components.ToggleInputComponent

data class DataPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val composable: @Composable () -> Unit
) {
    companion object {
        private val pronouns = listOf("She / Her", "He / Him", "They / Them")
        private val age = listOf("Less than 20", "20-29", "30-39", "40-49", "50-59", "More than 60")
        private val lifestyle = listOf("Active", "Moderate", "Sedentary")

        fun get() = listOf(
            DataPage(
                "Welcome",
                "What's your name?",
                "We'd be happy to meet you!"
            ) { TextInputComponent() },
            DataPage(
                "Hey!",
                "What is your sex?",
                "This would be factored into the measurements"
            ) { ToggleInputComponent(pronouns) },
            DataPage(
                "Thanks",
                "How old are you?",
                "This is necessary for accurate measurements"
            ) { ToggleInputComponent(age) },
            DataPage(
                "Good",
                "Add your parameters",
                "This will provide more accurate readings"
            ) { DropDownMenuComponent() },
            DataPage(
                "And the last",
                "Choose your lifestyle",
                "This will help us tailor a workout program for you"
            ) { ToggleInputComponent(lifestyle) }
)
    }
}
