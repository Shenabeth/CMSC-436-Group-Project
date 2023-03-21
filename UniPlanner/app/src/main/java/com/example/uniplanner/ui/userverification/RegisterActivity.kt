package com.example.uniplanner.ui.userverification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.uniplanner.Cognito
import com.example.uniplanner.R

class RegisterActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "RegisterActivity"
        private const val PASSWORD_KEY = "passwordHashKey"
        private const val USERNAME_KEY = "userHashKey"
    }

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var userRetypedPassword: EditText
    private lateinit var userDOB: EditText
    private var validator = Validators()

    // ############################################################# Cognito connection
    var authentication: Cognito? = null
    private var userId: String? = null
    // ############################################################# End Cognito connection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        authentication = Cognito(getApplicationContext())

        initializeUI()
    }

    /* INITIALIZES UI VARS */
    private fun initializeUI() {
        userName = findViewById(R.id.editTextTextUserName)
        userEmail = findViewById(R.id.editTextTextEmailAddress)
        userPassword = findViewById(R.id.editTextTextPassword)
        userRetypedPassword = findViewById(R.id.editTextTextRePassword)
        userDOB = findViewById(R.id.editTextTextDate)
    }

    /* onClickListener FOR REGISTER BUTTON */
    fun onClickRegister(v: View?){
        Log.i(TAG,"Logging in...")

        /* RETRIEVE STRINGS */
        val user: String = userName.text.toString() // HOLDS USER NAME STRING
        val email: String = userEmail.text.toString() // HOLDS EMAIL STRING
        val password: String = userPassword.text.toString() // HOLDS PASSWORD STRING
        val reTypedPassword: String = userRetypedPassword.text.toString() // HOLDS PASSWORD STRING
        val dateOfBirth: String = userDOB.text.toString() // HOLDS DOB

        /* SENDING TOAST IF EITHER EMAIL/PASSWORD IS EMPTY */
        // line necessary for password incorrect icon change

        // Resetting red x icons to original icons
        userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0)
        userDOB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0)
        userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_email_24, 0, 0, 0)
        userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
        userRetypedPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)

        // User Name
        if(TextUtils.isEmpty(user)){ // first name field empty / OTHER NAME FILTERING CAN BE DONE HERE
            userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter a first name!", Toast.LENGTH_SHORT).show()
            return
        }

        // Last Name
        if(TextUtils.isEmpty(dateOfBirth)){ //last name field empty / OTHER NAME FILTERING CAN BE DONE HERE
            userDOB.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter a date of birth!", Toast.LENGTH_SHORT).show()
            return
        }

        // Email
        if (TextUtils.isEmpty(email)) { // email empty / OTHER EMAIL FILTERING CAN BE DONE HERE
            userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter email!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!validator.validEmail(email)) {
            userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter valid email...", Toast.LENGTH_LONG).show()
            return
        }

        // Password
        if (TextUtils.isEmpty(password)) { // password empty / OTHER PASSWORD FILTERING CAN BE DONE HERE
            userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_SHORT).show()
            return
        }
//        if (!validator.validPassword(password)) {
//            userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
//            Toast.makeText(applicationContext, "Password must contain 8 characters with one letter and one number!", Toast.LENGTH_LONG).show()
//            return
//        }

        // Re-typed Password
        if(password != reTypedPassword){ // Passwords do not match
            userRetypedPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

       if (!password.isNullOrEmpty()) {
           authentication!!.addAttribute("name", user)
           authentication!!.addAttribute("email", email)
           authentication!!.addAttribute("birthday", dateOfBirth)
           authentication!!.signUpInBackground(user, password)
       }

        /* ASSUMING THE REGISTRATION GOES THROUGH, THESE LINES SAVE THE LOGIN INFO SO THAT THEY
        *  AUTOMATICALLY APPEAR IN THE BOXES WHEN THEY GO BACK TO THE LOGIN SCREEN. THIS ALSO CLOSES
        *  THE REGISTRATION ACTIVITY ITSELF. */
        val intent = Intent()
        intent.putExtra(PASSWORD_KEY, password)
        intent.putExtra(USERNAME_KEY, user)
        setResult(RESULT_OK, intent)
        finish()
    }
}