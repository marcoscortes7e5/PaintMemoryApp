package com.example.paintmemoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        sleep(1000)
        setTheme(R.style.Theme_PaintMemoryApp)
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
//                            val mainMenuGameEasyStartIntent = Intent(this, GameEasyActivity::class.java)
                            val mainMenuGameEasyStartIntent = Intent(this, GameActivity::class.java)
                                .putExtra("difficulty", mainMenuDifficultySpinner.selectedItem.toString())
                            startActivity(mainMenuGameEasyStartIntent)
                        }
                        listOfDifficultySetting[2] -> {
//                            val mainMenuGameHardStartIntent = Intent(this, GameHardActivity::class.java)
                            val mainMenuGameHardStartIntent = Intent(this, GameActivity::class.java)
                                .putExtra("difficulty", mainMenuDifficultySpinner.selectedItem.toString())
                            startActivity(mainMenuGameHardStartIntent)
                        }
                        listOfDifficultySetting[3] -> {
                            AlertDialog.Builder(this).setMessage("Este modo está en construcción").show()
                        }
                    }

                }
            val mainMenuHelpButton =
                findViewById<Button>(R.id.mainMenuHelpButton).setOnClickListener {
                    val mainMenuHelpAlertDialog = AlertDialog.Builder(this)
                    mainMenuHelpAlertDialog.setTitle("Como jugar a Paint Memory")
                    mainMenuHelpAlertDialog.setMessage(
                        "Selecciona una dificultad de la lista\n" +
                                "Ejecuta Paint.exe\n" +
                                "Encuentra las parejas para ganar")
                    mainMenuHelpAlertDialog.show()
                }
    }
}
