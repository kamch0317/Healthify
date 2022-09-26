package com.example.Healthify

import Healthify.databinding.CardCellBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.databinding.CardCellBinding

class WorkoutAdapter(
    private val workouts: MutableList<WorkoutData>,
    private val clickListener: OnWorkoutClickListener
) :
    RecyclerView.Adapter<WorkoutViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return WorkoutViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {

        holder.bindWork(workouts[position])
    }

    override fun getItemCount(): Int {
        return workouts.size
    }
}


