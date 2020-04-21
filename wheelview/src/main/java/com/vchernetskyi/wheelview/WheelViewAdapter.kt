package com.vchernetskyi.wheelview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class WheelViewAdapter(
    private val actionClick: (WheelItem) -> Unit,
    private val viewItemConfig: ViewItemConfig
) : ListAdapter<WheelItemWrapper, WheelViewHolder>(WheelItemDiffItemUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WheelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wheel_view_item, parent, false)
        return WheelViewHolder(actionClick, viewItemConfig, view)
    }

    override fun onBindViewHolder(holder: WheelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).item.id.toLong()
    }

    fun getItemByPosition(position: Int): WheelItem? {
        if (position >= itemCount || position < 0) return null
        return getItem(position).item
    }

    fun getPositionById(wheelItemId: Int): Int {
        for (position in 0 until itemCount) {
            if (getItem(position).item.id == wheelItemId) {
                return position
            }
        }
        return BROKEN_POSITION
    }

    companion object{
        const val BROKEN_POSITION = -1
    }
}

class WheelItemDiffItemUtil : DiffUtil.ItemCallback<WheelItemWrapper>() {

    override fun areItemsTheSame(oldItem: WheelItemWrapper, newItem: WheelItemWrapper): Boolean {
        return oldItem.item.id == newItem.item.id
    }

    override fun areContentsTheSame(oldItem: WheelItemWrapper, newItem: WheelItemWrapper): Boolean {
        return oldItem.item.title == newItem.item.title && oldItem.isSelected == newItem.isSelected
    }
}