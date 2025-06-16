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
import com.google.firebase.auth.FirebaseUser

@Composable
fun ConfiguracionScreen(navController: NavController? = null) {
    val firebaseAuth = FirebaseAuth.getInstance()
    var currentUser by remember { mutableStateOf<FirebaseUser?>(firebaseAuth.currentUser) }

    LaunchedEffect(Unit) {
        currentUser = firebaseAuth.currentUser
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
                },
                modifier = Modifier.fillMaxSize()
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            if (currentUser == null) {
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
                    "Welcome, ${currentUser?.displayName ?: currentUser?.email ?: "User"}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            // Otros botones
            ButtonRow(navController, Screen.Help.route, "Help")
            ButtonRow(navController, Screen.About.route, "About Application")
            ButtonRow(navController, Screen.Feedback.route, "Send Feedback")
            ButtonRow(navController, Screen.Support.route, "Support")

            Spacer(modifier = Modifier.weight(1f))

            if (currentUser != null) {
                IconButton(
                    onClick = {
                        firebaseAuth.signOut()
                        currentUser = null
                        navController?.navigate(Screen.List.route)
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
fun ButtonRow(navController: NavController?, route: String, text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController?.navigate(route)
            }
            .padding(16.dp)
    )
}

