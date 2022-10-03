package com.example.paintmemoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class PointsCountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_count)
        val bundle: Bundle? = intent.extras
        val score = bundle?.getInt("score")
        val difficulty = bundle?.getString("difficulty")
        val pointsCountPlayAgainButton = findViewById<Button>(R.id.pointsCountPlayAgainButton).setOnClickListener {
            when (difficulty){
                "Easy" -> {
                    val backToEasyGameIntent = Intent(this, GameEasyActivity::class.java)
                    startActivity(backToEasyGameIntent)
                }
                "Hard" -> {
                    val backToHardGameIntent = Intent(this, GameHardActivity::class.java)
                    startActivity(backToHardGameIntent)
                }
            }
        }
        val pointsCountTextView = findViewById<TextView>(R.id.pointsCountTextView).setText("Has tardado $score turnos")
        val pointscountBackToMenuButton = findViewById<Button>(R.id.pointsCountBackToMenuButton).setOnClickListener {
            val backToMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(backToMenuIntent)
        }
    }
}