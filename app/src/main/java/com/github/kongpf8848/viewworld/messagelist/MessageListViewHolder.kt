package com.github.kongpf8848.viewworld.messagelist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.kongpf8848.viewworld.R


sealed class MessageListViewHolder(view: View) : ViewHolder(view)

class MessageViewHolder(view: View) : MessageListViewHolder(view) {
    var uniqueId: Long = -1L
    val v_unread:View=view.findViewById(R.id.view_unread)
    val tv_subject: TextView = view.findViewById(R.id.tv_subject)

}

