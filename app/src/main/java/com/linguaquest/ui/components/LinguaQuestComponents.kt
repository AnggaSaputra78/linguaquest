package com.linguaquest.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================================
// 1. TEMA & PALET WARNA (DUOLINGO TACTILE STYLE)
// ============================================================================

object LinguaColors {
    val Primary = Color(0xFF006C49)
    val PrimaryContainer = Color(0xFF10B981)
    val PrimaryExtrusion = Color(0xFF00422B)
    val PrimaryFixed = Color(0xFF6FFBBE)

    val Secondary = Color(0xFF855300)
    val SecondaryContainer = Color(0xFFFEA619)
    val SecondaryExtrusion = Color(0xFF684000)
    val SecondaryFixed = Color(0xFFFFDDB8)

    val Tertiary = Color(0xFF006398)
    val TertiaryContainer = Color(0xFF4AAAEF)
    val TertiaryFixed = Color(0xFFCCE5FF)

    val Surface = Color(0xFFFAF8FF)
    val SurfaceWhite = Color(0xFFFFFFFF)
    val SurfaceContainer = Color(0xFFEAEDFF)
    val SurfaceContainerHigh = Color(0xFFE2E7FF)
    val SurfaceVariant = Color(0xFFDAE2FD)

    val OnSurface = Color(0xFF131B2E)
    val OnSurfaceVariant = Color(0xFF3C4A42)
    val Outline = Color(0xFF6C7A71)
}

// ============================================================================
// 2. TOMBOL 3D CLAYMORPHIC (EFEK TEKAN, PEGAS & GETARAN HAPTIC)
// ============================================================================

@Composable
fun TactileClayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    surfaceColor: Color = LinguaColors.PrimaryContainer,
    extrusionColor: Color = LinguaColors.PrimaryExtrusion,
    contentColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(9999.dp),
    extrusionDepth: Dp = 6.dp,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    // Efek getaran haptic saat tombol ditekan
    LaunchedEffect(isPressed) {
        if (isPressed) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    // Animasi kompresi tombol ke bawah sumbu Y
    val translateY by animateDpAsState(
        targetValue = if (isPressed) extrusionDepth - 1.dp else 0.dp,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessHigh),
        label = "ButtonCompression"
    )

    Box(
        modifier = modifier
            .padding(bottom = extrusionDepth)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
    ) {
        // Lapisan Bibir Bawah 3D (Extrusion Base)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = extrusionDepth)
                .background(color = extrusionColor, shape = shape)
        )

        // Lapisan Permukaan yang Ditekan (Pushable Top)
        Surface(
            modifier = Modifier.offset(y = translateY),
            shape = shape,
            color = surfaceColor,
            contentColor = contentColor
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = content
            )
        }
    }
}

// ============================================================================
// 3. HEADER GAMIFIKASI ATAS
// ============================================================================

@Composable
fun TopGamificationHeader(
    streakDays: Int = 14,
    gemsCount: String = "1.4k",
    currentLanguageFlag: String = "🇪🇸",
    currentLanguageCode: String = "ES · A2",
    onLanguageClick: () -> Unit = {},
    onStreakClick: () -> Unit = {},
    onGemsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Surface(
        color = LinguaColors.Surface.copy(alpha = 0.95f),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Ikon Maskot
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(LinguaColors.PrimaryContainer, LinguaColors.Primary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Mascot",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Pil Pilihan Bahasa
                Surface(
                    onClick = onLanguageClick,
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SurfaceWhite,
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, LinguaColors.SurfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = currentLanguageFlag, fontSize = 14.sp)
                        Text(
                            text = currentLanguageCode,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LinguaColors.OnSurface
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = LinguaColors.OnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Status Gamifikasi (Streak, Gems, Avatar)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Pil Streak Api (🔥)
                Surface(
                    onClick = onStreakClick,
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SecondaryFixed.copy(alpha = 0.7f),
                    border = BorderStroke(1.dp, LinguaColors.SecondaryContainer.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = LinguaColors.Secondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = streakDays.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = LinguaColors.Secondary
                        )
                    }
                }

                // Pil Permata (💎)
                Surface(
                    onClick = onGemsClick,
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.TertiaryFixed.copy(alpha = 0.7f),
                    border = BorderStroke(1.dp, LinguaColors.TertiaryContainer.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = "Gems",
                            tint = LinguaColors.Tertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = gemsCount,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = LinguaColors.Tertiary
                        )
                    }
                }

                // Avatar Profil
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .border(2.dp, LinguaColors.PrimaryContainer.copy(alpha = 0.6f), CircleShape)
                        .clickable(onClick = onProfileClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = LinguaColors.Primary
                    )
                }
            }
        }
    }
}

