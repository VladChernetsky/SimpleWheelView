package com.vchernetskyi.wheelview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class WheelViewAdapter(
    private val actionClick: (WheelItem) -> Unit,
    private val viewItemConfig: ViewItemConfig
) : ListAdapter<WheelItem, WheelViewHolder>(WheelItemDiffItemUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WheelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wheel_view_item, parent, false)
        return WheelViewHolder(actionClick, viewItemConfig, view)
    }

    override fun onBindViewHolder(holder: WheelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    fun getItemByPosition(position: Int): WheelItem? = getItem(position)

    fun getPositionById(wheelItemId: Int): Int {
        for (position in 0 until itemCount) {
            if (getItem(position).id == wheelItemId) {
                return position
            }
        }
        return -1
    }
}

class WheelItemDiffItemUtil : DiffUtil.ItemCallback<WheelItem>() {

    override fun areItemsTheSame(oldItem: WheelItem, newItem: WheelItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WheelItem, newItem: WheelItem): Boolean {
        return oldItem.title == newItem.title
    }
}