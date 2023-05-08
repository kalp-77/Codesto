package com.example.codemaster.components.NavDrawer

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin

@Stable
interface DrawerState {
    val drawerWidth: Float

    val drawerTranslationX: Float
    val drawerElevation: Float

    val backgroundTranslationX: Float
    val backgroundAlpha: Float

    val contentScaleX: Float
    val contentScaleY: Float
    val contentTranslationX: Float
    val contentTransformOrigin: TransformOrigin

    suspend fun open()
    suspend fun close()
}
