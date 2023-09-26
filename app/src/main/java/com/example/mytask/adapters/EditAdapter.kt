package com.example.mytask.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.litsners.ActionLitsner
import com.example.mytask.litsners.onDayPosition
import com.example.mytask.models.DetailsModel

class EditAdapter(
    val context: Context,
    val list: ArrayList<DetailsModel>,
    val onDayPosition: onDayPosition,
    val actionLitsner: ActionLitsner
) : RecyclerView.Adapter<EditAdapter.Edit>() {
    val removeList = ArrayList<DetailsModel>()

    init {
        removeList.clear()
    }

    class Edit(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDays: TextView? = itemView.findViewById(R.id.tvDaysDetail)
        val layoutParent: ConstraintLayout? = itemView.findViewById(R.id.layoutParent)
        val rvTime: RecyclerView? = itemView.findViewById(R.id.rvTimes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Edit {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false)
        return EditAdapter.Edit(inflater)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: Edit, position: Int) {
        val detail = list[position]
        holder.tvDays?.text = detail.value.toString()
        val locallist = onDayPosition.onDaySelected(detail.position)
        if (locallist.size > 0) {
            val timesAdapter = EditTimesAdapter(context, locallist, actionLitsner)
            holder.rvTime?.adapter = timesAdapter
            timesAdapter.notifyDataSetChanged()
        } else {
            removeList.add(detail)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun removeUnusedList() {
        Log.d("Tag", "${removeList.size}")
        removeList.forEach {
            list.remove(it)
        }
        notifyDataSetChanged()
    }

    fun removeItemAtPosition(index: Int) {
        list.removeAt(index)
        notifyDataSetChanged()
    }

}