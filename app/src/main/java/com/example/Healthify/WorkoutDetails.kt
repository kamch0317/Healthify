package com.example.Healthify

import Healthify.databinding.WorkoutDetailsBinding
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.Healthify.databinding.WorkoutDetailsBinding
import com.example.Healthify.model.WORK_ID_EXTRA


class WorkoutDetails : AppCompatActivity() {

    private lateinit var binding: WorkoutDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WorkoutDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backbtn.setOnClickListener{workout()}


        val workID = intent.getIntExtra(WORK_ID_EXTRA, -1)
        val work = workFromID(workID)
        if (work != null) {
            binding.cover.setImageResource(work.picDetails)
            binding.title.text = work.nameDetails
            binding.description.text = work.desDetails
        }

        binding.but1.setOnClickListener {
            if (work != null) {
                binding.link.text = work.link
                if (binding.link.text == "null") {
                    Toast.makeText(
                        applicationContext, "Url coming soon, sorry for the inconvenience!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(work.link)
                    startActivity(intent)
                }
            }
        }

    }

    private fun workout() {
        val context = binding.backbtn.context
        val intent = Intent(context, WorkoutMain::class.java)
        workoutList.clear()
        startActivity(intent)
    }


    private fun workFromID(workID: Int): WorkoutDataDetails? {
        for (workDetails in workoutDetails ) {
            if (workDetails.idDetails == workID)
                return workDetails
        }
        return null
    }

}