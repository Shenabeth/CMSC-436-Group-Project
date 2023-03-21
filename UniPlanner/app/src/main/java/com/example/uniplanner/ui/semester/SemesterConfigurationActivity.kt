package com.example.uniplanner.ui.semester

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniplanner.R
import com.example.uniplanner.ui.userverification.LoginActivity
import java.text.ParseException
import java.sql.Time

// JSON
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset


class SemesterConfigurationActivity : Activity(){

    private lateinit var mAdapter: ClassAdapter
    private lateinit var mTimeframeRadioGroup: RadioGroup
    private lateinit var mSemesterName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.semester_configurator)

        startActivity(Intent(this, LoginActivity::class.java))

        mAdapter = ClassAdapter(this)

        mSemesterName = findViewById<View>(R.id.semesterName) as EditText
        loadItemsFromFile()

        val mRecyclerView = findViewById<RecyclerView>(R.id.semesterList)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        val clearSemesterButton = findViewById<Button>(R.id.clearSemesterButton)
        clearSemesterButton.setOnClickListener {
            mAdapter.clear()
            mSemesterName.setText("Enter Semester")

            var writer: PrintWriter? = null
            try {
                val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                writer = PrintWriter(BufferedWriter(OutputStreamWriter(
                    fos)))

                writer.print("Enter Semester");
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                writer?.close()
            }
        }

        val submitButton = findViewById<View>(R.id.finishButton) as Button
        submitButton.setOnClickListener {
            saveItemsToFile()
        }

    }

    // Load stored ToDoItems
    private fun loadItemsFromFile() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))

            var className: String?
            var location: String?
            var credits: Int?

            mSemesterName.setText(reader.readLine())

            do {
                className = reader.readLine()
                if(className == null) {
                    break;
                }
                credits = reader.readLine().toInt()
                location = reader.readLine()
                mAdapter.add(ClassItem(className, credits, location))
            }
            while (true)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun saveItemsToFile() {
        var writer: PrintWriter? = null
        try {
            val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            writer = PrintWriter(BufferedWriter(OutputStreamWriter(
                fos)))

            writer.println(mSemesterName.text.toString())

            for (idx in 1 until mAdapter.itemCount) {
                writer.println(mAdapter.getItem(idx))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writer?.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i(TAG, "Entered onActivityResult()")

        if(resultCode == 1){
            val className = (data?.extras?.get("CLASS_NAME")) as String
            val credits = data?.extras?.get("CREDITS") as Int
            val location  = (data?.extras?.get("LOCATION")) as String
            val classItem = ClassItem(className, credits, location)
            mAdapter.add(classItem)
        }


    }



    companion object {

        const val ADD_CLASS_ITEM_REQUEST = 0
        private const val FILE_NAME = "SemesterConfiguratorActivityData.txt"
        const val TAG = "Lab-UserInterface"

        // IDs for menu items
        private const val MENU_DELETE = Menu.FIRST
        private const val MENU_DUMP = Menu.FIRST + 1
    }
}