package com.fabian.gestortask.ui.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fabian.gestortask.auth.FirebaseAuthManager
import com.fabian.gestortask.ui.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController? = null,
    authManager: FirebaseAuthManager = FirebaseAuthManager()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController?.navigate(Screen.Login.route) }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Create an account",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(12.dp))

        val cornerRadius = 10.dp

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            shape = RoundedCornerShape(cornerRadius),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            shape = RoundedCornerShape(cornerRadius),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            shape = RoundedCornerShape(cornerRadius),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeat Password") },
            shape = RoundedCornerShape(cornerRadius),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensajes de error o éxito
        errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 4.dp))
        }

        successMessage?.let {
            Text(text = it, color = Color.Green, modifier = Modifier.padding(bottom = 4.dp))
        }

        // Botón de registrar
        Button(
            onClick = {
                when {
                    name.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank() -> {
                        errorMessage = "Por favor completa todos los campos."
                        successMessage = null
                    }
                    password != repeatPassword -> {
                        errorMessage = "Las contraseñas no coinciden."
                        successMessage = null
                    }
                    else -> {
                        scope.launch {
                            val result = authManager.createUser(email, password)
                            if (result.isSuccess) {
                                successMessage = "Registro exitoso. ¡Bienvenido, $name!"
                                errorMessage = null
                                navController?.navigate("task_list") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                errorMessage = result.exceptionOrNull()?.localizedMessage ?: "Error al registrar usuario."
                                successMessage = null
                            }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Registrarse", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(text = "¿Ya tienes una cuenta?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Inicia sesión",
                color = Color(0xFF118df0),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController?.navigate(Screen.Login.route)
                }
            )
        }
    }
}

