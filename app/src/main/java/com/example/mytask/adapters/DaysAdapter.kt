package com.example.mytask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.models.DaysModel
import com.example.mytask.preferences.Preferences
import com.example.mytask.utils.Constants
import com.example.mytask.utils.Utils
import com.example.mytask.viewModels.MainActivityViewModel
import java.util.Calendar
import java.util.Date

class DaysAdapter(
    val context: Context,
    val list: ArrayList<DaysModel>,
    val mViewModel: MainActivityViewModel,
    val preferences: Preferences
) :
    RecyclerView.Adapter<DaysAdapter.Days>() {
    var selectedPosition = 0
    var lastSelectedPostion = 0

    class Days(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clDays: ConstraintLayout? = itemView.findViewById(R.id.clDays)
        val tvDays: TextView? = itemView.findViewById(R.id.tvDays)
        val todayIndicator: View? = itemView.findViewById(R.id.viewToday)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Days {
        val inflater = LayoutInflater.from(context).inflate(R.layout.irem_days, parent, false)
        return Days(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Days, position: Int) {
        val day = list[position]
        holder.tvDays?.text = day.value
        updateIseelected(position)

        if (getToday() == position) {
            holder.todayIndicator?.visibility = View.VISIBLE
        }

        if (selectedPosition == holder.adapterPosition) {
            holder.clDays?.background =
                context.resources.getDrawable(R.drawable.selector_click)
        } else {
            if (day.isSelected) {
                holder.clDays?.background =
                    context.resources.getDrawable(R.drawable.selector_days_selected)

            } else {
                holder.clDays?.background =
                    context.resources.getDrawable(R.drawable.selector_days)
            }

        }
        holder?.clDays?.setOnClickListener {
            lastSelectedPostion = selectedPosition
            selectedPosition = holder.adapterPosition
            Utils.LastPos = day.postion
            if (!day.isSelected) {
                mViewModel.startTime.value = ""
                mViewModel.endTime.value = ""
            } else {
                if (preferences.getString(position.toString(), "")?.isNotBlank() == true) {
                    val times =
                        preferences.getString(position.toString())?.split(",")?.toTypedArray()
                    mViewModel.startTime.value = times?.get(0)
                    mViewModel.endTime.value = times?.get(1)
                } else {
                    mViewModel.startTime.value = ""
                    mViewModel.endTime.value = ""
                }

            }
            notifyItemChanged(lastSelectedPostion)
            notifyItemChanged(selectedPosition)

        }


    }

    fun getToday(): Int {
        val c: Date = Calendar.getInstance().time
        val dayOfWeek: Int = c.day
        return dayOfWeek
    }

    fun updateSelection(pos: Int) {
        list[pos].isSelected = true
        notifyDataSetChanged()
    }

    fun updateIseelected(position: Int) {
        list.forEach {
            it.isSelected = preferences.getString(position.toString(), "")?.isBlank() != true
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}