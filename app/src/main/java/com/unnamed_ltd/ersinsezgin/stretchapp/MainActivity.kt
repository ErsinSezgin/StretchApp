package com.unnamed_ltd.ersinsezgin.stretchapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageButton
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {

    lateinit var repetitionTV : AppCompatEditText
    lateinit var stretchTV : AppCompatEditText
    lateinit var restTV : AppCompatEditText

    lateinit var toggle : ToggleButton

    lateinit var play : AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play = findViewById<AppCompatImageButton>(R.id.start_button)
        repetitionTV = findViewById<AppCompatEditText>(R.id.repetition_edit_text)
        stretchTV = findViewById<AppCompatEditText>(R.id.stretch_text)
        restTV = findViewById<AppCompatEditText>(R.id.rest_time_edit_text)

        toggle = findViewById<ToggleButton>(R.id.toggleButton)


        play.setOnClickListener {
            val intent = Intent(this, StretchActivity::class.java)
            intent.putExtra("reps", this@MainActivity.repetitionTV.text.toString().toInt())
            intent.putExtra("duration", this@MainActivity.stretchTV.text.toString().toInt())
            intent.putExtra("shouldSwitch", this@MainActivity.toggle.isChecked)
            intent.putExtra("rest", this@MainActivity.restTV.text.toString().toInt())
            startActivity(intent)
        }

    }
}
