package com.example.uniplanner.ui.semester

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import com.example.uniplanner.R
import java.text.SimpleDateFormat
import java.util.*

class AddClassActivity : FragmentActivity() {

    private lateinit var mClassNameView: TextView
    private lateinit var mCreditsView: NumberPicker
    private lateinit var mLocationView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_class)

        val np = findViewById<NumberPicker>(R.id.creditsView);
        np.maxValue = 6;
        np.minValue = 0;
        np.value = 3;

        val submitButtonView = findViewById(R.id.submitButton) as Button

        submitButtonView.setOnClickListener {
            mClassNameView = findViewById<View>(R.id.classNameView) as EditText
            mCreditsView = findViewById<View>(R.id.creditsView) as NumberPicker
            mLocationView = findViewById<View>(R.id.locationView) as EditText
            //TODO: Add class time

            val intentClassName = mClassNameView.text.toString()
            val intentCredits = mCreditsView.value
            val intentLocation = mLocationView.text.toString()

            val submitButton = findViewById<View>(R.id.submitButton) as Button
            submitButton.setOnClickListener {
                val submitIntent = Intent()
                submitIntent.putExtra("CLASS_NAME", intentClassName)
                submitIntent.putExtra("CREDITS", intentCredits)
                submitIntent.putExtra("LOCATION", intentLocation)

                setResult(1, submitIntent)
                finish()
            }
        }
    }
}