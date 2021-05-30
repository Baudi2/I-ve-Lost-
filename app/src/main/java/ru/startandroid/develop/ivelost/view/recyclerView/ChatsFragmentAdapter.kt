package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.MessageItem
import ru.startandroid.develop.testprojectnavigation.utils.explainOnItemClickListener

class ChatsFragmentAdapter(
        private val messageItemList: List<MessageItem>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<ChatsFragmentAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.message_item,
                parent, false
        )

        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = messageItemList[position]

        holder.userImage.setImageResource(currentItem.userImage)
        holder.userName.text = currentItem.userName
        holder.lastMessage.text = currentItem.lastMessageText
    }

    override fun getItemCount() = messageItemList.size

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.user_image_chat_message_row)
        val userName: TextView = itemView.findViewById(R.id.username_textView_message_item)
        val lastMessage: TextView = itemView.findViewById(R.id.message_textView_last_message_item)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    fun explain() = explainOnItemClickListener() //!. .
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}