// ============================================================================
// 4. KARTU MISI UNIT (UNIT MISSION BANNER)
// ============================================================================

@Composable
fun UnitMissionBanner(
    unitTag: String = "UNIT 3 • MADRID ADVENTURES",
    title: String = "Tapas, Travel & Directions",
    subtitle: String = "Master 45 essential conversational phrases",
    progressPercent: Int = 72,
    onGuidebookClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(16.dp, shape = RoundedCornerShape(24.dp), spotColor = LinguaColors.PrimaryContainer.copy(alpha = 0.35f))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(LinguaColors.PrimaryContainer, LinguaColors.Primary)
                    )
                )
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = unitTag,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }

                    Surface(
                        onClick = onGuidebookClick,
                        shape = RoundedCornerShape(9999.dp),
                        color = LinguaColors.SurfaceWhite,
                        shadowElevation = 3.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = LinguaColors.Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Guidebook",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = LinguaColors.OnSurface
                            )
                        }
                    }
                }

                Column {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        lineHeight = 30.sp
                    )
                    Text(
                        text = subtitle,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }

                // Progress Bar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(12.dp)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(Color.White.copy(alpha = 0.3f))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progressPercent / 100f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color.White)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = LinguaColors.SecondaryContainer,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "$progressPercent%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// 5. BILAH POWER-UPS (2X BOOST & STREAK FREEZE)
// ============================================================================

@Composable
fun PowerUpsHUDStrip(
    boostTimeText: String = "14:32 Left",
    streakFreezeText: String = "Equipped",
    onBoostClick: () -> Unit = {},
    onFreezeClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            onClick = onBoostClick,
            shape = RoundedCornerShape(9999.dp),
            color = LinguaColors.SurfaceWhite,
            border = BorderStroke(1.dp, LinguaColors.SurfaceVariant),
            shadowElevation = 3.dp,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(LinguaColors.SecondaryContainer, LinguaColors.Secondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = "2X Boost",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LinguaColors.OnSurface
                    )
                    Text(
                        text = boostTimeText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LinguaColors.Secondary
                    )
                }
            }
        }

        Surface(
            onClick = onFreezeClick,
            shape = RoundedCornerShape(9999.dp),
            color = LinguaColors.SurfaceWhite,
            border = BorderStroke(1.dp, LinguaColors.SurfaceVariant),
            shadowElevation = 3.dp,
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(LinguaColors.Tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = "Streak Freeze",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LinguaColors.OnSurface
                    )
                    Text(
                        text = streakFreezeText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LinguaColors.Tertiary
                    )
                }
            }
        }
    }
}

// ============================================================================
// 6. KARTU MISI HARIAN (DAILY QUESTS CARD)
// ============================================================================

