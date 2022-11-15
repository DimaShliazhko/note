package com.example.noties.feature.presentation.notes.deep_link


import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CanvasSample(
    modifier: Modifier = Modifier,
    second: (Int) -> Unit,
    minute: (Int) -> Unit,
    hour: (Int) -> Unit,
) {
    var data by remember { mutableStateOf(Calendar.getInstance()) }
    var second by remember { mutableStateOf(data.get(Calendar.SECOND)) }
    var minute by remember { mutableStateOf(data.get(Calendar.MINUTE)) }
    var hour by remember { mutableStateOf(data.get(Calendar.HOUR)) }

    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        delay(timeMillis = 1000)
        data = Calendar.getInstance()
        second = data.get(Calendar.SECOND)
        second(second)
        minute = data.get(Calendar.MINUTE)
        minute(minute)
        hour = data.get(Calendar.HOUR)
        hour(hour)

    }

    val time by remember {
        mutableStateOf("$hour + $minute + $second")
    }

    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 5000)
        )
    }

    val colorGradient = listOf(
        Color.Red,
        Color.Green,
        Color.Blue
    )

    val colorGradientClock = listOf(
        Color.Blue,
        Color.Blue.copy(alpha = 0.8f),
        Color.Blue.copy(alpha = 0.5f),
    )


    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )


    val offsetRepeat by animateFloatAsState(

        targetValue = 100f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val angle by infiniteTransition.animateFloat(
        initialValue = second / 60f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val angleSecond by infiniteTransition.animateFloat(
        initialValue = (6f * data.get(Calendar.SECOND)) - 90f,
        targetValue = ((6f * data.get(Calendar.SECOND)) - 90f) + 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(60000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val angleHour by infiniteTransition.animateFloat(
        initialValue = (30f * data.get(Calendar.HOUR)) - 90,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(36000 * 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val angleMinute by infiniteTransition.animateFloat(
        initialValue = (6f * data.get(Calendar.MINUTE)) - 90f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(60000 * 60, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val angleRepeat by animateFloatAsState(
        targetValue = 360f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )


    val brush = linearGradient(
        colors = colorGradientClock,
        tileMode = TileMode.Mirror
    )
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "hour = $hour", modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)
        Text(text = "minute = $minute", modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)
        Text(text = "second = $second", modifier = Modifier.fillMaxWidth(), fontSize = 16.sp)

        Canvas(
            modifier = Modifier
                .size(60.dp)
                .padding(24.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = 100.dp.toPx()

            drawCircle(
                brush = brush,
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = radius,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
            (0..12).forEachIndexed { index, i ->
                rotate(index * 30f, pivot = Offset(x = canvasWidth / 2, y = canvasHeight / 2)) {
                    drawLine(
                        color = Color.Gray,
                        strokeWidth = 10f,
                        start = Offset(x = (canvasWidth / 2) + radius - 25.dp.toPx(), y = canvasHeight / 2),
                        end = Offset(x = canvasWidth + radius - 15.dp.toPx(), y = canvasHeight / 2),
                        cap = StrokeCap.Round,
                    )
                }


            }
            rotate(
                degrees = angleSecond,
                pivot = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
            ) {
                drawLine(
                    color = Color.Red,
                    strokeWidth = 10f,
                    start = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    end = Offset(x = canvasWidth + radius - 15.dp.toPx(), y = canvasHeight / 2),
                    cap = StrokeCap.Round,
                )
            }

            rotate(
                degrees = angleMinute,
                pivot = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
            ) {
                drawLine(
                    color = Color.Blue,
                    strokeWidth = 10f,
                    start = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    end = Offset(x = canvasWidth + radius - 15.dp.toPx(), y = canvasHeight / 2),
                    cap = StrokeCap.Round,
                )
            }

            rotate(
                degrees = angleHour,
                pivot = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
            ) {
                drawLine(
                    color = Color.Green,
                    strokeWidth = 10f,
                    start = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    end = Offset(x = canvasWidth + radius- 40.dp.toPx(), y = canvasHeight / 2),
                    cap = StrokeCap.Round,
                )
            }

        }
        Canvas(
            modifier = Modifier
                .size(60.dp)
        ) {
/*            drawArc(
                brush = brush,
                startAngle = 0f,
                sweepAngle = angleSecond,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )*/
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

/*            drawArc(
                brush = SolidColor(Color.Black),
                startAngle = -90f,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(35f)
            )*/
/*
            val path = Path().apply {
                moveTo(100f, 100f)
                quadraticBezierTo(400f, 400f, 100f, 400f)
            }

            val outPath = Path()
            PathMeasure().apply {
                setPath(path, false)
                getSegment(0f, pathPortion.value * length, outPath)
            }
            drawPath(
                path = outPath,
                color = Color.Red,
                style = Stroke(width = 5.dp.toPx())
            )*/

/*            drawIntoCanvas {
                val paint = Paint().asFrameworkPaint()
                paint.apply {
                    isAntiAlias = true
                    textSize = 55f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                it.nativeCanvas.drawText(
                    "$time",
                    size.width / 2,
                    size.height / 2,
                    paint
                )
            }*/
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun addAnimation(duration: Int = 800): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with (slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
            )
}