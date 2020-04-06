package com.vchernetskyi.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vchernetskyi.wheelview.WheelItem

class MainViewModel : ViewModel() {

    private val _wheelViewItems = MutableLiveData<List<WheelItem>>()
    val wheelViewItems: LiveData<List<WheelItem>> = _wheelViewItems

    init {
        _wheelViewItems.postValue(provideWheelItems())
    }

    private fun provideWheelItems(): List<WheelItem> {
        return listOf(
            WheelItem(1, "Android"),
            WheelItem(2, "iOS"),
            WheelItem(3, "Windows 10"),
            WheelItem(4, "MacOS"),
            WheelItem(5, "Ubuntu"),
            WheelItem(6, "MS-DOS"),
            WheelItem(7, "ChromeOS"),
            WheelItem(8, "AmigaOS")
        )
    }
}