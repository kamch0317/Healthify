package com.example.Healthify

import Healthify.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.Healthify.model.Workout2
import com.example.Healthify.viewmodel.WorkoutViewModel
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_add2.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.text.DecimalFormat


class UpdateFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val args by navArgs<UpdateFragmentArgs>()
    private val df: DecimalFormat = DecimalFormat("0")
    lateinit var t1: String
    lateinit var updateSpinner: Spinner
    lateinit var updateSlider: Slider

    lateinit var updateDatePick: EditText
    lateinit var updateTimePick: EditText
    private lateinit var muserViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        updateSlider = view.findViewById(R.id.update_slider)
        updateSpinner = view.findViewById(R.id.update_spinner23)
        muserViewModel = ViewModelProvider(this)[WorkoutViewModel::class.java]


        updateDatePick = view.findViewById(R.id.update_wdate)
        updateTimePick = view.findViewById(R.id.update_wtime)

        updateDatePick.setRawInputType(InputType.TYPE_NULL)
        updateTimePick.setRawInputType(InputType.TYPE_NULL)

        view.update_wdate.setText(args.currentWorkout.workoutDate)
        view.update_wtime.setText(args.currentWorkout.workoutTime)
        view.update_slider_value.text = args.currentWorkout.workoutSets

        view.updatebutton.setOnClickListener {
            updateWorkout()
        }

        view.update_cancelbutton.setOnClickListener {
            cancelWorkout()
        }



        setHasOptionsMenu(true)

        pickDate()

        pickTime()

        sliderFunct()

        spinnerFunc()

        return view

    }

    fun spinnerFunc() {
        val adapter2 = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.workouts,
            android.R.layout.simple_spinner_item
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        updateSpinner.adapter = adapter2
        updateSpinner.onItemSelectedListener = this
    }

    fun sliderFunct() {

        updateSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(updateSlider: Slider) {
                val slideValue = updateSlider.value

                Log.i("Slider Value", df.format(slideValue))
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(updateSlider: Slider) {
                val value = updateSlider.value

                view!!.update_slider_value.text = df.format(value)

                updateSlider.addOnChangeListener { update_slider, _, _ ->
                    val value = update_slider.value
                    view?.update_slider_value!!.text = df.format(value)
                }
            }

        })
    }


    private fun pickTime() {
        view.apply {
            updateTimePick.setOnClickListener {
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
                        updateTimePick.setText(time)
                    }
                }

                // show
                timePickerFragment.show(supportFragmentManager, "TimePickerFragment")
            }
        }

    }

    private fun pickDate() {
        view.apply {
            updateDatePick.setOnClickListener {
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
                        updateDatePick.setText(date)
                    }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }

    }

    private fun cancelWorkout() {
        findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
    }

    private fun updateWorkout() {
        val workName = update_wdate.text.toString()
        val workDes = update_wtime.text.toString()
        val work3 = t1
        val work4 = update_slider_value.text.toString()

        if (inputCheck(workName, workDes)) {
            val updatedWork = Workout2(args.currentWorkout.id, workName, workDes, work3, work4)

            muserViewModel.updateWorkout2(updatedWork)
            Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill up all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(workName: String, workDes: String): Boolean {
        return !(TextUtils.isEmpty(workName) || TextUtils.isEmpty(workDes))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            muserViewModel.deleteWorkout2(args.currentWorkout)
            Toast.makeText(
                requireContext(),
                "Deleted! ${args.currentWorkout.workoutDate}",
                Toast.LENGTH_SHORT
            )
                .show()
            findNavController().navigate(R.id.action_updateFragment2_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentWorkout.workoutDate}")
        builder.setMessage("Are you sure you want to delete? ${args.currentWorkout.workoutDate}")
        builder.create().show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        t1 = p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
