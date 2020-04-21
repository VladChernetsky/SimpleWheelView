package com.vchernetskyi.wheelview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.vchernetskyi.wheelview.WheelViewAdapter.Companion.BROKEN_POSITION
import com.vchernetskyi.wheelview.animators.AlphaAnimator
import com.vchernetskyi.wheelview.animators.WheelItemAnimator
import kotlinx.android.synthetic.main.wheel_view.view.*

class WheelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val wheelItems = mutableListOf<WheelItemWrapper>()

    private val defaultAnimator = AlphaAnimator()
    private val wheelViewAdapter: WheelViewAdapter
    private val scrollListener = WheelViewScrollListener()

    private var selectedItemTextColor: Int = Color.RED
    private var itemTextColor: Int = Color.BLACK
    private var selectedPosition: Int = -1
    private var onItemSelectedListener: OnWheelViewItemSelectListener? = null
    private var containerHeight =
        context.resources.getDimensionPixelOffset(R.dimen.wheel_view_container_height)
    private var itemHeight =
        context.resources.getDimensionPixelOffset(R.dimen.wheel_view_item_height)
    private var itemTextSize: Int =
        context.resources.getDimensionPixelOffset(R.dimen.wheel_view_text_size)
    private var dividerColor: Int = ContextCompat.getColor(context, R.color.wheel_view_divider)

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

                itemTextSize =
                    getDimensionPixelOffset(R.styleable.WheelView_item_text_size, itemTextSize)
                dividerColor = getColor(R.styleable.WheelView_divider_color, dividerColor)

                itemHeight = getDimensionPixelOffset(
                    R.styleable.WheelView_item_height,
                    itemHeight
                )

                recycle()
            }
        }

        viewDividerTop.setBackgroundColor(dividerColor)
        viewDividerBottom.setBackgroundColor(dividerColor)

        wheelViewAdapter = WheelViewAdapter(
            ::actionItemClick, ViewItemConfig(
                itemTextColor,
                selectedItemTextColor,
                itemTextSize,
                itemHeight
            )
        )

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        scrollListener.callback = getScrollListenerCallback()

        rvWheelView.run {
            val padding: Int = (containerHeight / 2)
            setPadding(0, padding, 0, padding)

            layoutManager = WheelViewLayoutManager(context)
                .apply { this.animator = defaultAnimator }

            addOnScrollListener(scrollListener)
            itemAnimator = null
            adapter = wheelViewAdapter

            LinearSnapHelper().attachToRecyclerView(rvWheelView)
        }
    }

    private fun getScrollListenerCallback(): WheelViewScrollListener.OnItemSelectedListener {
        return object : WheelViewScrollListener.OnItemSelectedListener {
            override fun onItemSelected(layoutPosition: Int, itemView: View) {
                if (layoutPosition != selectedPosition) {
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }

    private fun actionItemClick(model: WheelItem) {
        selectItemById(model.id)
    }

    fun setOnWheelItemSelectListener(listener: OnWheelViewItemSelectListener) {
        this.onItemSelectedListener = listener
    }

    fun submitItems(items: List<WheelItem>) {
        wheelItems.addAll(items.mapIndexed { index, item -> WheelItemWrapper(item, index == 0) })
        wheelViewAdapter.submitList(wheelItems)
        selectedPosition = 0
    }

    @Suppress("unused")
    fun setAnimator(animator: WheelItemAnimator) {
        (rvWheelView.layoutManager as WheelViewLayoutManager).animator = animator
    }

    fun selectItemById(wheelItemId: Int, smooth: Boolean = false) {
        post {
            val foundPosition = wheelViewAdapter.getPositionById(wheelItemId)
            val position = if (foundPosition == BROKEN_POSITION) 0 else foundPosition

            if (smooth) {
                rvWheelView.smoothSnapToPosition(position)
            } else {
                (rvWheelView.layoutManager as? LinearLayoutManager)
                    ?.scrollToPositionWithOffset(position, itemHeight / -2)
            }

            notifyItemChanged(position)
        }
    }

    private fun notifyItemChanged(position: Int) {
        if (selectedPosition != -1) {
            val wrapper = wheelItems[selectedPosition]
            wheelItems[selectedPosition] = wrapper.copy(isSelected = false)
        }

        wheelItems[position] = wheelItems[position].copy(isSelected = true)
        wheelViewAdapter.notifyItemChanged(selectedPosition)
        wheelViewAdapter.notifyItemChanged(position)
        selectedPosition = position

        wheelViewAdapter.getItemByPosition(position)?.let {
            onItemSelectedListener?.onItemSelected(it)
        }
    }

    private fun RecyclerView.smoothSnapToPosition(
        position: Int,
        snapMode: Int = LinearSmoothScroller.SNAP_TO_START
    ) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }

    interface OnWheelViewItemSelectListener {
        fun onItemSelected(item: WheelItem)
    }
}