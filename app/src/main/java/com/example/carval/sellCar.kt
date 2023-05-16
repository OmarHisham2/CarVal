package com.example.carval

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class sellCar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_car)


        val fuel_Types = resources.getStringArray(R.array.FuelType)
        val fuelType_dropdown = findViewById<Spinner>(R.id.Fueltype_dropdown)
        val search = findViewById<Button>(R.id.Search_button)



        // Spinner choices and text color //
        val spinnerAdapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fuel_Types) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if (position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {

                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fuelType_dropdown.adapter = spinnerAdapter

        fuelType_dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                (view as TextView).setTextColor(Color.WHITE)
            }
        }
    }


}

