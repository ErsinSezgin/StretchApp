package com.unnamed_ltd.ersinsezgin.stretchapp

/**
 * Created by ersinsezgin on 10/13/17.
 */

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.widget.ProgressBar
import java.util.*
import kotlin.concurrent.timerTask


class StretchActivity : AppCompatActivity() {


    lateinit var secondTV : AppCompatTextView
    lateinit var messageTV : AppCompatTextView
    lateinit var progressBar : ProgressBar
    lateinit var schedules: MutableList<Schedule>
    var seconds: Int = 0 // amount of seconds left for current workout
    var cursor: Int = 0 //index of the current workout
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stretch)

        val reps = intent.getIntExtra("reps", 3)
        val duration = intent.getIntExtra("duration", 3)
        val rest = intent.getIntExtra("rest", 3)
        val shouldSwitch = intent.getBooleanExtra("shouldSwitch", false)

        schedules = createSchedule(reps, duration, rest, shouldSwitch)

        secondTV = findViewById<AppCompatTextView>(R.id.seconds_text)
        messageTV = findViewById<AppCompatTextView>(R.id.message_text)
        progressBar = findViewById<ProgressBar>(R.id.workout_progress)
        seconds = schedules.get(cursor).amount
        messageTV.text = schedules.get(cursor).action
        progressBar.progress = 0

        startCount()
    }

    fun scheduleNext(){
        cursor++
        if (cursor < schedules.size) {
            seconds = schedules.get(cursor).amount
            messageTV.text = schedules.get(cursor).action
            secondTV.text = seconds.toString()
            progressBar.progress = 0
            startCount()
        } else {
            stopCount()
        }
    }

    fun startCount(){
        timer = Timer()
        timer.schedule(timerTask {
            runOnUiThread {
                if (this@StretchActivity.seconds > 0) {
                    this@StretchActivity.secondTV.text = this@StretchActivity.seconds.toString()
                    val total = schedules.get(cursor).amount
                    progressBar.progress = (100 * (seconds.toDouble() / total)).toInt()
                    this@StretchActivity.seconds--
                } else {
                    timer.cancel()
                    scheduleNext()
                }
            }
        }, 0, 1000)
    }

    fun stopCount(){
        secondTV.text = ""
        timer.cancel()
        // Update UI and finish workout
    }

    fun createSchedule(
            reps: Int, duration: Int, rest: Int, switch: Boolean
    ) : MutableList<Schedule> {
        var list : MutableList<Schedule> = mutableListOf()
        for (i in 1..reps){
            if (switch) {
                list.add(Schedule("Right Side", duration))
                list.add(Schedule("Rest", rest))
                list.add(Schedule("Left Side", duration))
                list.add(Schedule("Rest", rest))
            } else {
                list.add(Schedule("Stretch", duration))
                list.add(Schedule("Rest", rest))
            }
        }
        list.add(Schedule("Finished", 0))

        return list
    }


}
