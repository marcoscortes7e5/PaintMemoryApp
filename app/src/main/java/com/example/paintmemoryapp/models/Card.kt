package com.example.paintmemoryapp.models

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.paintmemoryapp.R

class Card(var imageView: ImageView) {
    val card_back: Int = R.drawable.app_icon_small
    var drawable: Int = R.drawable.app_icon_small
    val isTurned: Boolean = false
    var tag: String = ""
}