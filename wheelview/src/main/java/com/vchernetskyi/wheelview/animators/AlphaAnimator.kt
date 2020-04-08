package com.vchernetskyi.wheelview.animators

import com.vchernetskyi.wheelview.WheelViewLayoutManager
import kotlin.math.abs
import kotlin.math.sqrt

class AlphaAnimator : WheelItemAnimator() {

    override fun animateItems(layoutManager: WheelViewLayoutManager) {
        val height = layoutManager.height
        if (height <= 0) return
        val mid = height / 2.0f
        for (i in 0 until layoutManager.childCount) {

            // Calculating the distance of the child from the center
            val child = layoutManager.getChildAt(i)
            child?.run {
                val childMid =
                    (layoutManager.getDecoratedTop(child) + layoutManager.getDecoratedBottom(child)) / 2.0f
                val distanceFromCenter = abs(mid - childMid)

                // The scaling formula
                val scale =
                    1 - sqrt((distanceFromCenter / height).toDouble()).toFloat() * 0.66f
                val alpha: Float = if (scale < .8) {
                    val calcAlpha = scale - 0.2
                    calcAlpha.toFloat()
                } else if (scale >= 0.8 && scale < 0.9) {
                    scale
                } else {
                    1F
                }

                child.alpha = alpha
            }
        }
    }
}