@Composable
fun DailyQuestsCard(
    onSprintClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = LinguaColors.SurfaceWhite),
        border = BorderStroke(1.dp, LinguaColors.SurfaceVariant.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(LinguaColors.SecondaryFixed),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = LinguaColors.Secondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "Daily Quests",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LinguaColors.OnSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SurfaceContainer
                ) {
                    Text(
                        text = "2 of 3 Done",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LinguaColors.OnSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            // Misi 1 (Selesai)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(9999.dp))
                    .background(LinguaColors.SurfaceContainer.copy(alpha = 0.5f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(LinguaColors.PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = "Score 90%+ in 2 lessons",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LinguaColors.OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Surface(
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SecondaryFixed.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = "+15 XP",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LinguaColors.Secondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Misi 2 (Sprint Aktif)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(9999.dp))
                    .background(LinguaColors.SurfaceContainer.copy(alpha = 0.5f))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(LinguaColors.SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "0/1",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = LinguaColors.OnSurfaceVariant
                        )
                    }

                    Column {
                        Text(
                            text = "Quick Review sprint",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LinguaColors.OnSurface
                        )
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(5.dp)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(LinguaColors.SurfaceContainerHigh)
                        )
                    }
                }

                Surface(
                    onClick = onSprintClick,
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SecondaryContainer,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Go",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// 7. JALUR PETUALANGAN ULAR BERLIKU (CANVAS CURVE + 6 NODE)
// ============================================================================

data class QuestNodeData(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val status: NodeStatus,
    val stars: Int = 0,
    val lessonCode: String = "",
    val horizontalOffsetDp: Dp = 0.dp
)

enum class NodeStatus { COMPLETED, ACTIVE, LOCKED, MILESTONE }

@Composable
fun SerpentineQuestPath(
    onNodeClick: (QuestNodeData) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")

    // Animasi denyut halo node aktif
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    // Animasi lompatan balon tooltip "START HERE"
    val bounceY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BounceY"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(720.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Pita Kurva Berliku Menggunakan Canvas Jetpack Compose
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val path = Path().apply {
                moveTo(width * 0.5f, 30.dp.toPx())
                cubicTo(
                    width * 0.75f, 90.dp.toPx(),
                    width * 0.82f, 150.dp.toPx(),
                    width * 0.5f, 210.dp.toPx()
                )
                cubicTo(
                    width * 0.18f, 270.dp.toPx(),
                    width * 0.18f, 340.dp.toPx(),
                    width * 0.5f, 400.dp.toPx()
                )
                cubicTo(
                    width * 0.82f, 460.dp.toPx(),
                    width * 0.75f, 530.dp.toPx(),
                    width * 0.5f, 600.dp.toPx()
                )
            }

            // Lapisan 1: Jalur Ambient Bayangan Lembut
            drawPath(
                path = path,
                color = LinguaColors.SurfaceVariant,
                style = Stroke(
                    width = 22.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Lapisan 2: Garis Putus-Putus Mint Segar
            drawPath(
                path = path,
                color = LinguaColors.PrimaryContainer.copy(alpha = 0.45f),
                style = Stroke(
                    width = 12.dp.toPx(),
                    cap = StrokeCap.Round,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(14.dp.toPx(), 12.dp.toPx())
                    )
                )
            )
        }

        // Distribusi 6 Node di Atas Jalur
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Node 1: Mercado Central (Selesai ⭐⭐⭐, Geser Kanan +48.dp)
            QuestNodeView(
                data = QuestNodeData(
                    id = 1,
                    title = "Mercado Central",
                    icon = Icons.Default.Check,
                    status = NodeStatus.COMPLETED,
                    stars = 3,
                    lessonCode = "1",
                    horizontalOffsetDp = 48.dp
                ),
                onClick = onNodeClick
            )

            // Node 2: Ordering Tapas (Selesai ⭐⭐⭐, Geser Kiri -48.dp)
            QuestNodeView(
                data = QuestNodeData(
                    id = 2,
                    title = "Ordering Tapas",
                    icon = Icons.Default.Restaurant,
                    status = NodeStatus.COMPLETED,
                    stars = 3,
                    lessonCode = "2",
                    horizontalOffsetDp = (-48).dp
                ),
                onClick = onNodeClick
            )

            // Node 3: NODE AKTIF (Directions & Metro, Berdenyut, +16.dp)
            Box(
                modifier = Modifier
                    .offset(x = 16.dp)
                    .clickable {
                        onNodeClick(
                            QuestNodeData(
                                id = 3,
                                title = "Directions & Metro",
                                icon = Icons.Default.Navigation,
                                status = NodeStatus.ACTIVE,
                                lessonCode = "L-3"
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Balon Panggilan ("START HERE ↓")
                    Surface(
                        modifier = Modifier.offset(y = bounceY.dp),
                        shape = RoundedCornerShape(9999.dp),
                        color = LinguaColors.OnSurface,
                        shadowElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(LinguaColors.PrimaryFixed)
                            )
                            Text(
                                text = "START HERE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDownward,
                                contentDescription = null,
                                tint = LinguaColors.SecondaryContainer,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Node Denyut Halo
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(88.dp * pulseScale)
                                .clip(CircleShape)
                                .background(LinguaColors.PrimaryContainer.copy(alpha = 0.25f))
                        )

                        TactileClayButton(
                            onClick = {
                                onNodeClick(
                                    QuestNodeData(
                                        id = 3,
                                        title = "Directions & Metro",
                                        icon = Icons.Default.Navigation,
                                        status = NodeStatus.ACTIVE,
                                        lessonCode = "L-3"
                                    )
                                )
                            },
                            surfaceColor = LinguaColors.PrimaryContainer,
                            extrusionColor = LinguaColors.PrimaryExtrusion,
                            shape = CircleShape,
                            extrusionDepth = 8.dp,
                            modifier = Modifier.size(76.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Navigation,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    text = "L-3",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = LinguaColors.SurfaceWhite,
                        shadowElevation = 4.dp,
                        border = BorderStroke(1.dp, LinguaColors.PrimaryContainer.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "Directions & Metro",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = LinguaColors.Primary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Node 4: Train Station (Terkunci, -56.dp)
            QuestNodeView(
                data = QuestNodeData(
                    id = 4,
                    title = "Train Station",
                    icon = Icons.Default.Lock,
                    status = NodeStatus.LOCKED,
                    lessonCode = "4",
                    horizontalOffsetDp = (-56).dp
                ),
                onClick = onNodeClick
            )

            // Node 5: Madrid City Trial (Peti Bos Emas +50 Permata, +40.dp)
            Box(
                modifier = Modifier
                    .offset(x = 40.dp)
                    .clickable {
                        onNodeClick(
                            QuestNodeData(
                                id = 5,
                                title = "Madrid City Trial",
                                icon = Icons.Default.LockOpen,
                                status = NodeStatus.MILESTONE,
                                lessonCode = "BOSS"
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = LinguaColors.SecondaryFixed,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MilitaryTech,
                                contentDescription = null,
                                tint = LinguaColors.Secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Boss Milestone",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = LinguaColors.Secondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(contentAlignment = Alignment.BottomCenter) {
                        TactileClayButton(
                            onClick = {
                                onNodeClick(
                                    QuestNodeData(
                                        id = 5,
                                        title = "Madrid City Trial",
                                        icon = Icons.Default.LockOpen,
                                        status = NodeStatus.MILESTONE,
                                        lessonCode = "BOSS"
                                    )
                                )
                            },
                            surfaceColor = LinguaColors.SecondaryContainer,
                            extrusionColor = LinguaColors.SecondaryExtrusion,
                            shape = RoundedCornerShape(20.dp),
                            extrusionDepth = 8.dp,
                            modifier = Modifier.size(76.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LockOpen,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        // Lencana Hadiah Permata (+50)
                        Surface(
                            modifier = Modifier.offset(y = 8.dp),
                            shape = RoundedCornerShape(9999.dp),
                            color = Color.White,
                            shadowElevation = 3.dp,
                            border = BorderStroke(1.dp, LinguaColors.SecondaryContainer.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Diamond,
                                    contentDescription = null,
                                    tint = LinguaColors.SecondaryContainer,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "+50",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = LinguaColors.Secondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = LinguaColors.SurfaceWhite,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = "Madrid City Trial",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LinguaColors.OnSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Node 6: Hostel Check-in (Terkunci, Tengah)
            QuestNodeView(
                data = QuestNodeData(
                    id = 6,
                    title = "Hostel Check-in",
                    icon = Icons.Default.Lock,
                    status = NodeStatus.LOCKED,
                    lessonCode = "6",
                    horizontalOffsetDp = 0.dp
                ),
                onClick = onNodeClick
            )
        }
    }
}

@Composable
private fun QuestNodeView(
    data: QuestNodeData,
    onClick: (QuestNodeData) -> Unit
) {
    Box(
        modifier = Modifier
            .offset(x = data.horizontalOffsetDp)
            .clickable { onClick(data) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (data.status == NodeStatus.COMPLETED && data.stars > 0) {
                Surface(
                    shape = RoundedCornerShape(9999.dp),
                    color = LinguaColors.SurfaceWhite,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        repeat(data.stars) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = LinguaColors.SecondaryContainer,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Box(contentAlignment = Alignment.TopEnd) {
                if (data.status == NodeStatus.COMPLETED) {
                    TactileClayButton(
                        onClick = { onClick(data) },
                        surfaceColor = LinguaColors.PrimaryContainer,
                        extrusionColor = LinguaColors.PrimaryExtrusion,
                        shape = CircleShape,
                        extrusionDepth = 6.dp,
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = data.icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                } else {
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        color = LinguaColors.SurfaceContainerHigh,
                        shadowElevation = 2.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = null,
                                tint = LinguaColors.Outline,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }

                if (data.lessonCode.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .offset(x = 4.dp, y = (-4).dp)
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(
                                if (data.status == NodeStatus.COMPLETED) LinguaColors.SurfaceWhite else LinguaColors.SurfaceContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = data.lessonCode,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (data.status == NodeStatus.COMPLETED) LinguaColors.Primary else LinguaColors.OnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(9999.dp),
                color = if (data.status == NodeStatus.COMPLETED) LinguaColors.SurfaceWhite else LinguaColors.SurfaceContainer,
                shadowElevation = if (data.status == NodeStatus.COMPLETED) 2.dp else 0.dp
            ) {
                Text(
                    text = data.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (data.status == NodeStatus.COMPLETED) LinguaColors.OnSurface else LinguaColors.OnSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// ============================================================================
// 8. BILAH AKSI PELAJARAN BERIKUTNYA (UP NEXT ACTION LAUNCHER)
// ============================================================================

@Composable
fun StickyPlayActionLauncher(
    upNextText: String = "Lesson 3: Directions",
    onStartClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = LinguaColors.SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, LinguaColors.SurfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(LinguaColors.PrimaryContainer.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = LinguaColors.Primary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Column {
                    Text(
                        text = "UP NEXT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = LinguaColors.Primary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = upNextText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LinguaColors.OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Tombol START 3D
            TactileClayButton(
                onClick = onStartClick,
                surfaceColor = LinguaColors.PrimaryContainer,
                extrusionColor = LinguaColors.PrimaryExtrusion,
                shape = RoundedCornerShape(9999.dp),
                extrusionDepth = 5.dp
            ) {
                Text(
                    text = "START",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ============================================================================
// 9. BILAH NAVIGASI BAWAH MELAYANG (FLOATING BOTTOM NAV BAR)
// ============================================================================

@Composable
fun FloatingBottomNavBar(
    selectedTab: String = "learn",
    onTabSelected: (String) -> Unit = {},
    onQuickReviewClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(24.dp),
            color = LinguaColors.SurfaceWhite.copy(alpha = 0.96f),
            shadowElevation = 8.dp,
            border = BorderStroke(1.dp, LinguaColors.SurfaceVariant)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                NavBarItem(
                    icon = Icons.Default.Map,
                    label = "Learn",
                    isSelected = selectedTab == "learn",
                    onClick = { onTabSelected("learn") }
                )

                NavBarItem(
                    icon = Icons.Default.FlagCircle,
                    label = "Quests",
                    isSelected = selectedTab == "quests",
                    onClick = { onTabSelected("quests") }
                )

                Spacer(modifier = Modifier.size(56.dp))

                NavBarItem(
                    icon = Icons.Default.MilitaryTech,
                    label = "Ranks",
                    isSelected = selectedTab == "ranks",
                    onClick = { onTabSelected("ranks") }
                )

                NavBarItem(
                    icon = Icons.Default.Person,
                    label = "Profile",
                    isSelected = selectedTab == "profile",
                    onClick = { onTabSelected("profile") }
                )
            }
        }

        // Tombol Petir Melayang Tengah (FAB Quick Review)
        Box(
            modifier = Modifier.offset(y = (-20).dp),
            contentAlignment = Alignment.Center
        ) {
            TactileClayButton(
                onClick = onQuickReviewClick,
                surfaceColor = LinguaColors.SecondaryContainer,
                extrusionColor = LinguaColors.SecondaryExtrusion,
                shape = CircleShape,
                extrusionDepth = 6.dp,
                modifier = Modifier.size(58.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "Quick Review",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) LinguaColors.Primary else LinguaColors.OnSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
            color = if (isSelected) LinguaColors.Primary else LinguaColors.OnSurfaceVariant
        )
    }
}

// ============================================================================
// 10. LAYAR UTAMA MASTER (LINGUAQUEST MAIN SCREEN)
// ============================================================================

@Composable
fun LinguaQuestMainScreen(
    onStartLesson: () -> Unit = {},
    onGuidebookOpen: () -> Unit = {},
    onNodeSelected: (QuestNodeData) -> Unit = {}
) {
    var currentTab by remember { mutableStateOf("learn") }

    Scaffold(
        topBar = {
            TopGamificationHeader(
                streakDays = 14,
                gemsCount = "1.4k",
                currentLanguageFlag = "🇪🇸",
                currentLanguageCode = "ES · A2"
            )
        },
        bottomBar = {
            FloatingBottomNavBar(
                selectedTab = currentTab,
                onTabSelected = { currentTab = it },
                onQuickReviewClick = onStartLesson
            )
        },
        containerColor = LinguaColors.Surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            UnitMissionBanner(
                unitTag = "UNIT 3 • MADRID ADVENTURES",
                title = "Tapas, Travel & Directions",
                subtitle = "Master 45 essential conversational phrases",
                progressPercent = 72,
                onGuidebookClick = onGuidebookOpen
            )

            PowerUpsHUDStrip()

            DailyQuestsCard(onSprintClick = onStartLesson)

            Spacer(modifier = Modifier.height(12.dp))

            SerpentineQuestPath(onNodeClick = onNodeSelected)

            StickyPlayActionLauncher(
                upNextText = "Lesson 3: Directions & Metro",
                onStartClick = onStartLesson
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}