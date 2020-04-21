package com.vchernetskyi.wheelview

import android.view.View
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.wheel_view_item.view.*

class WheelViewHolder(
    private val actionClick: (WheelItem) -> Unit,
    private val viewItemConfig: ViewItemConfig,
    view: View
) : RecyclerView.ViewHolder(view) {

    private val tvWheelItem = itemView.tvWheelItem

    fun bind(model: WheelItemWrapper) {
        tvWheelItem.text = model.item.title

        val textColor = if (model.isSelected) {
            viewItemConfig.selectedTextColor
        } else {
            viewItemConfig.itemTextColor
        }
        tvWheelItem.setTextColor(textColor)

        tvWheelItem.textSize = itemView.context.pxToSp(viewItemConfig.itemTextSize)
        if (viewItemConfig.itemHeight != -1) {
            itemView.updateLayoutParams {
                height = viewItemConfig.itemHeight
            }
        }

        itemView.setOnClickListener {
            actionClick.invoke(model.item)
        }

        itemView.tag = model.item.id
    }
}