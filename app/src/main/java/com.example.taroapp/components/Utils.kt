package com.example.tarotapp.components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.InputStream

fun loadImageFromAssets(context: Context, filePath: String): ImageBitmap? {
    return try {
        val inputStream: InputStream = context.assets.open(filePath)
        BitmapFactory.decodeStream(inputStream).asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}