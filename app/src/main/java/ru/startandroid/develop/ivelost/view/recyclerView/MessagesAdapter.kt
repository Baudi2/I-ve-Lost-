package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.MessageItem

class MessagesAdapter(
        private val messageItem: List<MessageItem>,
        private val listener: OnMessageClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val FROM_LEFT_MESSAGE_VIEW_HOLDER = 0
        private const val TO_RIGHT_MESSAGE_VIEW_HOLDER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FROM_LEFT_MESSAGE_VIEW_HOLDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_left_from, parent, false)
            FromLeftViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_right_to, parent, false)
            ToRightViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == FROM_LEFT_MESSAGE_VIEW_HOLDER) {
            (holder as FromLeftViewHolder).bind(messageItem[position])
        } else {
            (holder as ToRightViewHolder).bind(messageItem[position])
        }
    }

    override fun getItemCount() = messageItem.size

    override fun getItemViewType(position: Int): Int {
        return if (messageItem[position].isLeft != 0) {
            FROM_LEFT_MESSAGE_VIEW_HOLDER
        } else {
            TO_RIGHT_MESSAGE_VIEW_HOLDER
        }
    }

    inner class FromLeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView: TextView? = null

        init {
            textView = itemView.findViewById(R.id.textview_chat_left_from)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMessageClick(position, itemView, textView!!)
                }
            }
        }

        fun bind(messageItem: MessageItem) {
            itemView.findViewById<ImageView>(R.id.imageview_receiving_person).setImageResource(messageItem.userImage)
            textView!!.text = messageItem.lastMessageText
        }
    }

    inner class ToRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView: TextView? = null

        init {
            textView = itemView.findViewById(R.id.textview_chat_right_to)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMessageClick(position, itemView, textView!!)
                }
            }
        }

        fun bind(messageItem: MessageItem) {
            itemView.findViewById<ImageView>(R.id.current_user_chat_imageview).setImageResource(messageItem.userImage)
            textView!!.text = messageItem.lastMessageText
        }
    }

    interface OnMessageClickListener {
        fun onMessageClick(position: Int, itemView: View, textView: TextView)
    }


//TODO: Add DiffUtil callback, figure out what to do with primary key id, maybe increment them in bind fun
    //? video: https://www.youtube.com/watch?v=y31fzLe2Ajw
}











