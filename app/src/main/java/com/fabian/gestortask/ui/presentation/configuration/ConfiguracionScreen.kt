package com.fabian.gestortask.ui.presentation.configuration

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fabian.gestortask.domain.model.User
import com.fabian.gestortask.ui.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ConfiguracionScreen(
    navController: NavController? = null,
    viewModel: ConfiguracionViewModel = hiltViewModel()
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val uid = firebaseUser?.uid

    val userProfile by produceState<User?>(initialValue = null, key1 = uid) {
        Log.d("PerfilScreen", "UID recibido: $uid")
        if (uid != null) {
            value = viewModel.getUserProfile(uid)
        }
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {
            if (firebaseUser == null) {
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
                LaunchedEffect(userProfile) {
                    Log.d("PerfilScreen", "Perfil recibido: $userProfile")
                }
                Text(
                    text = userProfile?.let { "Bienvenido, ${it.firstName} ${it.lastName}" }
                        ?: "Cargando perfil...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            ButtonRow(navController, Screen.Help.route, "Help", firebaseUser != null)
            ButtonRow(navController, Screen.About.route, "About Application", firebaseUser != null)
            ButtonRow(navController, Screen.Feedback.route, "Send Feedback", firebaseUser != null)
            ButtonRow(navController, Screen.Support.route, "Support", firebaseUser != null)

            Spacer(modifier = Modifier.weight(1f))

            if (firebaseUser != null) {
                IconButton(
                    onClick = {
                        firebaseAuth.signOut()
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


