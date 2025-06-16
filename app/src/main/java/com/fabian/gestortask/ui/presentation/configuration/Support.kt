package com.fabian.gestortask.ui.presentation.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fabian.gestortask.ui.navigation.Screen
import com.fabian.gestortask.ui.utils.FaqSection
import com.fabian.gestortask.ui.utils.TitleSection

@Composable
fun Support(navController: NavController? = null){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
    {
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
                        Text(text = "Support")
                    }
                }
            }
        }

        LazyColumn {
            item {
                TitleSection("Soporte Técnico")
                FaqSection(
                    "Cómo ponerse en contacto con el equipo de soporte",
                    "Si necesitas asistencia o tienes preguntas, nuestro equipo de soporte está disponible para ayudarte. Puedes contactarnos por correo electrónico a support@taskzen.com o llamando al número +123456789 durante nuestro horario de atención."
                )
            }

            item {
                TitleSection("Política de Privacidad y Seguridad")
                FaqSection(
                    "Cómo se manejan los datos personales de los usuarios",
                    "La privacidad de tus datos es importante para nosotros. Nuestra política de privacidad detalla cómo recopilamos, utilizamos y protegemos tus datos personales. Puedes revisar nuestra política en el enlace a la privacidad en la aplicación."
                )
            }

            item {
                TitleSection("Contacto")
                FaqSection(
                    "Cómo puedes comunicarte con el equipo de soporte",
                    "Si tienes preguntas o necesitas asistencia, no dudes en ponerte en contacto con nosotros. Puedes enviarnos un correo electrónico a support@taskzen.com o llamarnos al número +123456789. Estamos aquí para ayudarte."
                )
            }

            item {
                TitleSection("Seguridad de la Cuenta")
                FaqSection(
                    "Cómo proteger tu cuenta y contraseña",
                    "Mantener segura tu cuenta es esencial. Te recomendamos utilizar contraseñas fuertes y cambiarlas periódicamente. No compartas tu contraseña con nadie y habilita la autenticación de dos factores para una capa adicional de seguridad."
                )
            }

            item {
                TitleSection("Informe de Vulnerabilidades")
                FaqSection(
                    "Cómo reportar vulnerabilidades de seguridad",
                    "Si encuentras una vulnerabilidad en nuestra aplicación, te agradecemos que nos la informes. Puedes reportar problemas de seguridad a través de nuestro programa de recompensas por seguridad o enviando un correo electrónico a security@taskzen.com."
                )
            }
        }

    }
}
