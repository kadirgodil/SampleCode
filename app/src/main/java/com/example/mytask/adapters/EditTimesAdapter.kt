package com.example.mytask.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.activities.MainActivity
import com.example.mytask.litsners.ActionLitsner
import com.example.mytask.models.DaysEntity
import com.example.mytask.utils.Utils

class EditTimesAdapter(
    val context: Context,
    val list: MutableList<DaysEntity>,
    val actionLitsner: ActionLitsner
) :
    RecyclerView.Adapter<EditTimesAdapter.mytimes>() {
    class mytimes(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStartTime: TextView? = itemView.findViewById(R.id.tvStarttime)
        val tvEndTime: TextView? = itemView.findViewById(R.id.tvEndtimes)
        val ivDelete: ImageView? = itemView.findViewById(R.id.ivDelete)
        val ivAdd: ImageView? = itemView.findViewById(R.id.icAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mytimes {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_edit, parent, false)
        return EditTimesAdapter.mytimes(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mytimes, position: Int) {
        val daysEntity = list[position]
        holder.tvStartTime?.setText("StartTime : ${daysEntity.startTime} , ")
        holder.tvEndTime?.setText("EndTime : ${daysEntity.endTime}")
        holder.ivDelete?.setOnClickListener {
            actionLitsner.onDayDeleted(daysEntity.id)
            list.remove(daysEntity)
            notifyDataSetChanged()
        }
        holder.ivAdd?.setOnClickListener {
            Utils.addedItemPosition = daysEntity.dayId
            Utils.isFromAdd = true
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }

    }
}