package com.example.Healthify

import Healthify.ListFragmentDirections
import Healthify.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.model.Workout2
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var workoutList = emptyList<Workout2>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = workoutList[position]

        holder.itemView.added_id.text = currentItem.id.toString()
        holder.itemView.added_wdate.text = currentItem.workoutDate
        holder.itemView.added_wtime.text = currentItem.workoutTime
        holder.itemView.added_wname.text = currentItem.workoutName
        holder.itemView.added_wsets.text = currentItem.workoutSets


        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment2(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(workout2: List<Workout2>) {
        this.workoutList = workout2
        notifyDataSetChanged()
    }
}