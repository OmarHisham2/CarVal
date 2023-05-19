package com.example.carval

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader



class purchaseCar : AppCompatActivity() {

    var rows = ArrayList<Row>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_car)



        val fuel_Types = resources.getStringArray(R.array.FuelType)

        // User Inputs
        val modelYear = findViewById<EditText>(R.id.Model_year)
        val drivenKM = findViewById<EditText>(R.id.KM_Driven)
        val fueltypeDropDown = findViewById<Spinner>(R.id.Fueltype_dropdown)
        val transmissionRadioGroup = findViewById<RadioGroup>(R.id.Transmission)
        val manualButton = findViewById<RadioButton>(R.id.Manual_choice)
        val automaticButton = findViewById<RadioButton>(R.id.Auto_choice)
        val mileage = findViewById<EditText>(R.id.Mileage)
        val power = findViewById<EditText>(R.id.Power)
        val engine = findViewById<EditText>(R.id.Engine_cc)
        val seats = findViewById<EditText>(R.id.NSeats)
        val newPrice = findViewById<EditText>(R.id.New_price)
        val title = findViewById<TextView>(R.id.purch_title)
        //

        // Variables
        var selectedModelYear = -1.0f;
        var selectedDrivenKM = -1.0f;
        var selectedFuel = 0f;
        var selectedTransmission = 0f;
        var selectedMileage = -1.0f;
        var selectedEngine = -1.0f;
        var selectedPower = -1.0f;
        var selectedSeats = -1.0f;
        var selectedNewPrice = -1.0f;
        var selectedRadioButton: RadioButton
        //
        val search = findViewById<Button>(R.id.Search_button)

        var inputData = FloatArray(9)

        var inputDataFilled = false;


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
        fueltypeDropDown.adapter = spinnerAdapter

        fueltypeDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        
        search.setOnClickListener {
            readCSV()
            Log.d("fuck", "awel row : ${rows[1000]} ")
            /*
            // For manual and auto choices
           var selectedRadioButtonId: Int = transmissionRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId)
                val string: String = selectedRadioButton.text.toString()

            } else {
                automaticButton.error = "Please select a transmission type"
            }

            if (selectedRadioButtonId == 213136180) // Manual
            {
                automaticButton.error = null
                selectedRadioButtonId = 0;
                selectedTransmission = selectedRadioButtonId.toFloat()
                inputData[3] = selectedTransmission

            } else if (selectedRadioButtonId == 2131361793) // Auto
            {
                automaticButton.error = null
                selectedRadioButtonId = 1;
                selectedTransmission = selectedRadioButtonId.toFloat()
                inputData[3] = selectedTransmission

            }

            // Empty Handling
            if (modelYear.text.toString() != "") {
                selectedModelYear = modelYear.text.toString().toFloat()
            }
            if (drivenKM.text.toString() != "") {
                selectedDrivenKM = drivenKM.text.toString().toFloat()
            }
            if (fueltypeDropDown.selectedItemPosition != 0) {
                selectedFuel = fueltypeDropDown.selectedItemPosition.toFloat()
                inputData[2] = selectedFuel
            } else {
                val errorTextview = fueltypeDropDown.getSelectedView() as TextView
                errorTextview.setTextColor(Color.RED)
            }
            if (mileage.text.toString() != "") {
                selectedMileage = mileage.text.toString().toFloat()
            }
            if (engine.text.toString() != "") {
                selectedEngine = engine.text.toString().toFloat()
            }
            if (power.text.toString() != "") {
                selectedPower = power.text.toString().toFloat()
            }
            if (seats.text.toString() != "") {
                selectedSeats = seats.text.toString().toFloat()
            }
            if (newPrice.text.toString() != "") {
                selectedNewPrice = newPrice.text.toString().toFloat()
            }


            //Error Handling
            if (selectedModelYear < 1885f || selectedModelYear > 2025f) // Year Error Handling
            {
                modelYear.requestFocus()
                modelYear.error = "Please enter a valid year!"
            } else

            {
                inputData[0] = selectedModelYear
            }

            if (selectedDrivenKM <= 0f) // Driven KMs Error Handling -- Need min value from data set
            {
                drivenKM.requestFocus()
                drivenKM.error = "Please enter a valid value"
            } else
            {
                inputData[1] = selectedDrivenKM
            }

            if (selectedMileage <= 0) {
                mileage.requestFocus()
                mileage.error = "Please enter a valid value"
            } else
            {
                inputData[4] = selectedMileage
            }
            if (selectedEngine <= 0f)
            {
                engine.requestFocus()
                engine.error = "Please enter a valid value"
            }
            else
            {

                inputData[5] = selectedEngine
            }
            if (selectedPower <= 0f) {
                power.requestFocus()
                power.error = "Please enter a valid power value"
            }
            else
            {

                inputData[6] = selectedPower
            }
            if (selectedSeats <= 0f) {
                seats.requestFocus()
                seats.error = "Please enter a valid value"
            } else {

                inputData[7] = selectedSeats
            }
            if (selectedNewPrice <= 0f) {
                newPrice.requestFocus()
                newPrice.error = "Please enter a valid value"
            } else
            {
                inputData[8] = selectedNewPrice
            }

            for (i in 0..8)
            {
                inputDataFilled = true
                if (inputData[i] == 0f)
                {
                    if (i != 2 && i != 3)
                    {
                        inputDataFilled = false
                        break;
                    }
                }

            }
            if(inputDataFilled) {
              //  calc(inputData)
            }

        }


             */
        }
    }

    private fun readCSV(){
        try {
            // Open the CSV file from the assets folder
            val inputStream = resources.openRawResource(R.raw.df)
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Read each line from the file
            var line: String? = reader.readLine()
            while (line != null) {
                // Split the line by comma to get individual values
                val values = line.split(",").map { it.trim() }
                val row = Row(values[1],values[2].toInt(),values[3].toInt(),values[4].toInt(),
                values[5].toInt(),values[6].toInt(),values[7].toFloat(),values[8].toInt(),values[9].toFloat(),
                values[10].toInt(),values[11].toFloat(),values[12].toFloat()
                )

                rows.add(row)

                // Read the next line
                line = reader.readLine()
            }

            // Close the reader
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


}