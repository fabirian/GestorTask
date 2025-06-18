package com.fabian.gestortask.ui.presentation.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ConfiguracionScreen(navController: NavController? = null) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser = remember { mutableStateOf(firebaseAuth.currentUser) }

    LaunchedEffect(currentUser.value) {
        println("DEBUG - UID: ${currentUser.value?.uid}")
        println("DEBUG - Email: ${currentUser.value?.email}")
        println("DEBUG - DisplayName: ${currentUser.value?.displayName}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController?.navigate(Screen.List.route)
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Settings")
                    }
                }
            }
        }

        // Contenido
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            if (currentUser.value == null) {
                Text(
                    "Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController?.navigate(Screen.Login.route)
                        }
                        .padding(16.dp)
                )
            } else {
                Text(
                    text = "Welcome, ${currentUser.value?.email ?: currentUser.value?.displayName ?: "User"}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            ButtonRow(
                navController = navController,
                route = Screen.Help.route,
                text = "Help",
                enabled = currentUser.value != null
            )
            ButtonRow(
                navController = navController,
                route = Screen.About.route,
                text = "About Application",
                enabled = currentUser.value != null
            )
            ButtonRow(
                navController = navController,
                route = Screen.Feedback.route,
                text = "Send Feedback",
                enabled = currentUser.value != null
            )
            ButtonRow(
                navController = navController,
                route = Screen.Support.route,
                text = "Support",
                enabled = currentUser.value != null
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón cerrar sesión (si hay usuario)
            if (currentUser.value != null) {
                IconButton(
                    onClick = {
                        firebaseAuth.signOut()
                        currentUser.value = null
                        navController?.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .background(MaterialTheme.colorScheme.outline)
                ) {
                    Text("Sign Out", color = MaterialTheme.colorScheme.surface)
                }
            }
        }
    }
}

@Composable
fun ButtonRow(navController: NavController?, route: String, text: String, enabled: Boolean) {
    val textColor = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline

    Text(
        text = text,
        color = textColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .then(
                if (enabled)
                    Modifier.clickable { navController?.navigate(route) }
                else
                    Modifier
            )
    )
}


