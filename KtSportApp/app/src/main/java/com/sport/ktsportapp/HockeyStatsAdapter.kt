package com.sport.ktsportapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.sport.ktsportapp.databinding.ItemHockeyStatBinding

class HockeyStatsAdapter(private val hockeyStatsList: List<HockeyStat>) :
    RecyclerView.Adapter<HockeyStatsAdapter.HockeyStatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HockeyStatsViewHolder {
        val binding = ItemHockeyStatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HockeyStatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HockeyStatsViewHolder, position: Int) {
        val hockeyStat = hockeyStatsList[position]
        holder.bind(hockeyStat)
    }

    override fun getItemCount(): Int {
        return hockeyStatsList.size
    }

    inner class HockeyStatsViewHolder(private val binding: ItemHockeyStatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hockeyStat: HockeyStat) {
            binding.textViewPlayerName.text = hockeyStat.name
            binding.textViewTeamName.text = hockeyStat.team
            binding.textViewGoals.text = hockeyStat.m.toString()
            binding.textViewAssists.text = hockeyStat.plusMinus.toString()
            binding.textViewPlusMinus.text = hockeyStat.plusMinusPerM
        }
    }
}
