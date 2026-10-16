package com.linguaquest.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel
) {
    var name by rememberSaveable { mutableStateOf("") }
    var targetMinutes by remember { mutableStateOf(30) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Selamat datang di LinguaQuest",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Aplikasi ini membantu kamu belajar bahasa Inggris dari nol secara bertahap.",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Siapa nama kamu?",
                    style = MaterialTheme.typography.titleSmall
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama kamu") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Berapa menit target belajar kamu setiap hari?",
                    style = MaterialTheme.typography.titleSmall
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(15, 30, 50).forEach { minutes ->
                        if (targetMinutes == minutes) {
                            Button(
                                onClick = { targetMinutes = minutes }
                            ) {
                                Text("$minutes menit")
                            }
                        } else {
                            OutlinedButton(
                                onClick = { targetMinutes = minutes }
                            ) {
                                Text("$minutes menit")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.completeOnboarding(
                    displayName = name.trim(),
                    targetMinutesPerDay = targetMinutes
                )
            },
            enabled = name.trim().isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mulai Belajar")
        }
    }
}