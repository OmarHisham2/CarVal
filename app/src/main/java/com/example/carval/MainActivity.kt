package com.example.carval

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickListener()
    }

    private fun clickListener()
    {
        val pCar = findViewById<MaterialCardView>(R.id.purchase_button)
        val sCar = findViewById<MaterialCardView>(R.id.sell_button)

        pCar.setOnClickListener {
            openPurchaseActivity()
        }

        sCar.setOnClickListener {
            openSellActivity()
        }
    }

    private fun openPurchaseActivity()
    {
        startActivity(Intent(this@MainActivity,purchaseCar::class.java))
    }
    private fun openSellActivity()
    {
        startActivity(Intent(this@MainActivity,sellCar::class.java))

    }
}