package com.example.gfgpt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {


    private val model = Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel("gemini-2.5-flash")

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getAnswer(girlfriendMessage: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val contextPrompt = """
                You are gfGPT, a helpful assistant for boyfriends who need advice on how to respond to their girlfriends. 
                You understand relationships, emotions, and communication between couples. 
                Always provide thoughtful, caring, and appropriate responses that will help maintain a healthy relationship.
                
                My girlfriend just said: "$girlfriendMessage"
                
                Please provide a sweet, understanding, and appropriate response that I can send back to her. 
                Make the response sound natural and caring, not robotic. Consider the emotional context and respond accordingly.
                If it's something serious, be supportive. If it's playful, match the energy. If she seems upset, be comforting.
                
                Just give me the response I should send, nothing else.
                """.trimIndent()

                val result = model.generateContent(contextPrompt)
                _response.value = result.text ?: "I'm not sure how to respond to that. Maybe try asking in a different way?"
            } catch (e: Exception) {
                _response.value = "Oops! I couldn't generate a response right now. Check your internet connection and try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to clear the response
    fun clearResponse() {
        _response.value = ""
    }
}