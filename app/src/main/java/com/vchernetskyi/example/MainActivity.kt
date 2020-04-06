package com.vchernetskyi.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vchernetskyi.wheelview.WheelItem
import com.vchernetskyi.wheelview.WheelView
import com.vchernetskyi.wheelview.animators.DefaultAnimator

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val provider = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = provider.create(MainViewModel::class.java)

        setupWheelView()
    }

    private fun setupWheelView() {
        viewModel.wheelViewItems.observe(this, Observer { list ->
            wheelViewPicker.submitItems(list)
        })

        wheelViewPicker.setAnimator(DefaultAnimator())
        wheelViewPicker.selectItemById(MOCK_SELECTED_ITEM_ID)

        wheelViewPicker.setOnWheelItemSelectListener(object :
            WheelView.OnWheelViewItemSelectListener {
            override fun onItemSelected(item: WheelItem) {
                Toast.makeText(applicationContext, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val MOCK_SELECTED_ITEM_ID = 5
    }
}
