package com.example.carval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.roundToInt

class predicted_price_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predicted_price_screen)

        val predictedPriceText = findViewById<TextView>(R.id.predictedPrice_text)
        val predictedPrice = this.intent.getFloatExtra("predictedPrice",-2f);


        val predictedPricetext = getString(R.string.Price_subtitle,predictedPrice)

        predictedPriceText.text = getString(R.string.Price_subtitle,predictedPrice)
    }

}