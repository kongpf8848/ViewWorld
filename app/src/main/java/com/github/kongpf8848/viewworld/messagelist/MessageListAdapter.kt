package com.github.kongpf8848.viewworld.messagelist

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.kongpf8848.viewworld.R

class MessageListAdapter internal constructor(
    theme: Resources.Theme,
    private val res: Resources,
    private val layoutInflater: LayoutInflater,
) : RecyclerView.Adapter<MessageListViewHolder>() {

    init {
        setHasStableIds(false)
    }

    var messages: List<MessageListItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            val oldMessageList = field

            field = value
            messagesMap = value.associateBy { it.uniqueId }

            val diffResult = DiffUtil.calculateDiff(
                MessageListDiffCallback(oldMessageList = oldMessageList, newMessageList = value)
            )
            diffResult.dispatchUpdatesTo(this)
        }

    private var messagesMap = emptyMap<Long, MessageListItem>()
    var selected: Set<Long> = emptySet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageListViewHolder {
        return createMessageViewHolder(parent)
    }

    private fun createMessageViewHolder(parent: ViewGroup?): MessageViewHolder {
        val view = layoutInflater.inflate(R.layout.message_list_item, parent, false)
        val holder = MessageViewHolder(view)
        return holder
    }


    override fun onBindViewHolder(holder: MessageListViewHolder, position: Int) {
        Log.d("JACK8", "onBindViewHolder() called with: holder = $holder, position = $position")
        val messageListItem = getItem(position)
        val h=holder as MessageViewHolder
        h.uniqueId = messageListItem.uniqueId
        h.tv_subject.text=messageListItem.subject
        h.v_unread.visibility=if(messageListItem.isRead) View.INVISIBLE else View.VISIBLE
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    private fun getItem(position: Int): MessageListItem = messages[position]

    fun getItemById(uniqueId: Long): MessageListItem? {
        return messagesMap[uniqueId]
    }

    fun isSelected(item: MessageListItem): Boolean {
        return item.uniqueId in selected
    }

    private class MessageListDiffCallback(
        private val oldMessageList: List<MessageListItem>,
        private val newMessageList: List<MessageListItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldMessageList.size

        override fun getNewListSize(): Int = newMessageList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMessageList[oldItemPosition].uniqueId == newMessageList[newItemPosition].uniqueId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldMessageList[oldItemPosition] == newMessageList[newItemPosition]
        }
    }
}