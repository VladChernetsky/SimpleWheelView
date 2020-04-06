package com.vchernetskyi.wheelview.animators

import com.vchernetskyi.wheelview.WheelViewLayoutManager

abstract class WheelItemAnimator {
    abstract fun animateItems(layoutManager: WheelViewLayoutManager)
}