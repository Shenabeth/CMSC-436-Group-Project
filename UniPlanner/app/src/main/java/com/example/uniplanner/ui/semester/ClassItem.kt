package com.example.uniplanner.ui.semester

import android.content.Intent
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClassItem {

    var className : String? = String()
    var credits = 0
    var location : String? = String()
    private val TAG = "UniPlanner"


    internal constructor(className: String, credits: Int, location: String) {
        this.className = className
        this.credits = credits
        this.location = location

    }

    internal constructor(intent: Intent) {

        className = intent.getStringExtra(CLASS_NAME)
        credits = intent.getIntExtra(CREDITS, credits)
        location = intent.getStringExtra(LOCATION)

    }

    override fun toString(): String {
        return (className + ITEM_SEP + credits.toString() + ITEM_SEP + location)
    }

    companion object {
        val ITEM_SEP: String? = System.getProperty("line.separator")

        const val CLASS_NAME = "className"
        const val CREDITS = "credits"
        const val LOCATION = "location"

        fun packageIntent(intent: Intent, className: String,
                          credits: Int, location: String) {

            intent.putExtra(CLASS_NAME, className)
            intent.putExtra(CREDITS, credits)
            intent.putExtra(LOCATION, location)

        }
    }
}