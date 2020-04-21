package com.vchernetskyi.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vchernetskyi.wheelview.WheelItem
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _wheelViewItems = MutableLiveData<List<WheelItem>>()
    val wheelViewItems: LiveData<List<WheelItem>> = _wheelViewItems

    private val _randomItemId = MutableLiveData<Int>()
    val randomItemId: LiveData<Int> = _randomItemId

    private val random = Random.Default

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

    fun onRandomClicked() {
        val items = provideWheelItems()
        _randomItemId.value = random.nextInt(1, items.size)
    }
}