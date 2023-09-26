package com.example.mytask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.litsners.ActionLitsner
import com.example.mytask.models.DaysEntity

class TimesAdapter(
    val context: Context,
    val list: MutableList<DaysEntity>
) :
    RecyclerView.Adapter<TimesAdapter.mytimes>() {
    class mytimes(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStartTime: TextView? = itemView.findViewById(R.id.tvStarttime)
        val tvEndTime: TextView? = itemView.findViewById(R.id.tvEndtimes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mytimes {

        val inflater = LayoutInflater.from(context).inflate(R.layout.item_times, parent, false)
        return mytimes(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mytimes, position: Int) {
        val daysEntity = list[position]
        holder.tvStartTime?.setText("StartTime : ${daysEntity.startTime} , ")
        holder.tvEndTime?.setText("EndTime : ${daysEntity.endTime}")
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}