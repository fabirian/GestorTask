package com.fabian.gestortask.ui.navigation

sealed class Screen(val route: String) {

    object TaskList : Screen("task_list")

    object AddTask : Screen("add_task")

    object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: String) = "edit_task/$taskId"
    }

    object Configuracion : Screen("configuracion")

    object Login : Screen("login")

    object Register : Screen("register")

    object Help : Screen("help")

    object About : Screen("about")

    object Support : Screen("support")

    object Feedback : Screen("feedback")

}

