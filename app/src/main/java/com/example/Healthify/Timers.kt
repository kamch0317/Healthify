package com.example.Healthify

import Healthify.R
import Healthify.databinding.ActivityTimersBinding
import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.ActivityTimersBinding
import kotlin.math.roundToInt


class Timers : AppCompatActivity() {
    private lateinit var binding: ActivityTimersBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var totalTime = 0
    private var userSetTime = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceIntent = Intent(applicationContext, TimerFunc::class.java)
        registerReceiver(updateTime, IntentFilter(TimerFunc.TIMER_UPDATED))

        //BUTTON FUNCTIONS
        binding.secondsField.text = getString(R.string.time_used,0)

        binding.tenButton.setOnClickListener {
            defaultTimer()
        }

        binding.thirtyButton.setOnClickListener {
            userSetTime = 30
            Toast.makeText(applicationContext, "Timer have been set as 30 seconds.", LENGTH_SHORT).show()
            resetButtonColor()
            setThirtyButtonColor(1)
        }

        binding.sixtyButton.setOnClickListener {
            userSetTime = 60
            Toast.makeText(applicationContext, "Timer have been set as 60 seconds.", LENGTH_SHORT).show()
            resetButtonColor()
            setSixtyButtonColor(1)
        }

        binding.customButton.setOnClickListener {
            showDialog()
            resetButtonColor()
            setCustomButtonColor(1)
        }

        binding.backButtonTimer.setOnClickListener{finish()}
        // BUTTON TEXT COLORS

        //3 main functions

        binding.startButton.setOnClickListener { startStopTimer() }

        binding.stopButton.setOnClickListener { stopTimer() }

        binding.resetButton.setOnClickListener { resetTimer() }

        binding.saveButton.setOnClickListener{ saveToReminder()}

        binding.bmiButtonTimer.setOnClickListener { bmi() }

        binding.reminderButtonTimer.setOnClickListener { reminder() }

        //binding.backButtonTimer.setOnClickListener { home() }

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerFunc.TIME_EXTRA, 0.0)
            binding.secondsText.text =
                getString(R.string.zero_second_1, getTimeStringFromDouble(time))
            totalTime = time.roundToInt()

            if( totalTime == userSetTime){
                Toast.makeText(applicationContext,"Times Up!",LENGTH_SHORT).show()
                stopTimer()
            }
        }
    }

    fun getTotalTime(): String {
        return binding.secondsField.text.toString()
    }

    private fun setTenButtonColor(number: Int){
        if(number == 1){
            binding.tenButton.setTextColor(Color.parseColor("#000000"))
        }
        else{
            binding.tenButton.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun setThirtyButtonColor(number: Int){
        if(number == 1){
            binding.thirtyButton.setTextColor(Color.parseColor("#000000"))
        }
        else{
            binding.thirtyButton.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun setSixtyButtonColor(number: Int){
        if(number == 1){
            binding.sixtyButton.setTextColor(Color.parseColor("#000000"))
        }
        else{
            binding.sixtyButton.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun setCustomButtonColor(number: Int){
        if(number == 1){
            binding.customButton.setTextColor(Color.parseColor("#000000"))
        }
        else{
            binding.customButton.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private fun resetButtonColor(){
        setTenButtonColor(0)
        setThirtyButtonColor(0)
        setSixtyButtonColor(0)
        setCustomButtonColor(0)
    }

    private fun defaultTimer(){
        userSetTime = 10
        Toast.makeText(applicationContext, "Timer have been set as 10 seconds.", LENGTH_SHORT).show()
        resetButtonColor()
        setTenButtonColor(1)
    }

    private fun getTimeStringFromDouble(time: Double): String {

        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        totalTime = seconds
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerFunc.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        stopService(serviceIntent)
        timerStarted = false
        binding.secondsField.text = getString(R.string.time_used,totalTime)
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("Seconds")

        val input = EditText(this)
        input.hint = "Enter seconds"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton(
            "Done",
            DialogInterface.OnClickListener {
                    dialog, which -> var seconds = input.text.toString()
                userSetTime= seconds.toInt()
                Toast.makeText(applicationContext, "Timer have been set as $userSetTime seconds.", LENGTH_SHORT).show()
            })

        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()


    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        defaultTimer()
        binding.secondsText.text = getString(R.string.zero_second_1, getTimeStringFromDouble(time))
        binding.secondsField.text = getString(R.string.time_used,0)
    }

    private fun saveToReminder() {
        val time= binding.secondsField.text
        val intent = Intent(this, Reminder::class.java)
        intent.putExtra("totalTime",time)
        startActivity(intent)
    }

//    private fun home() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }

    private fun bmi() {
        val intent = Intent(this, BMICalculator::class.java)
        startActivity(intent)
    }

    private fun reminder() {
        val intent = Intent(this, Reminder::class.java)
        startActivity(intent)
    }
}