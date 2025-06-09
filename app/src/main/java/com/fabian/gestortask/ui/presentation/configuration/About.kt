package com.fabian.gestortask.ui.presentation.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.utils.TitleSection

@Composable
fun About(navController: NavController? = null) {
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
                        Text(text = "About Application")
                    }
                }
            }
        }
        Column {

            TitleSection("Contacto")
            Text(
                "Puedes contactarnos en support@taskzen.com para obtener ayuda adicional.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
            TitleSection("Acerca de la aplicación")
            Text(
                "TaskZen v1.0.0",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "© 2023 TaskZen Technologies",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
