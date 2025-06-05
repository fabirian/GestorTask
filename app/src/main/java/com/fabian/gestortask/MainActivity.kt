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
import com.fabian.gestortask.ui.presentation.configuration.About
import com.fabian.gestortask.ui.presentation.configuration.ConfiguracionScreen
import com.fabian.gestortask.ui.presentation.configuration.Feedback
import com.fabian.gestortask.ui.presentation.configuration.Help
import com.fabian.gestortask.ui.presentation.configuration.Support
import com.fabian.gestortask.ui.presentation.login.LoginScreen
import com.fabian.gestortask.ui.presentation.register.RegisterScreen
import com.fabian.gestortask.ui.presentation.tasks.TaskScreen
import com.fabian.gestortask.ui.presentation.tasks.ListTaskScreen
import com.fabian.gestortask.ui.theme.GestorTaskTheme

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
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ListTaskScreen(navController)
        }
        composable(
            route = "task_screen?taskId={taskId}",
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            TaskScreen(
                navController = navController,
                taskId = if (taskId == -1) null else taskId
            )
        }
        composable("configuracion") {
            ConfiguracionScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("registro") {
            RegisterScreen(navController)
        }
        composable("help") {
            Help(navController)
        }
        composable("about") {
            About(navController)
        }
        composable("support") {
            Support(navController)
        }
        composable("feedback") {
            Feedback(navController)
        }
    }
}

