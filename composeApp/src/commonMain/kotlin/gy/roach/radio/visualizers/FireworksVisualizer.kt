package gy.roach.radio.visualizers

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

data class Particle(
    var x: Float,
    var y: Float,
    var velocityX: Float,
    var velocityY: Float,
    var alpha: Float = 1f,
    val color: Color,
    var lifetime: Float = 1f
)

data class Firework(
    var x: Float,
    var y: Float,
    var targetY: Float,
    var particles: List<Particle> = emptyList(),
    var exploded: Boolean = false,
    val color: Color = randomFireworkColor()
)

private fun randomFireworkColor() = Color(
    red = Random.nextFloat(),
    green = Random.nextFloat(),
    blue = Random.nextFloat(),
    alpha = 1f
)

@Composable
fun FireworksVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    var fireworks by remember { mutableStateOf(listOf<Firework>()) }
    val gravity = 0.15f

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            // Launch new fireworks randomly
            if (Random.nextFloat() < 0.05f) {
                fireworks = fireworks + Firework(
                    x = Random.nextFloat() * 1000f,
                    y = 1000f,
                    targetY = Random.nextFloat() * 400f + 100f
                )
            }

            // Update existing fireworks
            fireworks = fireworks.flatMap { firework ->
                if (!firework.exploded) {
                    // Move firework up
                    firework.y -= 10f
                    if (firework.y <= firework.targetY) {
                        // Explode!
                        val particleCount = Random.nextInt(30, 60)
                        val particles = List(particleCount) {
                            val angle = Random.nextFloat() * 2f * PI.toFloat()
                            val velocity = Random.nextFloat() * 8f + 2f
                            Particle(
                                x = firework.x,
                                y = firework.y,
                                velocityX = cos(angle) * velocity,
                                velocityY = sin(angle) * velocity,
                                color = firework.color
                            )
                        }
                        listOf(firework.copy(exploded = true, particles = particles))
                    } else {
                        listOf(firework)
                    }
                } else {
                    // Update particles
                    val updatedParticles = firework.particles.mapNotNull { particle ->
                        particle.x += particle.velocityX
                        particle.y += particle.velocityY
                        particle.velocityY += gravity
                        particle.alpha *= 0.96f
                        particle.lifetime -= 0.02f

                        if (particle.lifetime > 0) particle else null
                    }

                    if (updatedParticles.isEmpty()) {
                        emptyList() // Remove firework if all particles are gone
                    } else {
                        listOf(firework.copy(particles = updatedParticles))
                    }
                }
            }
            delay(16) // Approximately 60 FPS
        }
    }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(300.dp)
    ) {
        // Draw black background
        drawRect(Color.Black)

        fireworks.forEach { firework ->
            if (!firework.exploded) {
                // Draw rising firework
                drawCircle(
                    color = firework.color,
                    radius = 6f,
                    center = Offset(firework.x, firework.y)
                )
            } else {
                // Draw explosion particles
                firework.particles.forEach { particle ->
                    drawCircle(
                        color = particle.color.copy(alpha = particle.alpha),
                        radius = 4f,
                        center = Offset(particle.x, particle.y)
                    )
                }
            }
        }
    }
}
