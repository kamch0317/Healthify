package com.example.Healthify

import Healthify.R
import Healthify.databinding.ActivityReminderBinding
import android.app.*
import android.content.*
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.ActivityReminderBinding
import java.util.*
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.app.AlarmManager
import android.app.PendingIntent
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class Reminder : AppCompatActivity(){
    private lateinit var binding: ActivityReminderBinding
    private var sleepHours = 0
    private var sleepMins = 0
    private var sleepDate = 0
    private var sleepMonth = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.workoutCard.setOnClickListener{ clickWorkout() }

        binding.sleepCard.setOnClickListener{ clickSleep() }

        binding.waterCard.setOnClickListener{ clickWater() }

        binding.editButtonWorkout.setOnClickListener{ editWorkout() }

        binding.editButtonSleep.setOnClickListener{ editSleep() }

        binding.editButtonWater.setOnClickListener{ editWater() }

        binding.wakeUpButton.setOnClickListener{ wakeUp() }

        binding.bmiButtonReminder.setOnClickListener { bmi() }

        binding.timerButtonReminder.setOnClickListener { timer() }

        binding.backButtonReminder.setOnClickListener{ bmi() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        saveData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        saveData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification(){
        val intent = Intent(applicationContext, Notification :: class.java)
        val title = "Water Intake Reminder"
        val message = "Remember to intake water every 30 minutes."
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)

        var pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            // PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), AlarmManager.INTERVAL_HALF_HOUR,pendingIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(){
        val name = "Notif Channel"
        val desc = "A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        scheduleNotification()
    }

    private fun saveWorkoutTime(){
        val newTimeString = intent.getStringExtra("totalTime").toString()
        var newTime = 0
        var previousTime =binding.totalWorkoutTime.text.toString().toInt()

        if ( newTimeString == "null" )
        {
            val updated = previousTime + newTime
            val updatedTime = updated.toString().toInt()
            binding.totalWorkoutTime.text = getString(R.string.workout_time, updatedTime)
        } else {
            newTime= newTimeString.toInt()
            val updated = previousTime + newTime
            val updatedTime = updated.toString().toInt()
            binding.totalWorkoutTime.text = getString(R.string.workout_time, updatedTime)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveData(){
        var calGoal =binding.caloriesAmount.text
        var exhaustedCal =binding.exhaustedAmount.text
        var workoutTime =binding.totalWorkoutTime.text
        var requiredSleep = binding.sleepDuration.text
        var sleepTime = binding.sleepTime.text
        var waterGoal =binding.waterAmount.text
        var waterIntake = binding.intakeAmount.text
        var workoutStatus = binding.workOutStatus.text
        var waterStatus = binding.waterStatus.text
        var wakeUpTime = binding.wakeUpTimeField.text
        var sleepStatus = binding.sleepStatus.text
        var sleepMinsSave = sleepMins
        var sleepHourSave = sleepHours
        var sleepDateSave = sleepDate
        var sleepMonthSave = sleepMonth

        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = currentDate.format(formatter)

        binding.caloriesAmount.text = calGoal
        binding.exhaustedAmount.text = exhaustedCal
        binding.totalWorkoutTime.text = workoutTime
        binding.sleepDuration.text = requiredSleep
        binding.sleepTime.text = sleepTime
        binding.waterAmount.text=waterGoal
        binding.intakeAmount.text = waterIntake
        binding.workOutStatus.text=workoutStatus
        binding.waterStatus.text=waterStatus
        binding.wakeUpTimeField.text = wakeUpTime
        binding.sleepStatus.text = sleepStatus

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("CAL_GOAL",calGoal.toString())
            putString("EXHAUSTED_CAL",exhaustedCal.toString())
            putString("WORKOUT_TIME",workoutTime.toString())
            putString("REQUIRED_SLEEP",requiredSleep.toString())
            putString("SLEEP_TIME",sleepTime.toString())
            putString("WATER_GOAL",waterGoal.toString())
            putString("WATER_INTAKE",waterIntake.toString())
            putString("CURRENT_DATE", formatted.toString())
            putString("WORKOUT_STATUS", workoutStatus.toString())
            putString("WATER_STATUS", waterStatus.toString())
            putString("WAKEUP_TIME", wakeUpTime.toString())
            putString("SLEEP_STATUS", sleepStatus.toString())
            putString("SLEEP_MINS_SAVED", sleepMinsSave.toString())
            putString("SLEEP_HOURS_SAVED", sleepHourSave.toString())
            putString("SLEEP_DATE_SAVED", sleepDateSave.toString())
            putString("SLEEP_MONTH_SAVED", sleepMonthSave.toString())
        }.apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val calGoal = sharedPreferences.getString("CAL_GOAL", null)
        val exhaustedCal = sharedPreferences.getString("EXHAUSTED_CAL", null)
        val workoutTime = sharedPreferences.getString("WORKOUT_TIME", null)
        val requiredSleep = sharedPreferences.getString("REQUIRED_SLEEP", null)
        val sleepTime = sharedPreferences.getString("SLEEP_TIME", null)
        val waterGoal = sharedPreferences.getString("WATER_GOAL", null)
        val waterIntake = sharedPreferences.getString("WATER_INTAKE", null)
        val previousDate = sharedPreferences.getString("CURRENT_DATE", null)
        val workoutStatus = sharedPreferences.getString("WORKOUT_STATUS", null)
        val waterStatus = sharedPreferences.getString("WATER_STATUS", null)
        val wakeupTime = sharedPreferences.getString("WAKEUP_TIME", null)
        val sleepStatus = sharedPreferences.getString("SLEEP_STATUS", null)
        val sleepHoursSaved = sharedPreferences.getString("SLEEP_HOURS_SAVED", null)
        val sleepMinsSaved = sharedPreferences.getString("SLEEP_MINS_SAVED", null)
        val sleepDateSaved = sharedPreferences.getString("SLEEP_DATE_SAVED", null)
        val sleepMonthSaved = sharedPreferences.getString("SLEEP_MONTH_SAVED", null)

        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = currentDate.format(formatter).toString()

        if(previousDate != formatted){
            binding.totalWorkoutTime.text = getString(R.string.workout_time,0)
            binding.workOutStatus.text = getString(R.string.progress_text)
            binding.waterStatus.text = getString(R.string.progress_text)
            binding.exhaustedAmount.text = getString(R.string.exhausted_amount,0)
            binding.intakeAmount.text = getString(R.string.intake_amount,0)
            binding.wakeUpTimeField.text = getString(R.string.wakeUpField,"")
            binding.sleepStatus.text = getString(R.string.progress_text)
        }

        else{
            val workOutTime = workoutTime.toString().toInt()
            binding.totalWorkoutTime.text = getString(R.string.workout_time,workOutTime)
            val workoutProgress = workoutStatus.toString()
            val waterProgress = waterStatus.toString()
            binding.workOutStatus.text = workoutProgress
            binding.waterStatus.text = waterProgress
            binding.exhaustedAmount.text = exhaustedCal
            binding.intakeAmount.text = waterIntake
            binding.wakeUpTimeField.text = wakeupTime
            binding.sleepStatus.text = sleepStatus
        }

        saveWorkoutTime()

        binding.caloriesAmount.text = calGoal
        binding.sleepDuration.text = requiredSleep
        binding.sleepTime.text = sleepTime
        binding.waterAmount.text=waterGoal

        if(sleepHoursSaved!="" || sleepMinsSaved!="" ||sleepDateSaved!="" ||sleepMonthSaved!=""){
            Toast.makeText(applicationContext,"Please choose your sleep time before you press wake up button.", LENGTH_SHORT).show()
        }
        else{
            sleepHours = sleepHoursSaved.toString().toInt()
            sleepMins = sleepMinsSaved.toString().toInt()
            sleepDate = sleepDateSaved.toString().toInt()
            sleepMonth = sleepMonthSaved.toString().toInt()
        }
    }

    private fun loadSleepTime( date : Int , month : Int ,hours : Int , mins: Int){
        sleepHours = hours
        sleepMins  = mins
        sleepDate = date
        sleepMonth = month
    }

    private fun wakeUp(){
        val c = Calendar.getInstance()
        var endHour = c.get(Calendar.HOUR_OF_DAY)
        var endMinute = c.get(Calendar.MINUTE)
        var endDate = c.get(Calendar.DAY_OF_MONTH)
        var endMonth = c.get(Calendar.MONTH)
        var year = c.get(Calendar.YEAR)
        var startHour = sleepHours
        var startMinute = sleepMins
        var startDate = sleepDate
        var startMonth = sleepMonth

        val endTime = GregorianCalendar(year, endMonth, endDate, endHour, endMinute)
        val startTime = GregorianCalendar(year, startMonth, startDate,startHour,startMinute)

        val millis1 = startTime.timeInMillis
        val millis2 = endTime.timeInMillis

        val diff = millis2 - millis1

        var diffMinutes = diff / (60 * 1000)

        var diffHours = diff / (60 * 60 * 1000)

        if(diffMinutes>=60){
            val remainder = diffMinutes % 60
            diffMinutes = remainder
        }

        //Toast.makeText(applicationContext, "$diffHours" + "H" + "" + "$diffMinutes" + "Minutes" , LENGTH_SHORT).show()

        var amPm = if(endHour < 12){
            "A.M."
        } else{
            "P.M."
        }
        val wakeUpTimeString = String.format("%02d:%02d %s", endHour, endMinute,amPm)

        binding.wakeUpTimeField.text = getString(R.string.wakeUpField,wakeUpTimeString)

        val requiredSleep = binding.sleepDuration.text.toString().toInt()
        val requiredValue = requiredSleep*60*60*1000
        if(diff >= requiredValue){
            binding.sleepStatus.text = getString(R.string.sleepStatusSuccess)
        }
        else {
            binding.sleepStatus.text = getString(R.string.sleepStatusFail)
        }
    }

    private fun clickWorkout(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Calories Exhausted :")

        val input = EditText(this)
        input.hint = "exhausted kCal"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
            val exhausted = input.text.toString()
            if(exhausted == ""){
                dialog.cancel()
            }
            else{
                val exhaustedValue = exhausted.toInt()
                binding.exhaustedAmount.text = getString(R.string.exhausted_amount,exhaustedValue)
                val value=binding.caloriesAmount.text.toString()
                if( value != ""){
                    val goal = value.toInt()
                    if(exhaustedValue >= goal ){
                        binding.workOutStatus.text = getString(R.string.completed_text)
                    }
                    else{
                        binding.workOutStatus.text = getString(R.string.progress_text)
                    }
                } }})

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun clickSleep(){
        val cal = Calendar.getInstance()
        var hourChose = 0
        var minChose = 0
        var amPm = ""

        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker :TimePicker , hour :Int, minute :Int ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            hourChose = hour
            minChose = minute

            amPm = if(hourChose < 12){
                "A.M."
            } else{
                "P.M."
            }
            val finalTimeString = String.format("%02d:%02d %s", hourChose, minChose,amPm)
            binding.sleepTime.text = getString(R.string.sleep_time, finalTimeString)
            val date = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            loadSleepTime(date,month,hourChose,minChose)
        }
        TimePickerDialog(this,timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun clickWater(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

        builder.setTitle("Water Intake Amount :")

        val water = EditText(this)
        water.hint = "amount(ml)"
        water.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(water)

        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
            val waterIntake = water.text.toString()
            if(waterIntake == ""){
                dialog.cancel()
            }
            else{
                val waterIntakeValue = water.text.toString().toInt()
                binding.intakeAmount.text = getString(R.string.intake_amount,waterIntakeValue)
                val value=binding.waterAmount.text.toString()
                if( value != ""){
                    val goal = binding.waterAmount.text.toString().toInt()
                    if(waterIntakeValue >= goal ){
                        binding.waterStatus.text = getString(R.string.completed_text)
                    }
                    else{
                        binding.waterStatus.text = getString(R.string.progress_text)
                    }
                } }})

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun editWorkout(){

        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Workout Settings")

        val goal = EditText(this)
        goal.hint = "Calories Goal (kCal)"
        goal.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(goal)

        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
            val calGoal = goal.text.toString()
            if(calGoal == ""){
                dialog.cancel()
            }
            else{
                val calGoalValue = calGoal.toInt()
                binding.caloriesAmount.text = getString(R.string.calories_amount,calGoalValue)
                val value=binding.exhaustedAmount.text.toString()
                if( value != ""){
                    val exhausted = value.toInt()
                    if(exhausted >= calGoalValue ){
                        binding.workOutStatus.text = getString(R.string.completed_text)
                    }
                    else{
                        binding.workOutStatus.text = getString(R.string.progress_text)
                    }
                } }})

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun editSleep(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Sleep Settings")

        val sleepTime = EditText(this)
        sleepTime.hint = "sleeping hours (H)"
        sleepTime.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(sleepTime)

        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
            val requiredSleep = sleepTime.text.toString()
            if(requiredSleep == ""){
                dialog.cancel()
            }
            else{
                val requiredSleepValue = requiredSleep.toInt()
                binding.sleepDuration.text = getString(R.string.sleep_duration,requiredSleepValue)
                binding.sleepStatus.text = getString(R.string.progress_text)
            }})

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }



    private fun editWater(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Water Settings")

        val waterTarget = EditText(this)
        waterTarget.hint = "Water Goal (ml)"
        waterTarget.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(waterTarget)

        builder.setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, which ->
            val waterGoal = waterTarget.text.toString()
            if(waterGoal == ""){
                dialog.cancel()
            }
            else{
                val waterGoal = waterTarget.text.toString().toInt()
                binding.waterAmount.text = getString(R.string.water_amount,waterGoal)
                val value=binding.intakeAmount.text.toString()
                if( value != ""){
                    val intake = value.toInt()
                    if(intake >= waterGoal ){
                        binding.workOutStatus.text = getString(R.string.completed_text)
                    }
                    else{
                        binding.workOutStatus.text = getString(R.string.progress_text)
                    }
                } }})

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun home(){
        saveData()
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bmi(){
        saveData()
        val intent = Intent(this, BMICalculator::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timer(){
        saveData()
        val intent = Intent(this, Timers::class.java)
        startActivity(intent)
    }


}