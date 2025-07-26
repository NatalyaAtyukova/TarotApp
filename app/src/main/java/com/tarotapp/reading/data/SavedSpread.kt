package com.tarotapp.reading.data

import com.tarotapp.reading.TarotCard

data class SavedSpread(
    val date: String,
    val cards: List<TarotCard>
) 