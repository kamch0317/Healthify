package com.example.Healthify

import Healthify.R
import Healthify.databinding.ActivityBmicalculatorBinding
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Healthify.databinding.ActivityBmicalculatorBinding
import kotlinx.android.synthetic.main.activity_bmicalculator.*
import java.text.NumberFormat
import kotlin.math.pow

class BMICalculator : AppCompatActivity() {

    private lateinit var binding: ActivityBmicalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateBMI()
        }

        binding.timerButton.setOnClickListener {
            timer()
        }

        binding.reminderButton.setOnClickListener {
            reminder()
        }


//        binding.backButton.setOnClickListener {
//            home()
//        }

        bottom_nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    home()

                }

                R.id.tools -> {
                    tools()


                }

                R.id.workout -> {
                    workout()

                }

                R.id.foodplan -> {
                    foodplan()

                }

                R.id.usernav -> {
                    usersetting()

                }


            }
            true
        }
    }

    private fun calculateBMI() {
        val weight = binding.weightInput.text
        val height = binding.heightInput.text

        val weightValue = weight.toString().toDoubleOrNull()
        val heightValue = height.toString().toDoubleOrNull()

        binding.noSatisfiedQuote.setBackgroundResource(R.drawable.round_border)
        binding.moderateQuote.setBackgroundResource(R.drawable.round_border)
        binding.satisfiedQuote.setBackgroundResource(R.drawable.round_border)

        binding.noSatisfiedQuote.setTextColor(Color.parseColor("#000000"))
        binding.moderateQuote.setTextColor(Color.parseColor("#000000"))
        binding.satisfiedQuote.setTextColor(Color.parseColor("#000000"))

        if (weightValue == null || heightValue == null) {
            Toast.makeText(
                this,
                "Please enter weight and height to calculate BMI!",
                Toast.LENGTH_SHORT
            ).show()
            binding.bmiResult.setTextColor(Color.parseColor("#917D7D"))
            binding.bmiResult.text = getString(R.string.bmi_result, "kg/m²")
            val ori = "Your BMI level is"
            binding.bmiSentence.text = getString(R.string.bmi_sentence, "$ori")
            return
        }

        val squareHeight: Double = heightValue.pow(2)

        val bmi: Double = weightValue / squareHeight
        val roundedBmi = String.format("%.2f", bmi).toDouble()
        val formatBmi = NumberFormat.getNumberInstance().format(roundedBmi)
        binding.bmiResult.setTextColor(Color.parseColor("#000000"))
        binding.bmiResult.text = getString(R.string.bmi_result, "$formatBmi kg/m²")

        when {
            bmi >= 30 -> {
                binding.noSatisfiedQuote.setBackgroundColor(Color.parseColor("#6804EC"))
                binding.noSatisfiedQuote.setTextColor(Color.parseColor("#FFFFFF"))
                val overweight = "You are Obese!"
                binding.bmiSentence.text = getString(R.string.bmi_sentence, "$overweight")
                binding.bmiSentence.setTextColor(Color.parseColor("#f01831"))
            }
            bmi < 16.5 -> {
                binding.noSatisfiedQuote.setBackgroundColor(Color.parseColor("#6804EC"))
                binding.noSatisfiedQuote.setTextColor(Color.parseColor("#FFFFFF"))
                val underweight = "You are Underweight!"
                binding.bmiSentence.text = getString(R.string.bmi_sentence, "$underweight")
                binding.bmiSentence.setTextColor(Color.parseColor("#f01831"))
            }
            bmi in 25.0..29.9 -> {
                binding.moderateQuote.setBackgroundColor(Color.parseColor("#6804EC"))
                binding.moderateQuote.setTextColor(Color.parseColor("#FFFFFF"))
                val slightOverweight = "You are slightly overweight."
                binding.bmiSentence.text = getString(R.string.bmi_sentence, "$slightOverweight")
                binding.bmiSentence.setTextColor(Color.parseColor("#1331f0"))
            }
            bmi in 16.5..18.5 -> {
                binding.moderateQuote.setBackgroundColor(Color.parseColor("#6804EC"))
                binding.moderateQuote.setTextColor(Color.parseColor("#FFFFFF"))
                val slightUnderweight = "You are slightly underweight."
                binding.bmiSentence.text = getString(R.string.bmi_sentence, "$slightUnderweight")
                binding.bmiSentence.setTextColor(Color.parseColor("#1331f0"))
            }
            bmi in 18.5..24.9 -> {
                binding.satisfiedQuote.setBackgroundColor(Color.parseColor("#6804EC"))
                binding.satisfiedQuote.setTextColor(Color.parseColor("#FFFFFF"))
                val normal = "Congrats, your BMI is normal!"
                binding.bmiSentence.text = getString(R.string.bmi_sentence, "$normal")
                binding.bmiSentence.setTextColor(Color.parseColor("#20c920"))
            }
        }
    }

    private fun timer(){
        val intent = Intent(this, Timers::class.java)
        startActivity(intent)
    }

    private fun reminder(){
        val intent = Intent(this, Reminder::class.java)
        startActivity(intent)
    }

    private fun home() {
        val name = intent.getStringExtra("username")
        val context = binding.bottomNav.context
        val intent = Intent(context, Home::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }

    private fun tools() {
        val name = intent.getStringExtra("username")
        val context = binding.bottomNav.context
        val intent = Intent(context, BMICalculator::class.java)
        intent.putExtra("username", name);
        startActivity(intent)
    }


    private fun workout() {
        val name = intent.getStringExtra("username")
        val context = binding.bottomNav.context
        val intent = Intent(context, WorkoutMain::class.java)
        intent.putExtra("username", name);
        startActivity(intent)
    }

    private fun foodplan() {
        val name = intent.getStringExtra("username")
        val context = binding.bottomNav.context
        val intent = Intent(context, SelectedPage::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }



    private fun usersetting() {
        val name = intent.getStringExtra("username")
        val context = binding.bottomNav.context
        val intent = Intent(context, Usersetting::class.java)
        intent.putExtra("username", name);
        startActivity(intent)

    }



}