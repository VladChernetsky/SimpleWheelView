package com.vchernetskyi.example

import android.os.Bundle
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

        buttonRandom.setOnClickListener {
            viewModel.onRandomClicked()
        }
    }

    private fun setupWheelView() {

        viewModel.randomItemId.observe(this, Observer { randomId ->
            wheelViewPicker.selectItemById(randomId, true)
        })

        viewModel.wheelViewItems.observe(this, Observer { list ->
            wheelViewPicker.submitItems(list)
        })

        wheelViewPicker.setAnimator(DefaultAnimator())
        wheelViewPicker.setOnWheelItemSelectListener(object :
            WheelView.OnWheelViewItemSelectListener {
            override fun onItemSelected(item: WheelItem) {
                textViewChosenItem.text = item.toString()
            }
        })

        wheelViewPicker.selectItemById(MOCK_SELECTED_ITEM_ID, true)
    }

    companion object {
        private const val MOCK_SELECTED_ITEM_ID = 4
    }
}
