package com.vchernetskyi.wheelview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vchernetskyi.wheelview.animators.WheelItemAnimator
import kotlin.math.abs
import kotlin.math.sqrt

class WheelViewLayoutManager(ctx: Context) : LinearLayoutManager(ctx) {

    var animator: WheelItemAnimator? = null

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        animator?.animateItems(this)
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            animator?.animateItems(this)
            scrolled
        } else {
            0
        }
    }
}