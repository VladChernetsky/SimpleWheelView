package com.vchernetskyi.wheelview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import com.vchernetskyi.wheelview.animators.DefaultAnimator
import com.vchernetskyi.wheelview.animators.WheelItemAnimator
import kotlinx.android.synthetic.main.wheel_view.view.*

class WheelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val defaultAnimator = DefaultAnimator()
    private val wheelViewAdapter: WheelViewAdapter
    private val scrollListener = WheelViewScrollListener()

    private var selectedItemTextColor: Int = Color.RED
    private var itemTextColor: Int = Color.BLACK
    private var selectedPosition: Int = -1
    private var onItemSelectedListener: OnWheelViewItemSelectListener? = null
    private var selectedView: TextView? = null
    private var containerHeight =
        context.resources.getDimensionPixelOffset(R.dimen.wheel_view_container_height)
    private val itemHeight =
        context.resources.getDimensionPixelOffset(R.dimen.wheel_view_item_height)

    init {
        View.inflate(context, R.layout.wheel_view, this)
        attrs?.let {
            context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.layout_height)).run {
                containerHeight = getDimensionPixelSize(0, containerHeight)
                recycle()
            }

            context.obtainStyledAttributes(it, R.styleable.WheelView).apply {
                itemTextColor = getColor(R.styleable.WheelView_item_text_color, itemTextColor)
                selectedItemTextColor =
                    getColor(R.styleable.WheelView_selected_item_text_color, selectedItemTextColor)
                recycle()
            }
        }
        wheelViewAdapter = WheelViewAdapter(::actionItemClick, ViewItemConfig(itemTextColor))

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        scrollListener.callback = getScrollListenerCallback()

        rvWheelView.run {
            val padding: Int = (containerHeight / 2) - itemHeight
            setPadding(0, padding, 0, padding)

            layoutManager = WheelViewLayoutManager(context)
                .apply { this.animator = defaultAnimator }

            addOnScrollListener(scrollListener)

            adapter = wheelViewAdapter

            LinearSnapHelper().attachToRecyclerView(rvWheelView)
        }
    }

    private fun getScrollListenerCallback(): WheelViewScrollListener.OnItemSelectedListener {
        return object : WheelViewScrollListener.OnItemSelectedListener {
            override fun onItemSelected(layoutPosition: Int, itemView: View) {
                if (layoutPosition != selectedPosition) {
                    selectedPosition = layoutPosition
                    changeSelectedTextColor(itemView)
                    wheelViewAdapter.getItemByPosition(layoutPosition)?.let {
                        onItemSelectedListener?.onItemSelected(it)
                    }
                }
            }
        }
    }

    private fun changeSelectedTextColor(itemView: View) {
        selectedView?.setTextColor(itemTextColor)
        selectedView = (itemView as TextView)
        selectedView?.setTextColor(selectedItemTextColor)
    }

    private fun actionItemClick(model: WheelItem) {
        selectItemById(model.id)
    }

    fun setOnWheelItemSelectListener(listener: OnWheelViewItemSelectListener) {
        this.onItemSelectedListener = listener
    }

    fun submitItems(items: List<WheelItem>) {
        wheelViewAdapter.submitList(items)
    }

    fun setAnimator(animator: WheelItemAnimator) {
        (rvWheelView.layoutManager as WheelViewLayoutManager).animator = animator
    }

    fun selectItemById(wheelItemId: Int) {
        post {
            val position = wheelViewAdapter.getPositionById(wheelItemId)
            if (position != -1) {
                rvWheelView.smoothScrollToPosition(position)
                wheelViewAdapter.getItemByPosition(position)?.let {
                    onItemSelectedListener?.onItemSelected(it)
                }
            }
        }
    }

    interface OnWheelViewItemSelectListener {
        fun onItemSelected(item: WheelItem)
    }
}