package com.carlosart.elojodedios.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosart.elojodedios.data.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(val role: String, val text: String)

data class AssistantUiState(
    val messages: List<ChatMessage> = listOf(
        ChatMessage(
            role = "assistant",
            text = "Bienvenido. Soy El Ojo de Dios. Dime qué deseas hacer."
        )
    ),
    val isLoading: Boolean = false,
    val error: String? = null
)

class AssistantViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _uiState = MutableStateFlow(AssistantUiState())
    val uiState: StateFlow<AssistantUiState> = _uiState.asStateFlow()

    fun sendPrompt(prompt: String, apiKey: String) {
        val userMessage = ChatMessage(role = "user", text = prompt)
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + userMessage,
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            try {
                val response = repository.askGemini(prompt = prompt, apiKey = apiKey)
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + ChatMessage(role = "assistant", text = response),
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + ChatMessage(
                        role = "assistant",
                        text = "No pude responder en este momento. Verifica la API key o intenta otra pregunta."
                    ),
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
