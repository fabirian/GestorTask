package com.fabian.gestortask.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RequireAuth(navController: NavController, content: @Composable () -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user == null) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    } else {
        content()
    }
}
