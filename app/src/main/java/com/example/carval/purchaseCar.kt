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
import com.example.carval.ml.SvrModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


class purchaseCar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_car)

        val transmissions = resources.getStringArray(R.array.Transmission)
        val transmission_dropdownmenu = findViewById<Spinner>(R.id.transmission_dropdown)
        val search = findViewById<Button>(R.id.Search_button)

        search.setOnClickListener{
            calc()
        }



        // Spinner choices and text color //
        val spinnerAdapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transmissions) {

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
        transmission_dropdownmenu.adapter = spinnerAdapter

        transmission_dropdownmenu.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

    private fun calc(){
        val model = SvrModel.newInstance(applicationContext)

        val byteBuffer = ByteBuffer.allocateDirect(10 * 4) // Allocate space for 10 floats (4 bytes each)
        byteBuffer.order(ByteOrder.nativeOrder()) // Set the byte order based on the system architecture
        //testing
        val inputData = floatArrayOf(2010.0f, 72000.0f, 3.0f, 0.0f, 1.0f, 37.240f, 998.0f, 58.16f, 5.0f, 60000f)
        for (value in inputData) {
            byteBuffer.putFloat(value)
        }
        byteBuffer.rewind()
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 10), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val value = outputFeature0.floatArray

        Log.d("fuck", "calc:${value[0]}")

        // Releases model resources if no longer used.
        model.close()

        

    }

}