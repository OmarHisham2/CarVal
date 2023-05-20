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
    var outRows = ArrayList<Row>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_car)


        val fuel_Types = resources.getStringArray(R.array.FuelType)

        // User Inputs
        val minModelYear = findViewById<EditText>(R.id.min_model_year)
        val maxModelYear = findViewById<EditText>(R.id.max_model_year)

        val minDrivenKM = findViewById<EditText>(R.id.min_KM)
        val maxDrivenKM = findViewById<EditText>(R.id.max_KM)

        val fueltypeDropDown = findViewById<Spinner>(R.id.Fueltype_dropdown)

        val transmissionRadioGroup = findViewById<RadioGroup>(R.id.Transmission_RG)
        val ownerRadioGroup = findViewById<RadioGroup>(R.id.ownerNo_RG)

        val manualButton = findViewById<RadioButton>(R.id.Manual_choice)
        val automaticButton = findViewById<RadioButton>(R.id.Auto_choice)

        val minMileage = findViewById<EditText>(R.id.min_mileage)
        val maxMileage = findViewById<EditText>(R.id.max_mileage)

        val minPower = findViewById<EditText>(R.id.min_power)
        val maxPower = findViewById<EditText>(R.id.max_power)

        val minEngine = findViewById<EditText>(R.id.min_engine)
        val maxEngine = findViewById<EditText>(R.id.max_engine)

        val seats = findViewById<EditText>(R.id.NSeats)

        val minNewPrice = findViewById<EditText>(R.id.min_newprice)
        val maxNewPrice = findViewById<EditText>(R.id.max_newprice)

        val minPrice = findViewById<EditText>(R.id.min_price)
        val maxPrice = findViewById<EditText>(R.id.max_price)

        //

        // Variables
        var selectedminModelYear = -1.0f;
        var selectedmaxModelYear = Int.MAX_VALUE.toFloat()

        var selectedminDrivenKM = -1.0f;
        var selectedmaxDrivenKM = Int.MAX_VALUE.toFloat()

        var selectedFuel = -1.0f;
        var selectedTransmission = -1.0f;
        var selectedOwner = -1.0f;

        var selectedminMileage = -1.0f;
        var selectedmaxMileage = Float.MAX_VALUE

        var selectedminEngine = -1.0f;
        var selectedmaxEngine = Int.MAX_VALUE.toFloat()

        var selectedminPower = -1.0f;
        var selectedmaxPower = Int.MAX_VALUE.toFloat()

        var selectedSeats = -1.0f;

        var selectedminNewPrice = -1.0f;
        var selectedmaxNewPrice = Float.MAX_VALUE

        var selectedminPrice = -1.0f;
        var selectedmaxPrice = Float.MAX_VALUE

        var selectedTransmissionType: RadioButton

        var selectedOwnerType: RadioButton
        //
        val search = findViewById<Button>(R.id.Search_button)

        var inputData = FloatArray(18)

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


            // For manual and auto choices
           var selectedRadioButtonId: Int = transmissionRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedTransmissionType = findViewById(selectedRadioButtonId)
                val string: String = selectedTransmissionType.text.toString()
            }

            if (selectedRadioButtonId == 213136180) // Manual
            {
                automaticButton.error = null
                selectedRadioButtonId = 0;
                selectedTransmission = selectedRadioButtonId.toFloat()
                inputData[5] = selectedTransmission

            } else if (selectedRadioButtonId == 2131361793) // Auto
            {
                automaticButton.error = null
                selectedRadioButtonId = 1;
                selectedTransmission = selectedRadioButtonId.toFloat()
                inputData[5] = selectedTransmission
            }




            //For Owner Type

            var selectedRadioButtonId2: Int = ownerRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId2 != -1) {
                selectedOwnerType = findViewById(selectedRadioButtonId2)
                val string: String = selectedOwnerType.text.toString()
            }
            if (selectedRadioButtonId2 == 2131361924) // Owner 1
            {
                selectedRadioButtonId2 = 1;
                selectedOwner = selectedRadioButtonId2.toFloat()
                inputData[6] = selectedOwner

            }
            else if (selectedRadioButtonId2 == 2131361925) // Owner 2
            {
                selectedRadioButtonId2 = 2;
                selectedOwner = selectedRadioButtonId2.toFloat()
                inputData[6] = selectedOwner

            }
            else if (selectedRadioButtonId2 == 2131361926) // Owner 3
            {
                selectedRadioButtonId2 = 3;
                selectedOwner = selectedRadioButtonId2.toFloat()
                inputData[6] = selectedOwner

            }
            else if (selectedRadioButtonId2 == 2131361927) // Owner 4
            {
                selectedRadioButtonId2 = 4;
                selectedOwner = selectedRadioButtonId2.toFloat()
                inputData[6] = selectedOwner
            }
            // Empty Handling
            if (minModelYear.text.toString() != "") {
                selectedminModelYear = minModelYear.text.toString().toFloat()
            }
            if (maxModelYear.text.toString() != "") {
                selectedmaxModelYear = maxModelYear.text.toString().toFloat()
            }


            if (minDrivenKM.text.toString() != "") {
                selectedminDrivenKM = minDrivenKM.text.toString().toFloat()
            }
            if (maxDrivenKM.text.toString() != "") {
                selectedmaxDrivenKM = maxDrivenKM.text.toString().toFloat()
            }


            if (fueltypeDropDown.selectedItemPosition != 0) {
                selectedFuel = fueltypeDropDown.selectedItemPosition.toFloat()
                inputData[4] = selectedFuel
            }

            if (minMileage.text.toString() != "") {
                selectedminMileage = minMileage.text.toString().toFloat()
            }
            if (maxMileage.text.toString() != "") {
                selectedmaxMileage = maxMileage.text.toString().toFloat()
            }


            if (minEngine.text.toString() != "") {
                selectedminEngine = minEngine.text.toString().toFloat()
            }
            if (maxEngine.text.toString() != "") {
                selectedmaxEngine = maxEngine.text.toString().toFloat()
            }

            if (minPower.text.toString() != "") {
                selectedminPower = minPower.text.toString().toFloat()
            }
            if (maxPower.text.toString() != "") {
                selectedmaxPower = maxPower.text.toString().toFloat()
            }

            if (seats.text.toString() != "") {
                selectedSeats = seats.text.toString().toFloat()
            }

            if (minNewPrice.text.toString() != "") {
                selectedminNewPrice = minNewPrice.text.toString().toFloat()
            }
            if (maxNewPrice.text.toString() != "") {
                selectedmaxNewPrice = maxNewPrice.text.toString().toFloat()
            }

            if (minPrice.text.toString() != "") {
                selectedminPrice = minPrice.text.toString().toFloat()
            }
            if (minPrice.text.toString() != "") {
                selectedminPrice = minPrice.text.toString().toFloat()
            }


            //Error Handling
            if ((selectedminModelYear < 1885f || selectedminModelYear > 2025f )&& selectedminModelYear != -1f) // Year Error Handling
            {
                minModelYear.requestFocus()
                minModelYear.error = "Please enter a valid year!"
            } else
            {
                inputData[0] = selectedminModelYear
            }
            if ((selectedmaxModelYear < 1885f || selectedmaxModelYear > 2025f )&& selectedmaxModelYear != Int.MAX_VALUE.toFloat()) // Year Error Handling
            {
                minModelYear.requestFocus()
                minModelYear.error = "Please enter a valid year!"
            } else
            {
                inputData[1] = selectedmaxModelYear
            }

            if (selectedSeats <= 0f && selectedSeats != -1f) {
                seats.requestFocus()
                seats.error = "Please enter a valid value"
            }
            else
            {inputData[13] = selectedSeats}

            inputData[2] = selectedminDrivenKM
            inputData[3] = selectedmaxDrivenKM
            inputData[7] = selectedminMileage
            inputData[8] = selectedmaxMileage
            inputData[9] = selectedminEngine
            inputData[10] = selectedmaxEngine
            inputData[11] = selectedminPower
            inputData[12] = selectedmaxPower
            inputData[14] = selectedminNewPrice
            inputData[15] = selectedmaxNewPrice
            inputData[16] = selectedminPrice
            inputData[17] = selectedmaxPrice



            filter(inputData[0].toInt(),inputData[1].toInt(),inputData[2].toInt(),inputData[3].toInt(),inputData[4].toInt(),inputData[5].toInt(),
                inputData[6].toInt(), inputData[7],inputData[8],inputData[9].toInt(),inputData[10].toInt(),inputData[11].toInt(),
                inputData[12].toInt(), inputData[13].toInt(), inputData[14],inputData[15],inputData[16],inputData[17],)


            Log.d("fuck","awel row : ${outRows[0]}")
        }
    }

    private fun readCSV() {
        try {
            // Open the CSV file from the assets folder
            val inputStream = resources.openRawResource(R.raw.df)
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Read each line from the file
            var line: String? = reader.readLine()
            while (line != null) {
                // Split the line by comma to get individual values
                val values = line.split(",").map { it.trim() }
                val row = Row(
                    values[1],
                    values[2].toInt(),
                    values[3].toInt(),
                    values[4].toInt(),
                    values[5].toInt(),
                    values[6].toInt(),
                    values[7].toFloat(),
                    values[8].toInt(),
                    values[9].toFloat(),
                    values[10].toInt(),
                    values[11].toFloat(),
                    values[12].toFloat()
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

    private fun filter(
        minYear: Int = -1,
        maxYear: Int = Int.MAX_VALUE,
        minKilometers: Int = -1,
        maxKilometers: Int = Int.MAX_VALUE,
        fuelType: Int = -1,
        trans: Int = -1,
        ownerType: Int = -1,
        minMileage: Float = -1f,
        maxMileage: Float = Float.MAX_VALUE,
        minEngine: Int = -1,
        maxEngine: Int = Int.MAX_VALUE,
        minPower: Int = -1,
        maxPower: Int = Int.MAX_VALUE,
        seats: Int = -1,
        minNewPrice: Float = -1f,
        maxNewPrice: Float = Float.MAX_VALUE,
        minPrice: Float = -1f,
        maxPrice: Float = Float.MAX_VALUE

    ) {
        var counter: Int = 0
        for (row in rows) {
            if (row.year in minYear..maxYear && row.kilometers in minKilometers..maxKilometers
                && row.mileage in minMileage..maxMileage && row.engine in minEngine..maxEngine
                && (row.power >= minPower && row.power <= maxPower) && (row.price in minPrice..maxPrice)
                && (row.newPrice in minNewPrice..maxNewPrice) && counter < 10
            ) {
                if ((row.fuelType == fuelType || fuelType == -1) && (row.trans == trans || trans == -1) && (row.ownerType == ownerType || ownerType == -1)
                    && (seats == -1 || row.seats == seats)
                ) {
                    outRows.add(row)
                    counter++
                }
            }
        }
    }
}