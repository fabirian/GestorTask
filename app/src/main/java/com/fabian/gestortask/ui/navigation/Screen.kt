package com.fabian.gestortask.ui.navigation

sealed class Screen(val route: String) {

    object TaskList : Screen("task_list")

    object AddTask : Screen("task_screen")

    object EditTask : Screen("task_screen?taskId={taskId}") {
        fun createRoute(taskId: String) = "task_screen?taskId=$taskId"
    }

    object Configuracion : Screen("configuracion")

    object Login : Screen("login")

    object Register : Screen("register")

    object Help : Screen("help")

    object About : Screen("about")

    object Support : Screen("support")

    object Feedback : Screen("feedback")
}
