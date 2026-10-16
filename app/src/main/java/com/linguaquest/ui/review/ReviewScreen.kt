package com.linguaquest.ui.review

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linguaquest.ui.components.LinguaColors
import com.linguaquest.ui.components.TactileClayButton

@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
            }
            Text(
                text = "Quick Review",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        when {
            state.isLoading -> CircularProgressIndicator()
            state.isCompleted -> {
                Text(
                    text = "Review Selesai!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = LinguaColors.Primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Kosakata Anda sudah diperbarui.")
                Spacer(modifier = Modifier.height(32.dp))
                TactileClayButton(
                    onClick = onBack,
                    surfaceColor = LinguaColors.PrimaryContainer,
                    extrusionColor = LinguaColors.PrimaryExtrusion
                ) {
                    Text("Kembali ke Beranda", color = LinguaColors.SurfaceWhite, fontWeight = FontWeight.Bold)
                }
            }
            state.vocabList.isNotEmpty() -> {
                val vocab = state.vocabList[state.currentIndex]
                
                Text(
                    text = "${state.currentIndex + 1} / ${state.vocabList.size}",
                    color = LinguaColors.OnSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Flashcard
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .graphicsLayer {
                            // Animasi flip sederhana (rotateY)
                            rotationY = if (state.isFlipped) 180f else 0f
                            cameraDistance = 12f * density
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.isFlipped) LinguaColors.SecondaryContainer else LinguaColors.PrimaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.isFlipped) {
                            // Tampilkan arti (grafika terbalik agar tidak mirror)
                            Box(modifier = Modifier.graphicsLayer { rotationY = 180f }) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = vocab.indonesian,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = LinguaColors.SurfaceWhite
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = vocab.exampleId,
                                        fontSize = 14.sp,
                                        color = LinguaColors.SurfaceWhite.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = vocab.english,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = LinguaColors.SurfaceWhite
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Ketuk untuk melihat arti",
                                    fontSize = 14.sp,
                                    color = LinguaColors.SurfaceWhite.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                if (!state.isFlipped) {
                    TactileClayButton(
                        onClick = viewModel::flipCard,
                        surfaceColor = LinguaColors.TertiaryContainer,
                        extrusionColor = LinguaColors.Tertiary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Balik Kartu", color = LinguaColors.SurfaceWhite, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TactileClayButton(
                            onClick = viewModel::onForget,
                            surfaceColor = LinguaColors.SecondaryContainer,
                            extrusionColor = LinguaColors.SecondaryExtrusion,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = LinguaColors.SurfaceWhite)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Lupa", color = LinguaColors.SurfaceWhite, fontWeight = FontWeight.Bold)
                        }

                        TactileClayButton(
                            onClick = viewModel::onRemember,
                            surfaceColor = LinguaColors.PrimaryContainer,
                            extrusionColor = LinguaColors.PrimaryExtrusion,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = LinguaColors.SurfaceWhite)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Ingat", color = LinguaColors.SurfaceWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}