package com.github.kongpf8848.viewworld.activity.slidemenu

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kongpf8848.viewworld.R
import com.github.kongpf8848.viewworld.base.BaseActivity
import com.github.kongpf8848.viewworld.databinding.ActivitySlideMenu2Binding
import com.github.kongpf8848.viewworld.messagelist.*
import com.github.kongpf8848.viewworld.swipe.ItemTouchHelper
import com.github.kongpf8848.viewworld.swipe.SwipeAction
import com.github.kongpf8848.viewworld.swipe.SwipeResourceProvider
import java.util.ArrayList

class SlideMenu2Activity : BaseActivity<ActivitySlideMenu2Binding>() {

    private lateinit var adapter: MessageListAdapter

    private val swipeActionSupportProvider = SwipeActionSupportProvider { item, action ->
        when (action) {
            SwipeAction.None -> false
            SwipeAction.ToggleSelection -> true
            SwipeAction.ToggleRead -> true
            SwipeAction.ToggleStar -> true
            SwipeAction.Archive -> true
            SwipeAction.Delete -> true
            SwipeAction.Move -> true
            SwipeAction.Spam -> true
        }
    }

    private val swipeListener = object : MessageListSwipeListener {
        override fun onSwipeStarted(item: MessageListItem, action: SwipeAction) {
            Log.d(TAG, "onSwipeStarted() called with: item = $item, action = $action")
        }

        override fun onSwipeActionChanged(item: MessageListItem, action: SwipeAction) {
            Log.d(TAG, "onSwipeActionChanged() called with: item = $item, action = $action")
        }

        override fun onSwipeAction(item: MessageListItem, action: SwipeAction) {
            Log.d(TAG, "onSwipeAction() called with: item = $item, action = $action")
            when (action) {
                SwipeAction.Delete -> {
                    var position = -1
                    for ((index, value) in adapter.messages.withIndex()) {
                        if (value.uniqueId == item.uniqueId) {
                            (adapter.messages as ArrayList).removeAt(index)
                            position = index
                            break
                        }
                    }
                    if (position >= 0) {
                        adapter.notifyItemRemoved(position)
                    }
                }

                SwipeAction.ToggleRead -> {
                    var position = -1
                    item.isRead = !item.isRead
                    for ((index, value) in adapter.messages.withIndex()) {
                        if (value.uniqueId == item.uniqueId) {
                            position = index
                            break
                        }
                    }
                    if (position >= 0) {
                        adapter.notifyItemChanged(position)
                    }
                }

                else -> {}
            }
        }

        override fun onSwipeEnded(item: MessageListItem) {
            Log.d(TAG, "onSwipeEnded() called with: item = $item")
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_slide_menu_2
    }

    override fun onCreateEnd(savedInstanceState: Bundle?) {
        super.onCreateEnd(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MessageListAdapter(
            theme = theme,
            res = resources,
            layoutInflater = layoutInflater,
        ).apply {
            messages = getData()
        }
        val itemTouchHelper = ItemTouchHelper(
            MessageListSwipeCallback(
                this,
                resourceProvider = SwipeResourceProvider(theme),
                swipeActionSupportProvider,
                swipeRightAction = SwipeAction.ToggleRead,
                swipeLeftAction = SwipeAction.Delete,
                adapter,
                swipeListener
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)

        binding.recyclerview.adapter = adapter
    }


    private fun getData(): List<MessageListItem> {
        val list: MutableList<MessageListItem> = ArrayList<MessageListItem>()
        for (i in 0..49L) {
            list.add(
                MessageListItem(
                    uniqueId = i,
                    subject = "我是来自Gmail的邮件主题${i}",
                    isRead = false,
                    isStarred = false
                )
            )
        }
        return list
    }
}
