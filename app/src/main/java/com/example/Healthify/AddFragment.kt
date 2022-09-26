package com.example.Healthify

import Healthify.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.Healthify.model.Workout2
import com.example.Healthify.viewmodel.WorkoutViewModel
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_add2.*
import kotlinx.android.synthetic.main.fragment_add2.view.*
import java.text.DecimalFormat

class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private val df: DecimalFormat = DecimalFormat("0")
    lateinit var t1: String
    lateinit var spinner: Spinner
    lateinit var slider: Slider
    lateinit var datePick: EditText
    lateinit var timePick: EditText
    private lateinit var muserViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add2, container, false)

        muserViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        spinner = view.findViewById(R.id.spinner23)
        slider = view.findViewById(R.id.slider)
        datePick = view.findViewById(R.id.wdate)
        timePick = view.findViewById(R.id.wtime)

        datePick.setRawInputType(InputType.TYPE_NULL)
        timePick.setRawInputType(InputType.TYPE_NULL)

        pickDate()

        pickTime()//

        view.addbutton.setOnClickListener {
            insertDataToDatabase()
        }

        view.cancelbutton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        sliderFunct()

        spinnerFunc()

        return view

    }

    private fun pickDate() {
        view.apply {
            datePick.setOnClickListener {
                // create new instance of DatePickerFragment
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                // we have to implement setFragmentResultListener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        datePick.setText(date)
                    }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }

    }

    private fun pickTime() {
        view.apply {
            timePick.setOnClickListener {
                // create new instance of DatePickerFragment
                val timePickerFragment = TimePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                // we have to implement setFragmentResultListener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY_TIME",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY_TIME") {
                        val time = bundle.getString("SELECTED_TIME")
                        timePick.setText(time)
                    }
                }

                // show
                timePickerFragment.show(supportFragmentManager, "TimePickerFragment")
            }
        }

    }

    private fun insertDataToDatabase() {
        val workDate = wdate.text.toString()
        val workTime = wtime.text.toString()
        val workName = t1
        val workReps = slider_value.text.toString()

        if (inputCheck(workDate, workTime, workName, workReps)) {
            val workout = Workout2(0, workDate, workTime, workName, workReps)
            muserViewModel.addWorkout2(workout)
            Toast.makeText(requireContext(), "Added!!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext(), "Please fill in every field !", Toast.LENGTH_LONG)
                .show()
        }

    }

    fun spinnerFunc() {
        val adapter2 = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.workouts,
            android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter2
        spinner.onItemSelectedListener = this
    }

    private fun inputCheck(
        workDate: String,
        workTime: String,
        workName: String,
        workReps: String
    ): Boolean {
        return !(TextUtils.isEmpty(workDate) || TextUtils.isEmpty(workTime) || TextUtils.isEmpty(
            workName
        ) || TextUtils.isEmpty(workReps))
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        t1 = p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun sliderFunct() {

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: Slider) {
                val slideValue = slider.value

                Log.i("Slider Value", df.format(slideValue))
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: Slider) {
                val value = slider.value

                view!!.slider_value.text = df.format(value)

                slider.addOnChangeListener { slider, _, _ ->
                    val value = slider.value
                    view?.slider_value!!.text = df.format(value)
                }
            }

        })
    }


}


