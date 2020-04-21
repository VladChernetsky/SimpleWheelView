package com.vchernetskyi.wheelview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class WheelViewScrollListener : RecyclerView.OnScrollListener() {

    var callback: OnItemSelectedListener? = null

    override fun onScrollStateChanged(recyclerView: RecyclerView, state: Int) {
        super.onScrollStateChanged(recyclerView, state)

        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            // Find the closest child to the recyclerView center --> this is the selected item.
            val recyclerViewCenterY = getRecyclerViewCenterY(recyclerView)
            var minDistance = recyclerView.height
            var position = -1
            for (i in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(i)
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val childCenterX = lm.getDecoratedTop(child) +
                        (lm.getDecoratedBottom(child) - lm.getDecoratedTop(child)) / 2
                val childDistanceFromCenter = abs(childCenterX - recyclerViewCenterY)

                if (childDistanceFromCenter < minDistance) {
                    minDistance = childDistanceFromCenter
                    position = recyclerView.getChildLayoutPosition(child)
                }
            }
            // Notify on the selected item
            val selectedChild = recyclerView.layoutManager?.findViewByPosition(position)

            if (selectedChild != null) {
                callback?.onItemSelected(position, selectedChild)
            }
        }
    }

    private fun getRecyclerViewCenterY(recyclerView: RecyclerView): Int {
        return (recyclerView.bottom - recyclerView.top) / 2
    }

    interface OnItemSelectedListener {
        fun onItemSelected(layoutPosition: Int, itemView: View)
    }
}