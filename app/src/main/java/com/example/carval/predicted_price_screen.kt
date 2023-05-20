package com.example.carval

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.roundToInt

class predicted_price_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predicted_price_screen)

        val predictedPriceText = findViewById<TextView>(R.id.predictedPrice_text)
        val predictedPrice = this.intent.getFloatExtra("predictedPrice",-2f);

        val returntoMenuButton = findViewById<Button>(R.id.returntoMenu_button)
        val reEvaluateButton = findViewById<Button>(R.id.reEvaluateButton)


        val predictedPricetext = getString(R.string.Price_subtitle,predictedPrice)

        predictedPriceText.text = getString(R.string.Price_subtitle,predictedPrice)

        returntoMenuButton.setOnClickListener()
        {
            startActivity(Intent(this@predicted_price_screen,MainActivity::class.java))
        }
        reEvaluateButton.setOnClickListener()
        {
            startActivity(Intent(this@predicted_price_screen,sellCar::class.java))
        }


    }

}