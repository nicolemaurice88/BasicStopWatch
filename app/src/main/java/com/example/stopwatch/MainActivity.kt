package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.reset_window.*
import android.widget.TextView


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.widget.Button
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


class MainActivity : AppCompatActivity() {

    var textView: TextView? = null
    var start: Button? = null
    var pause: Button? = null
    var reset: Button? = null
    var MillisecondTime: Long = 0
    var StartTime: Long = 0
    var TimeBuff: Long = 0
    var UpdateTime = 0L
    var handler: Handler? = null
    var Seconds: Int = 0
    var Minutes: Int = 0
    var MilliSeconds: Int = 0

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime / 1000).toInt()
            Minutes = Seconds / 60
            Seconds = Seconds % 60
            MilliSeconds = (UpdateTime % 1000).toInt()

            textView!!.text = ("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds))

            handler!!.postDelayed(this, 0)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById<View>(R.id.sw_timer) as TextView
        start = findViewById<View>(R.id.start_btn) as Button
        pause = findViewById<View>(R.id.pause_btn) as Button
        reset = findViewById<View>(R.id.reset_btn) as Button


        handler = Handler()

        //intialize timer
        start!!.setOnClickListener {
            StartTime = SystemClock.uptimeMillis()
            handler!!.postDelayed(runnable, 0)

            reset!!.isEnabled = false
        }
        pause!!.setOnClickListener {
            TimeBuff += MillisecondTime

            handler!!.removeCallbacks(runnable)

            reset!!.isEnabled = true
        }
        //add popup window to reset button
        reset!!.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.reset_window, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            //.setTitle(("Reset"))
            val mAlertDialog = mBuilder.show()

            mAlertDialog.pResetBtn.setOnClickListener {


                MillisecondTime = 0L
                StartTime = 0L
                TimeBuff = 0L
                UpdateTime = 0L
                Seconds = 0
                Minutes = 0
                MilliSeconds = 0

                textView!!.text = "00:00:00"
                mAlertDialog.dismiss()
            }
            mAlertDialog.pCancelWin.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


    }


}
