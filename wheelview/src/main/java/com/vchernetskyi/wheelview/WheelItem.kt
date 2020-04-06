package com.vchernetskyi.wheelview

import android.os.Parcel
import android.os.Parcelable

class WheelItem(val id: Int = 0, val title: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString() ?: ""
    )

    override fun toString(): String {
        return "id=$id title$title"
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(title)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<WheelItem> {
        override fun createFromParcel(parcel: Parcel): WheelItem {
            return WheelItem(parcel)
        }

        override fun newArray(size: Int): Array<WheelItem?> {
            return arrayOfNulls(size)
        }
    }
}