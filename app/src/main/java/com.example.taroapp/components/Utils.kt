package com.example.tarotapp.components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.InputStream

fun loadImageFromAssets(context: Context, fileName: String): ImageBitmap? {
    return try {
        val inputStream: InputStream = context.assets.open(fileName)
        BitmapFactory.decodeStream(inputStream).asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}