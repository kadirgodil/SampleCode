package com.example.mytask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.activities.TimesAdapter
import com.example.mytask.models.DaysEntity
import com.example.mytask.models.DetailsModel
import com.example.mytask.viewModels.DetailsViewModel

class DetailsAdapter(
    val context: Context,
    val list: ArrayList<DetailsModel>,
    val detailsModel: DetailsViewModel
) : RecyclerView.Adapter<DetailsAdapter.Details>() {
    class Details(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDays: TextView? = itemView.findViewById(R.id.tvDaysDetail)
        val layoutParent: ConstraintLayout? = itemView.findViewById(R.id.layoutParent)
        val rvTime: RecyclerView? = itemView.findViewById(R.id.rvTimes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Details {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false)
        return Details(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Details, position: Int) {
        val detail = list[position]
        holder.tvDays?.text = detail.value.toString()
        val list = detailsModel.getDataByDayId(detail.position)

        if (list.size > 0) {
            val timesAdapter = TimesAdapter(context, list)
            holder.rvTime?.adapter = timesAdapter
            timesAdapter.notifyDataSetChanged()
        } else {
            holder.layoutParent?.visibility = View.GONE
        }
    }
}