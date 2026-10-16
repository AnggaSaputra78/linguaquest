package com.linguaquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.linguaquest.ui.MainScreen
import com.linguaquest.ui.theme.LinguaQuestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LinguaQuestTheme {
                val application = application as LinguaQuestApplication
                MainScreen(container = application.container)
            }
        }
    }
}