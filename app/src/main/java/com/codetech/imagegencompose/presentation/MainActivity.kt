package com.codetech.imagegencompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.codetech.imagegencompose.data.model.GenerationState
import com.codetech.imagegencompose.presentation.components.AnimatedGenerateButton
import com.codetech.imagegencompose.presentation.components.AnimatedParticles
import com.codetech.imagegencompose.presentation.components.PremiumLoadingIndicator
import com.codetech.imagegencompose.presentation.viewmodel.MainViewModel
import com.codetech.imagegencompose.ui.theme.ImageGenComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageGenComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0A0A1F)
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    var prompt by remember {
        mutableStateOf("Epic fantasy character, detailed armor and textures, magical glow, dramatic cinematic lighting, ultra-detailed, concept art, fantasy illustration, high-resolution, heroic pose")
    }
    val generationState by viewModel.generationState.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "gradient"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A1F),
                        Color(0xFF1A1A3F),
                        Color(0xFF0F0F2A)
                    )
                )
            )
    ) {
        // Animated particles background
        AnimatedParticles()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Premium Header
            Text(
                text = "AI IMAGE",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF006E),
                            Color(0xFF8338EC),
                            Color(0xFF3A86FF)
                        )
                    ),
                    shadow = Shadow(
                        color = Color(0xFFFF006E).copy(alpha = 0.5f),
                        offset = Offset(0f, 4f),
                        blurRadius = 20f
                    ),
                    letterSpacing = 2.sp
                )
            )

            Text(
                text = "GENERATOR",
                style = TextStyle(
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF3A86FF),
                            Color(0xFF06FFA5),
                            Color(0xFFFFBE0B)
                        )
                    ),
                    shadow = Shadow(
                        color = Color(0xFF3A86FF).copy(alpha = 0.5f),
                        offset = Offset(0f, 4f),
                        blurRadius = 20f
                    ),
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.offset(y = (-8).dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Premium Image Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                // Glow effect layer
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 30.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color(0xFFFF006E),
                            spotColor = Color(0xFF8338EC)
                        )
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF006E).copy(alpha = 0.3f),
                                    Color(0xFF8338EC).copy(alpha = 0.3f),
                                    Color(0xFF3A86FF).copy(alpha = 0.3f)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                // Main content
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFF1A1A3F).copy(alpha = 0.6f))
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF006E),
                                    Color(0xFF8338EC),
                                    Color(0xFF3A86FF),
                                    Color(0xFF06FFA5)
                                ),
                                start = Offset(0f, animatedOffset),
                                end = Offset(1000f, 1000f + animatedOffset)
                            ),
                            shape = RoundedCornerShape(22.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    when (val state = generationState) {
                        is GenerationState.Idle -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Text(
                                    text = "✨",
                                    fontSize = 64.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Enter your creative prompt",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "and watch AI bring it to life",
                                    color = Color(0xFF06FFA5),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        is GenerationState.Loading -> {
                            PremiumLoadingIndicator()
                        }
                        is GenerationState.Success -> {
                            AsyncImage(
                                model = state.bitmap,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        is GenerationState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(
                                    text = "⚠️",
                                    fontSize = 48.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Oops! Something went wrong",
                                    color = Color(0xFFFF006E),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = state.message,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Glass Input Field
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = Color(0xFF8338EC).copy(alpha = 0.3f)
                    ),
                label = {
                    Text(
                        "Your Creative Prompt",
                        color = Color(0xFF06FFA5),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                placeholder = {
                    Text(
                        "Describe your imagination...",
                        color = Color.White.copy(alpha = 0.4f)
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.9f),
                    focusedContainerColor = Color(0xFF1A1A3F).copy(alpha = 0.7f),
                    unfocusedContainerColor = Color(0xFF1A1A3F).copy(alpha = 0.5f),
                    focusedBorderColor = Color(0xFF8338EC),
                    unfocusedBorderColor = Color(0xFF8338EC).copy(alpha = 0.5f)
                ),
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Premium Animated Button
            AnimatedGenerateButton(
                onClick = { viewModel.generateImage(prompt) },
                enabled = generationState !is GenerationState.Loading
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
