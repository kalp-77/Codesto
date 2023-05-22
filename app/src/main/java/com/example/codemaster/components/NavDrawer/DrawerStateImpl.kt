package com.example.codemaster.components.NavDrawer

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val AnimationDurationMillis = 300
//private const val DrawerMaxElevation = 8f

@Stable
class DrawerStateImpl(
    override val drawerWidth: Float,
): DrawerState {
    override val backgroundTranslationX: Float
        get() = animation.value * drawerWidth

    override val backgroundAlpha: Float
        get() = .25f * animation.value

    private val animation = Animatable(0f)

    override val drawerTranslationX: Float
        get() = -drawerWidth * (1f - animation.value)

    override val drawerElevation: Float
        get() = 0 * animation.value

    override val contentScaleX: Float
        get() = 1f - .03f * animation.value

    override val contentScaleY: Float
        get() = 1f - .03f * animation.value

    override val contentTranslationX: Float
        get() = drawerWidth * animation.value

    override val contentTransformOrigin: TransformOrigin
        get() = TransformOrigin(pivotFractionX = 0f, pivotFractionY = .5f)

    override suspend fun open() {
        coroutineScope {
            launch {
                animation.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = AnimationDurationMillis,
                    )
                )
            }
        }
    }

    override suspend fun close() {
        coroutineScope {
            launch {
                animation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = AnimationDurationMillis,
                    )
                )
            }
        }
    }
}