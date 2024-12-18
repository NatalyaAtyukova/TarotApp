package com.example.tarotapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarotapp.tarotCards
import kotlin.random.Random

@Composable
fun SingleCardScreen() {
    val context = LocalContext.current
    var selectedCard by remember { mutableStateOf<Pair<String, String>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedCard == null) {
            Text("Выберите карту Таро", fontSize = 24.sp)
        } else {
            val imageBitmap = loadImageFromAssets(context, selectedCard!!.first)
            imageBitmap?.let {
                Image(bitmap = it, contentDescription = "Tarot Card", modifier = Modifier.size(200.dp))
            }
            Text(selectedCard!!.second, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            selectedCard = tarotCards[Random.nextInt(tarotCards.size)]
        }) {
            Text("Вытянуть карту")
        }
    }
}