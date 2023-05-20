package com.example.carval

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carval.ml.SvrModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class sellCar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_car)

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
        val title = findViewById<TextView>(R.id.Sell_title)
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
            // For manual and auto choices
            var selectedRadioButtonId: Int = transmissionRadioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId)
                val string: String = selectedRadioButton.text.toString()

            } else {

                    automaticButton.error = "Please select a transmission type"
                    val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                    transmissionRadioGroup.startAnimation(shake)
            }

            if (selectedRadioButtonId == 213136180) // Manual
            {
                automaticButton.requestFocus()
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
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                fueltypeDropDown.startAnimation(shake)
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
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                modelYear.startAnimation(shake)
            } else

            {
                inputData[0] = selectedModelYear
            }

            if (selectedDrivenKM <= 0f) // Driven KMs Error Handling -- Need min value from data set
            {
                drivenKM.requestFocus()
                drivenKM.error = "Please enter a valid value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                drivenKM.startAnimation(shake)
            } else
            {
                inputData[1] = selectedDrivenKM
            }

            if (selectedMileage <= 0) {
                mileage.requestFocus()
                mileage.error = "Please enter a valid value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                mileage.startAnimation(shake)
            } else
            {
                inputData[4] = selectedMileage
            }
            if (selectedEngine <= 0f)
            {
                engine.requestFocus()
                engine.error = "Please enter a valid value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                engine.startAnimation(shake)
            }
            else
            {

                inputData[5] = selectedEngine
            }
            if (selectedPower <= 0f) {
                power.requestFocus()
                power.error = "Please enter a valid power value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                power.startAnimation(shake)
            }
            else
            {

                inputData[6] = selectedPower
            }
            if (selectedSeats <= 0f) {
                seats.requestFocus()
                seats.error = "Please enter a valid value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                seats.startAnimation(shake)
            } else {

                inputData[7] = selectedSeats
            }
            if (selectedNewPrice <= 0f) {
                newPrice.requestFocus()
                newPrice.error = "Please enter a valid value"
                val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)
                newPrice.startAnimation(shake)
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
                var predictedPrice =  calc(inputData)
                val intent = Intent(this@sellCar, predicted_price_screen::class.java)
                    .putExtra("predictedPrice", predictedPrice)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)

               /* val myIntent = Intent(this@sellCar, predicted_price_screen::class.java)
                this@sellCar.startActivity(myIntent)*/

            }

        }
    }
    override fun finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

    }
    private fun calc(inputData: FloatArray): Float {
        val model = SvrModel.newInstance(applicationContext)
        val byteBuffer =
            ByteBuffer.allocateDirect(9 * 4) // Allocate space for 9 floats (4 bytes each)
        byteBuffer.order(ByteOrder.nativeOrder()) // Set the byte order based on the system architecture

        //testing
        //val inputData = floatArrayOf(2011.0f, 46000.0f, 3.0f, 0.0f, 18.20f, 1199.0f, 88.70f, 5.0f, 10521.42f)  Outputs :4988.328
        // val inputData = floatArrayOf(2010.0f, 72000.0f, 0.0f, 0.0f, 1.0f, 37.24f, 998.0f, 58.16f, 5.0f, 6287.342f) Outputs :57.763676

        // val inputData = floatArrayOf(2013.0f, 40670.0f, 4.0f, 1.0f, 15.20f, 1968.0f, 140.80f, 5.0f, 35618.0f) Outputs : 21641.895
        // val inputData = floatArrayOf(2014.0f, 27365.0f, 4.0f, 0.0f,  28.40f, 1248.0f, 74.80f, 5.0f, 9629.36f) Outputs :  4379.22

        for (i in 0..8) {
            byteBuffer.putFloat(inputData[i])
        }
        byteBuffer.rewind()
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 9), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val value = outputFeature0.floatArray[0] //output


        Log.d("testing42", "Expected price is $value")

        // Releases model resources if no longer used.
        model.close()
        return value;

    }


}

