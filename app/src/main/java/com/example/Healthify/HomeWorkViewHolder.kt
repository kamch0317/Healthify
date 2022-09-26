package com.example.Healthify

import Healthify.databinding.CardCellBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.Healthify.databinding.CardCellBinding

class HomeWorkViewHolder (

    private val cardCellBinding: CardCellBinding,
) : RecyclerView.ViewHolder(cardCellBinding.root) {
    fun bindWork(workout: WorkoutData) {
        cardCellBinding.cover.setImageResource(workout.pic)
        cardCellBinding.title.text = workout.title
        cardCellBinding.author.text = workout.cmore

    }
}