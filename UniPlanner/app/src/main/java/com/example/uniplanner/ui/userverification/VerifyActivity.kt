package com.example.uniplanner.ui.userverification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.uniplanner.R

class VerifyActivity : AppCompatActivity() {
    private lateinit var verificationCode: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
    }

    /* INITIALIZES UI VARS */
    private fun initializeUI() {
        verificationCode = findViewById(R.id.editTextTextVerify)
    }

    fun onClickVerify(v: View?){
        val code: String = verificationCode.text.toString() // HOLDS CODE STRING

        //authentication!!.confirmUser(userId, etConfCode.getText().toString().replace(" ", ""))
    }


}