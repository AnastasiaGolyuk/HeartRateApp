package test.createx.heartrateapp.presentation.navigation

sealed class Graph(val route: String) {
    data object EnteringGraph :Graph(route="entering_graph")
    data object HomeGraph :Graph(route="home_graph")
    data object HeartMeasurementGraph :Graph(route="heart_measurement_graph")

    data object WorkoutGraph :Graph(route="workout_graph")
    data object ProfileDetailsGraph :Graph(route="profile_details_graph")
}