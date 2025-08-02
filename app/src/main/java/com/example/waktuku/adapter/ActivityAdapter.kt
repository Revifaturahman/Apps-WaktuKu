package com.example.waktuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waktuku.data.model.ActivityModel
import com.example.waktuku.databinding.ItemActivityBinding

class ActivityAdapter(
    private val activityList: MutableList<ActivityModel>,
    private val listener: OnItemActionListener
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    interface OnItemActionListener {
        fun onMoveUp(position: Int)
        fun onMoveDown(position: Int)
        fun onDelete(position: Int)
        fun onItemClick(activity: ActivityModel)
    }

    inner class ActivityViewHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activityList[position]
        with(holder.binding) {
            tvActivityName.text = activity.activity
            tvActivityTime.text = activity.time

            ivUp.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
            ivDown.visibility = if (position == activityList.size - 1) View.INVISIBLE else View.VISIBLE

            ivUp.setOnClickListener {
                listener.onMoveUp(position)
            }

            ivDown.setOnClickListener {
                listener.onMoveDown(position)
            }

            ivDelete.setOnClickListener{
                listener.onDelete(position)
            }

            root.setOnClickListener {
                listener.onItemClick(activity)
            }
        }
    }

    override fun getItemCount(): Int = activityList.size

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < 0 || toPosition < 0 || fromPosition >= itemCount || toPosition >= itemCount) return
        val item = activityList.removeAt(fromPosition)
        activityList.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(fromPosition)
        notifyItemChanged(toPosition)
    }

    fun getItem(position: Int): ActivityModel = activityList[position]

    fun removeItem(position: Int) {
        activityList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateData(newList: List<ActivityModel>) {
        activityList.clear()
        activityList.addAll(newList)
        notifyDataSetChanged()
    }
}
