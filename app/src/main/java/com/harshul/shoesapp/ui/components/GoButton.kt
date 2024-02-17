package com.harshul.shoesapp.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun GoButton(onNextClick : () -> Unit) {
    Surface(
        modifier = Modifier.wrapContentSize(),
        color = Color.Transparent
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "Fading Circles")

        val radiusMultiplierInnerCircle by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 3500
                    0.9f at 250
                    1f at 500
                    1f at 3500
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(2000)
            ),
            label = "radius multiplier"
        )

        val radiusMultiplierOuterCircle by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.4f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 3500
                    1f at 500
                    1.4f at 3500
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(2000)
            ),
            label = "radius multiplier"
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 3500
                    0f at 500
                    1f at 501
                    0f at 3500
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(2000)
            ),
            label = "alpha value"
        )

        Column(
            modifier = Modifier
                .size(200.dp)
                .clickable { onNextClick() }
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = (size.width / 3) * radiusMultiplierOuterCircle,
                        style = Stroke(width = 2.dp.toPx()),
                        alpha = alpha
                    )
                    drawCircle(
                        color = Color.Black,
                        radius = (size.width / 3) * radiusMultiplierInnerCircle
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Go", color = Color.White, fontSize = 24.sp)
        }
    }
}