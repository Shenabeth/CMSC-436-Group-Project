package com.example.uniplanner.ui.userverification

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uniplanner.Cognito
import com.example.uniplanner.R

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        private const val PREF_TAG = "loginSharedPrefTag"
        private const val PASSWORD_KEY = "passwordHashKey"
        private const val USERNAME_KEY = "userHashKey"
        private const val CHECKBOX_KEY = "checkBoxKey"
        private const val GET_LOGIN_INFO_REQUEST_CODE = 99
        var success = false;
    }

    private lateinit var userName: EditText
    private lateinit var userPassword: EditText
    private lateinit var saveInfoBox: CheckBox
    private lateinit var mPrefs: SharedPreferences
    private lateinit var resulter: ActivityResultLauncher<Intent>
    private var validator = Validators()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        initializeUI() /* INITIALIZE UI CONTAINER VARS */

    }

    override fun onResume() {
        super.onResume()
        // Icon edge case fix for registration, would keep red X's if failed login before successful registration
        userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0)
        userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)
    }

    /* INITIALIZES UI VARS */
    private fun initializeUI() {
        // set views
        userName = findViewById(R.id.editTextTextUserName)
        userPassword = findViewById(R.id.editTextTextPassword)
        saveInfoBox = findViewById(R.id.saveInfoCheckBox)

        // setting value of edittexts for email/password from stored info
        mPrefs = getPreferences(Context.MODE_PRIVATE)
        userPassword.setText(mPrefs.getString(PASSWORD_KEY, ""))
        saveInfoBox.isChecked = mPrefs.getBoolean(CHECKBOX_KEY, false)

        // Activity callback listener for Register button, will set values of login page edittexts
        // with new user info on successful registration
        resulter =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) { // setting email and password boxes if result is ok
                    val pword = it.data?.getStringExtra(PASSWORD_KEY)
                    val user = it.data?.getStringExtra(USERNAME_KEY)
                    userName.setText(user)
                    userPassword.setText(pword)
                    Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
                }
            }
    }

    /* onClickListener FOR LOGIN BUTTON */
    fun onClickLogin(v: View?){
        Log.i(TAG,"Logging in...")

        /* RETRIEVE EMAIL/PASSWORD STRING */
        val username: String = userName.text.toString() // HOLDS EMAIL STRING
        val password: String = userPassword.text.toString() // HOLDS PASSWORD STRING

        userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_circle_outline_24, 0, 0, 0)
        userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0)

        /* PROCESS EMAIL/PASSWORD */

        /* SENDING TOAST IF EITHER EMAIL/PASSWORD/USER IS EMPTY */
        if(TextUtils.isEmpty(username)){
            userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        // if (TextUtils.isEmpty(email)) {
        //     userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
        //     Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
        //     return
        // }
        /* CAN DO OTHER EMAIL/PASSWORD VALIDATION HERE, SUCH AS REGEX'S FOR FORMATTING */
        // if (!validator.validEmail(email)) {
        //     userEmail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
        //     Toast.makeText(applicationContext, "Please enter valid email...", Toast.LENGTH_LONG).show()
        //     return
        // }

        if (TextUtils.isEmpty(password)) {
            userPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_cancel_24, 0, 0, 0) // makes icon red x
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        /* SAVING LOGIN INFO IF NECESSARY, OR ERASING SAVED INFO*/
        val prefEditor = mPrefs.edit()
        if(saveInfoBox.isChecked){ // if save info box is checked, save info
            prefEditor.clear()
            prefEditor.putString(PASSWORD_KEY, password)
            prefEditor.putString(USERNAME_KEY, username)
            prefEditor.putBoolean(CHECKBOX_KEY, true)
            prefEditor.commit()
        } else {
            prefEditor.clear()
            prefEditor.putString(PASSWORD_KEY, "")
            prefEditor.putString(USERNAME_KEY, "")
            prefEditor.putBoolean(CHECKBOX_KEY, false)
            prefEditor.commit()
        }

        val authentication = Cognito(getApplicationContext())
        authentication.userLogin(
            username,
            password
        )

        finish()



    }

    /* onClickListener FOR REGISTER BUTTON */
    fun onClickRegister(v: View?){
        Log.i(TAG,"Starting RegisterActivity...")
        //creating intent
        val intent = Intent(this, RegisterActivity::class.java)

        /* START RegisterActivity */
        resulter.launch(intent)

    }
}