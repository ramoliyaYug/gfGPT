package com.example.gfgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.gfgpt.ui.theme.GeminitestingTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminitestingTheme(enableFullscreen = true) {
                Screen(viewModel = viewModel)
            }
        }
    }
}
