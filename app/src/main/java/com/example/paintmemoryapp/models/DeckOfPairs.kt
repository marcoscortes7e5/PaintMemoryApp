package com.example.paintmemoryapp.models

import com.example.paintmemoryapp.R


class DeckOfPairs {
    val listOfDrawablesTagPairsHardMode = listOf(Pair(R.drawable.eraser_front_card,"eraser"),
        Pair(R.drawable.eraser_front_card,"eraser"),
        Pair(R.drawable.pencil_front_card,"pencil"),
        Pair(R.drawable.pencil_front_card,"pencil"),
        Pair(R.drawable.spray_front_card,"spray"),
        Pair(R.drawable.spray_front_card,"spray"),
        Pair(R.drawable.bucket_front_card,"bucket"),
        Pair(R.drawable.bucket_front_card,"bucket"))
    val listOfDrawablesTagPairsShuffledHardMode = listOfDrawablesTagPairsHardMode.shuffled()

    val listOfDrawablesTagPairsEasyMode = listOf(Pair(R.drawable.eraser_front_card,"eraser"),
        Pair(R.drawable.eraser_front_card,"eraser"),
        Pair(R.drawable.pencil_front_card,"pencil"),
        Pair(R.drawable.pencil_front_card,"pencil"),
        Pair(R.drawable.spray_front_card,"spray"),
        Pair(R.drawable.spray_front_card,"spray"))
    val listOfDrawablesTagPairsShuffledEasyMode = listOfDrawablesTagPairsEasyMode.shuffled()
}