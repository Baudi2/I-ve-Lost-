package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.HeaderItem

//? типичный класс адаптер, единственная разница от обычного адаптера то что в качестве viewHolder
//? мы используем не одноименный класс а RecyclerView.ViewHolder, это позволяет нам использовать в
//? этом адаптаре более одного viewHolder
class HeaderAdapter(private val listener: HeaderItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var headerItemList: List<HeaderItem>

    companion object {
        private const val HEADER_TYPE = 0
        private const val SELECT_ONE_TYPE = 1
    }

    fun inputData(item: List<HeaderItem>) {
        headerItemList = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item_lost_found, parent, false)
            HeaderItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.select_one_item, parent, false)
            LinearViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HEADER_TYPE) {
            (holder as HeaderItemViewHolder).bind(headerItemList[position])
        } else {
            (holder as LinearViewHolder).bind(headerItemList[position])
        }
    }

    override fun getItemCount() = headerItemList.size

    override fun getItemViewType(position: Int): Int {
        return if (headerItemList[position].headerImage != null) {
            HEADER_TYPE
        } else {
            SELECT_ONE_TYPE
        }
    }

    inner class HeaderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(headerItem: HeaderItem) {
            itemView.findViewById<TextView>(R.id.lost_found_item_text_view).text = headerItem.headerTopic
            itemView.findViewById<ImageView>(R.id.lost_found_item_image_view).setImageResource(headerItem.headerImage!!)
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onHeaderItemListener(position)
                }
            }
        }
    }

    inner class LinearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(selectedItem: HeaderItem) {
            itemView.findViewById<TextView>(R.id.text_view_select).text = selectedItem.headerTopic
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onHeaderItemListener(position)
                }
            }
        }
    }

    interface HeaderItemListener {
        fun onHeaderItemListener(position: Int)
    }
}