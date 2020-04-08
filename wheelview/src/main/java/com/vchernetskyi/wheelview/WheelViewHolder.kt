package com.vchernetskyi.wheelview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.wheel_view_item.view.*

class WheelViewHolder(
    private val actionClick: (WheelItem) -> Unit,
    private val viewItemConfig: ViewItemConfig,
    view: View
) : RecyclerView.ViewHolder(view) {

    private val tvWheelItem = itemView.tvWheelItem

    fun bind(model: WheelItem) {
        tvWheelItem.text = model.title
        tvWheelItem.setTextColor(viewItemConfig.itemTextColor)
        tvWheelItem.textSize = itemView.context.pxToSp(viewItemConfig.itemTextSize)
        itemView.setOnClickListener {
            actionClick.invoke(model)
        }
    }
}