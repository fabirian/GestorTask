package com.fabian.gestortask.ui.navigation

sealed class Screen(val route: String) {

    object List : Screen("list")

    object AddTask : Screen("task_screen")

    object EditTask : Screen("task_screen?taskId={taskId}") {
        fun createRoute(taskId: String) = "task_screen?taskId=$taskId"
    }

    object ListTaskList : Screen("list_task_list")

    object AddTaskList : Screen("add_task_list")

    object EditTaskList : Screen("add_task_list?listId={listId}") {
        fun createRoute(listId: String) = "add_task_list?listId=$listId"
    }

    object Perfil : Screen("perfil")

    object Configuracion : Screen("configuracion")

    object Login : Screen("login")

    object Register : Screen("register")

    object Help : Screen("help")

    object About : Screen("about")

    object Support : Screen("support")

    object Feedback : Screen("feedback")
}
