package com.vchernetskyi.wheelview

import android.content.Context
import android.util.TypedValue

fun Context.pxToSp(px: Int): Float {
    return px / resources.displayMetrics.scaledDensity
}

fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    ).toInt()
}

fun Context.spToPx(sp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics
    ).toInt()
}

fun Context.dpToSp(dp: Float): Float {
    return (dpToPx(dp) / resources.displayMetrics.scaledDensity)
}