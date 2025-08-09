package com.revifaturahman.waktuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revifaturahman.waktuku.R
import com.revifaturahman.waktuku.data.model.ActivityModel
import com.revifaturahman.waktuku.databinding.ItemActivityBinding

class ActivityAdapter(
    private val activityList: MutableList<ActivityModel>,
    private val listener: OnItemActionListener
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    interface OnItemActionListener {
        fun onMoveUp(position: Int)
        fun onMoveDown(position: Int)
        fun onDelete(position: Int)
    }

    inner class ActivityViewHolder(val binding: ItemActivityBinding) : RecyclerView.ViewHolder(binding.root)

    // Pemetaan fleksibel: daftar keyword -> ikon
    private val keywordToIcon = listOf(
        listOf("makan", "sarapan", "makan malam", "makan siang") to R.drawable.ic_eat,
        listOf("lari", "jogging") to R.drawable.ic_run,
        listOf("tidur", "istirahat") to R.drawable.ic_sleep,
        listOf("baca", "membaca", "belajar") to R.drawable.ic_book,
        listOf("berdoa", "sholat", "doa") to R.drawable.ic_pray,
        listOf("bangun", "bangun tidur") to R.drawable.ic_alarm,
        listOf("Sekolah ", "Kuliah", "berangkat sekolah") to R.drawable.ic_school,
        listOf("Mengerjakan", "tugas", "Mengerjakan tugas", "kerja") to R.drawable.ic_assignment,
        listOf("olahraga", "senam") to R.drawable.ic_sport,
        listOf("sepeda", "bersepeda") to R.drawable.ic_bycyle,
        listOf("nonton", "film", "tv") to R.drawable.ic_watch,
        listOf("game", "main game") to R.drawable.ic_game,
        listOf("scroll", "instagram", "tiktok", "sosmed") to R.drawable.ic_scroll,
        listOf("bersih", "nyapu", "ngepel", "sapu", "beres", "beres kasur") to R.drawable.ic_cleaning,
        listOf("cuci", "piring", "baju", "laundry") to R.drawable.ic_water,
        listOf("masak", "memasak", "telur", "telor", "daging") to R.drawable.ic_cook,
        listOf("belanja", "pasar", "shoping", "shop") to R.drawable.ic_shop,
        listOf("mandi", "pemandian") to R.drawable.ic_bath,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val binding = ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activityList[position]
        with(holder.binding) {
            tvActivityName.text = activity.activity
            tvActivityTime.text = activity.time

            // Ambil icon sesuai kata kunci
            val activityNameLower = activity.activity.lowercase()
            val matchedIcon = keywordToIcon.firstOrNull { (keywords, _) ->
                keywords.any { keyword -> activityNameLower.contains(keyword) }
            }?.second ?: R.drawable.ic_notification

            ivIcon.setImageResource(matchedIcon)

            ivUp.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
            ivDown.visibility = if (position == activityList.size - 1) View.INVISIBLE else View.VISIBLE

            ivUp.setOnClickListener {
                listener.onMoveUp(position)
            }

            ivDown.setOnClickListener {
                listener.onMoveDown(position)
            }

            ivDelete.setOnClickListener {
                listener.onDelete(position)
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
