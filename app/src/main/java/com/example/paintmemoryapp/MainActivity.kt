package com.example.paintmemoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listOfDifficultySetting = listOf("Selecciona dificultad","Easy", "Hard", "Hardcore")
        val mainMenuDifficultySpinner = findViewById<Spinner>(R.id.mainMenuDifficultySpinner)
        if (mainMenuDifficultySpinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, listOfDifficultySetting
            )
            mainMenuDifficultySpinner.adapter = adapter
        }
            val mainMenuPlayButton =
                findViewById<Button>(R.id.mainMenuPlayButton).setOnClickListener {
                    when (mainMenuDifficultySpinner.selectedItem){
                        listOfDifficultySetting[0] -> {
                            AlertDialog.Builder(this).setMessage("Selecciona una dificultad de la lista").show()
                        }
                        listOfDifficultySetting[1] -> {
                            val mainMenuGameEasyStartIntent = Intent(this, GameEasyActivity::class.java)
                                .putExtra("difficulty", mainMenuDifficultySpinner.selectedItem.toString())
                            startActivity(mainMenuGameEasyStartIntent)
                        }
                        listOfDifficultySetting[2] -> {
                            val mainMenuGameHardStartIntent = Intent(this, GameHardActivity::class.java)
                                .putExtra("difficulty", mainMenuDifficultySpinner.selectedItem.toString())
                            startActivity(mainMenuGameHardStartIntent)
                        }
                    }

                }
            val mainMenuHelpButton =
                findViewById<Button>(R.id.mainMenuHelpButton).setOnClickListener {
                    val mainMenuHelpAlertDialog = AlertDialog.Builder(this)
                    mainMenuHelpAlertDialog.setTitle("Como jugar a Paint Memory")
                    mainMenuHelpAlertDialog.setMessage(
                        "Ejecuta Paint.exe\n" +
                                "Selecciona una dificultad de la lista\n" +
                                "Encuentra las parejas para ganar")
                    mainMenuHelpAlertDialog.show()
                }
    }
}
