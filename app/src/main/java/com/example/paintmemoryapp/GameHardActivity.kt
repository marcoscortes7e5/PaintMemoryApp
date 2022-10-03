package com.example.paintmemoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.paintmemoryapp.models.Card

class GameHardActivity : AppCompatActivity() {
    var numberOfMoves = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_hard)
        var gameHardTurnTextView = findViewById<TextView>(R.id.gameHardTurnTextView)
        gameHardTurnTextView.text = ("Turno: 0")
        val gameHardBackToMenuButton = findViewById<Button>(R.id.gameHardBackToMenuButton).setOnClickListener {
            val gameHardBackToMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(gameHardBackToMenuIntent)
        }
        val listOfDrawablesTagPairs = listOf(Pair(R.drawable.eraser_front_card,"eraser"),
                                            Pair(R.drawable.eraser_front_card,"eraser"),
                                            Pair(R.drawable.pencil_front_card,"pencil"),
                                            Pair(R.drawable.pencil_front_card,"pencil"),
                                            Pair(R.drawable.spray_front_card,"spray"),
                                            Pair(R.drawable.spray_front_card,"spray"),
                                            Pair(R.drawable.bucket_front_card,"bucket"),
                                            Pair(R.drawable.bucket_front_card,"bucket"))
        val listOfDrawablesTagPairsShuffled = listOfDrawablesTagPairs.shuffled()
        val listOfPlayedCards = mutableListOf<Card>()
        var counter = 0
        val listOfCards = listOf(
                                Card(findViewById(R.id.gameHardCard1)),
                                Card(findViewById(R.id.gameHardCard2)),
                                Card(findViewById(R.id.gameHardCard3)),
                                Card(findViewById(R.id.gameHardCard4)),
                                Card(findViewById(R.id.gameHardCard5)),
                                Card(findViewById(R.id.gameHardCard6)),
                                Card(findViewById(R.id.gameHardCard7)),
                                Card(findViewById(R.id.gameHardCard8))
        )
        listOfCards.forEach { card ->
            card.drawable = listOfDrawablesTagPairsShuffled[counter].first
            card.tag = listOfDrawablesTagPairsShuffled[counter].second
            counter++
        }
        listOfCards.forEach { card ->
            card.imageView.setOnClickListener {
                card.imageView.setImageDrawable(getDrawable(card.drawable))
                listOfPlayedCards.add(card)
                isListFull(listOfPlayedCards, listOfCards, gameHardTurnTextView)
            }
        }

    }

    private fun isListFull(
        listOfPlayedCards: MutableList<Card>,
        listOfCards: List<Card>,
        gameHardTurnTextView: TextView
    ) {
        if (listOfPlayedCards.size == 2) {
            numberOfMoves++
            gameHardTurnTextView.setText("Turno: $numberOfMoves")
            areCardsRepeated(listOfPlayedCards, listOfCards)
        }
    }

    private fun areCardsRepeated(listOfPlayedCards: MutableList<Card>, listOfCards: List<Card>) {
        if (listOfPlayedCards[0].imageView.id == listOfPlayedCards[1].imageView.id){
            val sameCardAlertDialog = AlertDialog.Builder(this)
                .setMessage("No puedes usar la misma carta dos veces").show()
            listOfPlayedCards.forEach { card -> card.imageView.setImageDrawable(getDrawable(card.card_back)) }
            listOfPlayedCards.clear()
        } else {
            compareCardTags(listOfPlayedCards, listOfCards)
        }
    }

    private fun compareCardTags(listOfPlayedCards: MutableList<Card>, listOfCards: List<Card>) {
        if (listOfPlayedCards[0].tag == listOfPlayedCards[1].tag){
            Toast.makeText(this, "Son iguales", Toast.LENGTH_SHORT).show()
            listOfPlayedCards.forEach { card -> card.imageView.isEnabled = false }
            listOfPlayedCards.clear()

        } else {
            Toast.makeText(this, "Son diferentes", Toast.LENGTH_SHORT).show()
            listOfPlayedCards.forEach { card -> card.imageView.setImageDrawable(getDrawable(card.card_back)) }
            listOfPlayedCards.clear()
        }
        isGameFinished(listOfCards)
    }

    private fun isGameFinished(listOfCards: List<Card>) {
        var numberOfDisabledCards = 0
        listOfCards.forEach { card ->
            if (!card.imageView.isEnabled){
                numberOfDisabledCards++
                if (numberOfDisabledCards == listOfCards.size){
                    val bundle: Bundle? = intent.extras
                    val difficultySetting = bundle?.getString("difficulty")
                    val pointsCountIntent = Intent(this, PointsCountActivity::class.java)
                        .putExtra("score", numberOfMoves).putExtra("difficulty", difficultySetting)
                    startActivity(pointsCountIntent)
                }
            }
        }
    }
}