package com.sport.info.forecasts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sport.info.R
import com.sport.info.bets.BetsData

class AdapterF(private val listt:ArrayList<ForecastData>): RecyclerView.Adapter<AdapterF.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var header = itemView.findViewById<TextView>(R.id.tv_name)
        var about = itemView.findViewById<TextView>(R.id.tv_about)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater= LayoutInflater.from(parent.context).inflate(R.layout.bets_item,parent,false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = listt[position]
        holder.header.text = current.name
        holder.about.text = current.about
    }

    override fun getItemCount(): Int {
        return listt.size
    }

}