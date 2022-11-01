package com.example.noties.feature.presentation.notes.deep_link

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noties.common.utils.Emoji

@Composable
fun EmojiSlider(
    modifier: Modifier = Modifier,
    progressWidth: Float = 18f,
    progressColor: Color = Color(0xFFE1306C),
    emojiSizeStart: Float = 60f,
    emojiSizeEnd: Float = 80f,
    emoji: String = Emoji.loveFace
) {

    var offsetX by remember { mutableStateOf(10f) }
    var isPressed by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    val emojiSize by rememberInfiniteTransition()
        .animateFloat(
            initialValue = emojiSizeStart,
            targetValue = emojiSizeEnd,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    },
                    onDragStarted = { isPressed = true },
                    onDragStopped = { isPressed = false },
                ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
            {
                val canvasWidth = size.width
                val canvasHeight = size.height
                offsetX = offsetX.coerceIn(0f, canvasWidth)

                //Gray Line
                drawLine(
                    start = Offset(x = 0f, y = canvasHeight / 2),
                    end = Offset(x = canvasWidth, y = canvasHeight / 2),
                    color = Color.LightGray,
                    strokeWidth = progressWidth - 5,
                    cap = StrokeCap.Round
                )
                //Progress Line
                drawLine(
                    start = Offset(x = 0f, y = canvasHeight / 2),
                    end = Offset(x = offsetX, y = canvasHeight / 2),
                    color = progressColor,
                    strokeWidth = progressWidth,
                    cap = StrokeCap.Round
                )

                //Emoji and text
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        emoji,
                        offsetX - 26f,
                        (canvasHeight / 2) + 16f,
                        Paint().asFrameworkPaint().apply {
                            textSize = emojiSize
                        }
                    )
                }
            }
        }
        if (isPressed) {
            Text(
                text = emoji,
                fontSize = progress.coerceIn(20f, 80f).sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 26.dp)
                    .offset {
                        IntOffset(offsetX.toInt(), -100)
                    }
            )
        }
    }
}

@Composable
@Preview
fun previewEmojiSlider() {
    EmojiSlider()
}