package com.example.paintmemoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import com.example.paintmemoryapp.models.Card
import com.example.paintmemoryapp.models.DeckOfPairs

class GameActivity : AppCompatActivity() {
    var numberOfMoves = 0
    var cardsNumber = 0
    var savedArrayOfTags = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var gameTurnTextView = findViewById<TextView>(R.id.gameTurnTextView)
        gameTurnTextView.text = ("Turno: $numberOfMoves")
        val gameBackToMenuButton =
            findViewById<Button>(R.id.gameBackToMenuButton).setOnClickListener {
                val gameHardBackToMenuIntent = Intent(this, MainActivity::class.java)
                startActivity(gameHardBackToMenuIntent)
            }
        val bundle: Bundle? = intent.extras
        val difficultySetting = bundle?.getString("difficulty")
        val listOfPlayedCards = mutableListOf<Card>()
        val deckOfPairs = DeckOfPairs()
        val arrayOfSavedPairsEasy = ArrayList(deckOfPairs.listOfDrawablesTagPairsShuffledEasyMode)
        when (difficultySetting){
            "Easy" -> startGameEasyMode(deckOfPairs.listOfDrawablesTagPairsShuffledEasyMode, listOfPlayedCards, gameTurnTextView, savedInstanceState)
            "Hard" -> startGameHardMode(deckOfPairs.listOfDrawablesTagPairsShuffledHardMode, listOfPlayedCards, gameTurnTextView, savedInstanceState)
        }
    }

    private fun startGameEasyMode(
        deckOfPairs: List<Pair<Int, String>>,
        listOfPlayedCards: MutableList<Card>,
        gameTurnTextView: TextView,
        savedInstanceState: Bundle?,
    ) {
        val rowToDelete = findViewById<TableRow>(R.id.fourthTableRow)
        rowToDelete.forEach {
            it.visibility = View.INVISIBLE
        }
        var counter = 0
        val listOfCardsEasyMode = listOf(
            Card(findViewById(R.id.gameCard1)),
            Card(findViewById(R.id.gameCard2)),
            Card(findViewById(R.id.gameCard3)),
            Card(findViewById(R.id.gameCard4)),
            Card(findViewById(R.id.gameCard5)),
            Card(findViewById(R.id.gameCard6))
        )

        listOfCardsEasyMode.forEach { card ->
            card.drawable = deckOfPairs[counter].first
            card.tag = deckOfPairs[counter].second
            counter++
        }
        if (savedInstanceState != null){
            savedArrayOfTags = savedInstanceState.getStringArrayList("cardTag")!!
            listOfCardsEasyMode.filter { it.tag in savedArrayOfTags }.forEach { card -> card.imageView.setImageDrawable(getDrawable(card.drawable)) }
        }
        listOfCardsEasyMode.forEach { card ->
            card.imageView.setOnClickListener {
                card.imageView.setImageDrawable(getDrawable(card.drawable))
                listOfPlayedCards.add(card)
                isListFull(listOfPlayedCards, listOfCardsEasyMode, gameTurnTextView, savedInstanceState)
            }
        }
    }
    private fun startGameHardMode(
        deckOfPairs: List<Pair<Int, String>>,
        listOfPlayedCards: MutableList<Card>,
        gameTurnTextView: TextView,
        savedInstanceState: Bundle?,
    ) {
        var counter = 0
        val listOfCardsHardMode = listOf(
            Card(findViewById(R.id.gameCard1)),
            Card(findViewById(R.id.gameCard2)),
            Card(findViewById(R.id.gameCard3)),
            Card(findViewById(R.id.gameCard4)),
            Card(findViewById(R.id.gameCard5)),
            Card(findViewById(R.id.gameCard6)),
            Card(findViewById(R.id.gameCard7)),
            Card(findViewById(R.id.gameCard8))
        )
        listOfCardsHardMode.forEach { card ->
            card.drawable = deckOfPairs[counter].first
            card.tag = deckOfPairs[counter].second
            counter++
        }
        listOfCardsHardMode.forEach { card ->
            card.imageView.setOnClickListener {
                card.imageView.setImageDrawable(getDrawable(card.drawable))
                listOfPlayedCards.add(card)
                isListFull(
                    listOfPlayedCards,
                    listOfCardsHardMode,
                    gameTurnTextView,
                    savedInstanceState,
                )
            }
        }
    }

    private fun isListFull(
        listOfPlayedCards: MutableList<Card>,
        listOfCards: List<Card>,
        gameTurnTextView: TextView,
        savedInstanceState: Bundle?,
    ) {
        if (listOfPlayedCards.size == 2) {
            numberOfMoves++
            gameTurnTextView.setText("Turno: $numberOfMoves")
            areCardsRepeated(listOfPlayedCards, listOfCards, savedInstanceState)
        }
    }

    private fun areCardsRepeated(
        listOfPlayedCards: MutableList<Card>,
        listOfCards: List<Card>,
        savedInstanceState: Bundle?,
    ) {
        if (listOfPlayedCards[0].imageView.id == listOfPlayedCards[1].imageView.id){
            val sameCardAlertDialog = AlertDialog.Builder(this)
                .setMessage("No puedes usar la misma carta dos veces").show()
            listOfPlayedCards.forEach { card -> card.imageView.setImageDrawable(getDrawable(card.card_back)) }
            listOfPlayedCards.clear()
        } else {
            compareCardTags(listOfPlayedCards, listOfCards, savedInstanceState)
        }
    }

    private fun compareCardTags(
        listOfPlayedCards: MutableList<Card>,
        listOfCards: List<Card>,
        savedInstanceState: Bundle?,
    ) {
        if (listOfPlayedCards[0].tag == listOfPlayedCards[1].tag){
            Toast.makeText(this, "Son iguales", Toast.LENGTH_SHORT).show()
            listOfPlayedCards.forEach { card -> card.imageView.isEnabled = false }
            savedArrayOfTags.add(listOfPlayedCards[0].tag)
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putStringArrayList("cardTag", savedArrayOfTags)
    }

}
