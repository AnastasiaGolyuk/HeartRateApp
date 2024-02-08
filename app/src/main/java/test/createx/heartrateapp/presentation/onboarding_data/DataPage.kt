package test.createx.heartrateapp.presentation.onboarding_data

data class DataPage(
    val title: String,
    val subtitle: String,
    val description: String,
) {
    companion object {

        fun get() = listOf(
            DataPage(
                "Welcome", "What's your name?", "We'd be happy to meet you!"
            ),
            DataPage(
                "Hey!", "What is your sex?", "This would be factored into the measurements"
            ),
            DataPage(
                "Thanks", "How old are you?", "This is necessary for accurate measurements"
            ),
            DataPage(
                "Good", "Add your parameters", "This will provide more accurate readings"
            ),
            DataPage(
                "And the last",
                "Choose your lifestyle",
                "This will help us tailor a workout program for you"
            )
        )
    }
}
