package com.fabian.gestortask.ui.presentation.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.utils.FaqSection
import com.fabian.gestortask.ui.utils.TitleSection

@Composable
fun Help(navController: NavController? = null) {
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
                    navController?.navigate(Screen.Configuracion.route)
                },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(text = "Help")
                    }
                }
            }
        }

        LazyColumn {
            item {
                TitleSection("Preguntas frecuentes")
                FaqSection(
                    "¿Cómo puedo cambiar mi contraseña?",
                    "Para cambiar tu contraseña, ve a la sección de perfil, luego selecciona la opción 'Cambiar contraseña'. Ingresa tu contraseña actual y luego tu nueva contraseña. Confirma la nueva contraseña y presiona 'Guardar cambios'."
                )
                FaqSection(
                    "¿Cómo puedo actualizar mi perfil?",
                    "Puedes actualizar tu perfil llendo a la sección de perfil y editando la información que deseas cambiar. Asegúrate de hacer clic en 'Guardar cambios' una vez que hayas realizado tus modificaciones."
                )
            }
            item {
                TitleSection("Contacto")
                Text(
                    "Puedes contactarnos en support@task.com para obtener ayuda adicional.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}
