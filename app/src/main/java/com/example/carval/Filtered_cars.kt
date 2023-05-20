package com.example.carval

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class Filtered_cars : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered_cars)


        val data = DataHolder.data // Contains outputRows

        // Car0
        val car0name = findViewById<TextView>(R.id.car0name)
        val car0usedprice = findViewById<TextView>(R.id.car0UPrice)
        val car0newprice = findViewById<TextView>(R.id.car0NewPrice)
        val car0year = findViewById<TextView>(R.id.car0year)

        val car1name = findViewById<TextView>(R.id.car1name)
        val car1usedprice = findViewById<TextView>(R.id.car1UPrice)
        val car1newprice = findViewById<TextView>(R.id.car1NewPrice)
        val car1year = findViewById<TextView>(R.id.car1year)

        val car2name = findViewById<TextView>(R.id.car2name)
        val car2usedprice = findViewById<TextView>(R.id.car2UPrice)
        val car2newprice = findViewById<TextView>(R.id.car2NewPrice)
        val car2year = findViewById<TextView>(R.id.car2year)

        val car3name = findViewById<TextView>(R.id.car3name)
        val car3usedprice = findViewById<TextView>(R.id.car3UPrice)
        val car3newprice = findViewById<TextView>(R.id.car3NewPrice)
        val car3year = findViewById<TextView>(R.id.car3year)

        val car4name = findViewById<TextView>(R.id.car4name)
        val car4usedprice = findViewById<TextView>(R.id.car4UPrice)
        val car4newprice = findViewById<TextView>(R.id.car4NewPrice)
        val car4year = findViewById<TextView>(R.id.car4year)

        val car5name = findViewById<TextView>(R.id.car5name)
        val car5usedprice = findViewById<TextView>(R.id.car5UPrice)
        val car5newprice = findViewById<TextView>(R.id.car5NewPrice)
        val car5year = findViewById<TextView>(R.id.car5year)

        val car6name = findViewById<TextView>(R.id.car6name)
        val car6usedprice = findViewById<TextView>(R.id.car6UPrice)
        val car6newprice = findViewById<TextView>(R.id.car6NewPrice)
        val car6year = findViewById<TextView>(R.id.car6year)

        val car7name = findViewById<TextView>(R.id.car7name)
        val car7usedprice = findViewById<TextView>(R.id.car7UPrice)
        val car7newprice = findViewById<TextView>(R.id.car7NewPrice)
        val car7year = findViewById<TextView>(R.id.car7year)

        val car8name = findViewById<TextView>(R.id.car8name)
        val car8usedprice = findViewById<TextView>(R.id.car8UPrice)
        val car8newprice = findViewById<TextView>(R.id.car8NewPrice)
        val car8year = findViewById<TextView>(R.id.car8year)

        val car9name = findViewById<TextView>(R.id.car9name)
        val car9usedprice = findViewById<TextView>(R.id.car9UPrice)
        val car9newprice = findViewById<TextView>(R.id.car9NewPrice)
        val car9year = findViewById<TextView>(R.id.car9year)


        val returntoMenuButton = findViewById<Button>(R.id.returntoMenu_button)
        val reFilterButton = findViewById<Button>(R.id.ReFilter_button)





        if(data[0].name != null)
        {
        car0name.text = data[0].name
        car0usedprice.text = data[0].price.toInt().toString()
        car0newprice.text = data[0].newPrice.toInt().toString()
        car0year.text = data[0].year.toString()
        }
        if(data[1].name != null)
        {
            car1name.text = data[1].name
            car1usedprice.text = data[1].price.toInt().toString()
            car1newprice.text = data[1].newPrice.toInt().toString()
            car1year.text = data[1].year.toString()
        }
        if(data[2].name != null)
        {
            car2name.text = data[2].name
            car2usedprice.text = data[2].price.toInt().toString()
            car2newprice.text = data[2].newPrice.toInt().toString()
            car2year.text = data[2].year.toString()
        }
        if(data[3].name != null)
        {
            car3name.text = data[3].name
            car3usedprice.text = data[3].price.toInt().toString()
            car3newprice.text = data[3].newPrice.toInt().toString()
            car3year.text = data[3].year.toString()
        }
        if(data[4].name != null)
        {
            car4name.text = data[4].name
            car4usedprice.text = data[4].price.toInt().toString()
            car4newprice.text = data[4].newPrice.toInt().toString()
            car4year.text = data[4].year.toString()
        }
        if(data[5].name != null)
        {
            car5name.text = data[5].name
            car5usedprice.text = data[5].price.toInt().toString()
            car5newprice.text = data[5].newPrice.toInt().toString()
            car5year.text = data[5].year.toString()
        }
        if(data[6].name != null)
        {
            car6name.text = data[6].name
            car6usedprice.text = data[6].price.toInt().toString()
            car6newprice.text = data[6].newPrice.toInt().toString()
            car6year.text = data[6].year.toString()
        }
        if(data[7].name != null)
        {
            car7name.text = data[7].name
            car7usedprice.text = data[7].price.toInt().toString()
            car7newprice.text = data[7].newPrice.toInt().toString()
            car7year.text = data[7].year.toString()
        }
        if(data[8].name != null)
        {
            car8name.text = data[8].name
            car8usedprice.text = data[8].price.toInt().toString()
            car8newprice.text = data[8].newPrice.toInt().toString()
            car8year.text = data[8].year.toString()
        }
        if(data[9].name != null)
        {
            car9name.text = data[9].name
            car9usedprice.text = data[9].price.toInt().toString()
            car9newprice.text = data[9].newPrice.toInt().toString()
            car9year.text = data[9].year.toString()
        }

        returntoMenuButton.setOnClickListener()
        {
            startActivity(Intent(this@Filtered_cars,MainActivity::class.java))
        }
        reFilterButton.setOnClickListener()
        {
            startActivity(Intent(this@Filtered_cars,purchaseCar::class.java))
        }




    }






}