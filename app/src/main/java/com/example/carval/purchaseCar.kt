package com.example.carval

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.carval.ml.LinearRegression1
import com.example.carval.ml.SvrModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class purchaseCar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_car)

        val fuel_Types = resources.getStringArray(R.array.FuelType)
        val fuelType_dropdown = findViewById<Spinner>(R.id.Fueltype_dropdown)
        val search = findViewById<Button>(R.id.Search_button)

        search.setOnClickListener {
            calc()
        }



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

    private fun calc() {
        val model = SvrModel.newInstance(applicationContext)
       // val model = LinearRegression1.newInstance(applicationContext)
        val values = arrayOf(
            floatArrayOf(2019.0f, 6.5e6f, 3.0f, 1.0f,
                46.956f, 5998.0f, 560.0f, 10.0f, 281060.0f, 195520.0f),
            floatArrayOf(1998.0f, 171.0f, 0.0f, 0.0f,  6.4f, 624.0f,
                34.2f, 2.0f, 3884.392f, 537.68f
            )
        )

        val byteBuffer = ByteBuffer.allocateDirect(9 * 4) // Allocate space for 9 floats (4 bytes each)
        byteBuffer.order(ByteOrder.nativeOrder()) // Set the byte order based on the system architecture
        //testing
         val inputData = floatArrayOf(2011.0f, 46000.0f, 3.0f, 0.0f, 18.20f, 1199.0f, 88.70f, 5.0f, 10521.42f)
      // val inputData = floatArrayOf(2010.0f, 72000.0f, 0.0f, 0.0f, 1.0f, 37.24f, 998.0f, 58.16f, 5.0f, 6287.342f)
       // val inputData = floatArrayOf(2013.0f, 40670.0f, 4.0f, 1.0f, 15.20f, 1968.0f, 140.80f, 	5.0f, 35618.0f)
       //val inputData = floatArrayOf(2014.0f, 27365.0f, 4.0f, 0.0f,  28.40f, 1248.0f, 	74.80f, 5.0f, 9629.36f)

        for (i in 0..8) {
           byteBuffer.putFloat(inputData[i])
          //  byteBuffer.putFloat(calculateZScore(inputData[i],values[0][i],values[1][i]))
        }
        byteBuffer.rewind()
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 9), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val value = outputFeature0.floatArray[0]
        //val value =  calculateXScore(outputFeature0.floatArray[0],values[0][9],values[1][9])



       // Log.d("wtf","${values[0][10]}")
        Log.d("fuck", "index: 1 price is ${value}")

        // Releases model resources if no longer used.
        model.close()

        

    }

    private fun calculateZScore(sample: Float, max: Float,min: Float): Float {
        val z = (sample-min)/(max-min)
        return z
    }

    private fun calculateXScore(sample: Float, max: Float,min: Float):Float{
        val x = sample*(max-min) + min
        return x
    }


}