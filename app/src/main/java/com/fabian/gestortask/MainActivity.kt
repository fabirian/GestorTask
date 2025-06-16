package com.fabian.gestortask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.presentation.configuration.About
import com.fabian.gestortask.ui.presentation.configuration.ConfiguracionScreen
import com.fabian.gestortask.ui.presentation.configuration.Feedback
import com.fabian.gestortask.ui.presentation.configuration.Help
import com.fabian.gestortask.ui.presentation.configuration.Support
import com.fabian.gestortask.ui.presentation.list.AddTaskListScreen
import com.fabian.gestortask.ui.presentation.list.ListTaskListScreen
import com.fabian.gestortask.ui.presentation.login.LoginScreen
import com.fabian.gestortask.ui.presentation.perfil.PerfilScreen
import com.fabian.gestortask.ui.presentation.register.RegisterScreen
import com.fabian.gestortask.ui.presentation.tasks.TaskScreen
import com.fabian.gestortask.ui.presentation.tasks.ListTaskScreen
import com.fabian.gestortask.ui.theme.GestorTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorTaskTheme {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.List.route) {

        composable(Screen.List.route) {
            ListTaskScreen(navController)
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
            TaskScreen(
                navController = navController,
                taskId = taskId
            )
        }

        composable(Screen.AddTask.route) {
            TaskScreen(navController = navController, taskId = null)
        }

        composable(Screen.ListTaskList.route) {
            ListTaskListScreen(navController)
        }

        composable(Screen.AddTaskList.route) {
            AddTaskListScreen(navController, taskListId = null)
        }

        composable(
            route = Screen.EditTaskList.route,
            arguments = listOf(
                navArgument("listId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
            AddTaskListScreen(navController = navController, taskListId =  listId)
        }

        composable(Screen.Configuracion.route) {
            ConfiguracionScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Help.route) {
            Help(navController)
        }

        composable(Screen.About.route) {
            About(navController)
        }

        composable(Screen.Support.route) {
            Support(navController)
        }

        composable(Screen.Feedback.route) {
            Feedback(navController)
        }
        
        composable(Screen.Perfil.route) {
            PerfilScreen(navController)
        }
    }
}



