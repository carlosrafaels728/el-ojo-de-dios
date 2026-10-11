package com.carlosart.elojodedios.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AssistantApp(viewModel: AssistantViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var messageText by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF07111F),
                        Color(0xFF0D1B2A),
                        Color(0xFF09192B)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "EL OJO DE DIOS",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7CF0FF),
                    letterSpacing = 0.8.sp
                )
            )

            Text(
                text = "Asistente de carlosart",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB6DFFF)
            )

            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Key, contentDescription = null) },
                label = { Text("API Key de Gemini") }
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.messages) { message ->
                    val alignment = if (message.role == "user") Alignment.End else Alignment.Start
                    val bgColor = if (message.role == "user") Color(0xFF0A60B7) else Color(0xFF102437)
                    val textColor = if (message.role == "user") Color.White else Color(0xFFEAF7FF)

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = alignment
                    ) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(18.dp))
                                .background(bgColor)
                                .padding(12.dp),
                            horizontalAlignment = if (message.role == "user") Alignment.End else Alignment.Start
                        ) {
                            Text(
                                text = if (message.role == "user") "Tú" else "El Ojo de Dios",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (message.role == "user") Color(0xFFCFE8FF) else Color(0xFF7CF0FF),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = message.text,
                                color = textColor,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                if (uiState.isLoading) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(strokeWidth = 2.dp)
                            Text(
                                text = "Pensando...",
                                modifier = Modifier.padding(start = 8.dp),
                                color = Color(0xFFBCE5FF)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Send
                    ),
                    placeholder = { Text("Pregunta al sistema...") },
                    singleLine = false,
                    maxLines = 4
                )

                Button(
                    onClick = {
                        if (messageText.isNotBlank() && apiKey.isNotBlank()) {
                            viewModel.sendPrompt(messageText.trim(), apiKey.trim())
                            messageText = ""
                        }
                    },
                    modifier = Modifier.heightIn(min = 56.dp),
                    shape = RoundedCornerShape(18.dp),
                    enabled = messageText.isNotBlank() && apiKey.isNotBlank() && !uiState.isLoading
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        // Voz opcional: aquí se puede integrar con SpeechRecognizer en una próxima fase.
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Filled.Mic, contentDescription = null)
                    Text(text = "Hablar", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}
