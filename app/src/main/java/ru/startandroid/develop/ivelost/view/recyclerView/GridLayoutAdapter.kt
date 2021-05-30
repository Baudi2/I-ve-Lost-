package ru.startandroid.develop.ivelost.view.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.GridLayoutItem
import ru.startandroid.develop.ivelost.module.data.HeaderItem
import ru.startandroid.develop.testprojectnavigation.utils.shortToast
import ru.startandroid.develop.testprojectnavigation.utils.showPopup

class GridLayoutAdapter(
        private val gridLayoutList: List<GridLayoutItem>,
        private val listener: OnItemClickListener,
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.header_lost_found, parent, false)
                HeaderViewHolder(view)
            }

            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.example_item, parent, false)
                ItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HEADER) {
            (holder as HeaderViewHolder).bind()
        } else {
            (holder as ItemViewHolder).bind(gridLayoutList[position])
        }
    }

    override fun getItemCount() = gridLayoutList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    @SuppressLint("CutPasteId")
    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            HeaderAdapter.HeaderItemListener {
        private val theList: List<HeaderItem>

        init {
            theList = generateItemListHorizontalLayout(8)

            val headerAdapter = HeaderAdapter(this)
            headerAdapter.inputData(theList)
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            itemView.findViewById<RecyclerView>(R.id.fragmentLostHorizontalTopicRecyclerView).layoutManager =
                    layoutManager
            itemView.findViewById<RecyclerView>(R.id.fragmentLostHorizontalTopicRecyclerView).adapter =
                    headerAdapter
        }

        fun bind() {}

        override fun onHeaderItemListener(position: Int) {
            shortToast(theList[position].headerTopic)
        }

        private fun generateItemListHorizontalLayout(size: Int): ArrayList<HeaderItem> {
            // the we create new empty arrayList<>
            val list = ArrayList<HeaderItem>()

            // and it uses the size value in the for loop to fill this list with data
            // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
            for (i in 0 until size) {
                // this part is only responsible for alternating between our 5 drawables
                val drawable = when (i % 8) {
                    0 -> R.drawable.icon_all_topics
                    1 -> R.drawable.icon_documents
                    2 -> R.drawable.icon_money
                    3 -> R.drawable.icon_paw
                    4 -> R.drawable.icon_people
                    5 -> R.drawable.icon_jewelry
                    6 -> R.drawable.icon_personal_belongings
                    else -> R.drawable.icon_other
                }

                val header = when (i % 8) {
                    0 -> "Все"
                    1 -> "Документы"
                    2 -> "Деньги"
                    3 -> "Животные"
                    4 -> "Люди"
                    5 -> "Драгоценности"
                    6 -> "Личные вещи"
                    else -> "Другое"
                }

                // creates new ExampleItem and passes through its constructor the necessary data
                val item = HeaderItem(header, drawable)
                list += item
            }
            // after filling the list with data we eventually return it
            return list
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
            itemView.findViewById<ImageView>(R.id.grid_layout_item_options).setOnClickListener {
                showPopup(it, R.menu.menu_grid_layout_options,
                        "Объявление скрыто", "Объявление добавлено в избранные",
                        R.id.found_lost_hide_ad, R.id.found_lost_favorite_ad, null)
            }
        }

        fun bind(gridLayoutList: GridLayoutItem) {
            itemView.findViewById<ImageView>(R.id.image_holder)
                    .setImageResource(gridLayoutList.imageResource)
            itemView.findViewById<TextView>(R.id.text_view_header).text = gridLayoutList.headerText
            itemView.findViewById<TextView>(R.id.text_view_location).text =
                    gridLayoutList.descriptionText
            itemView.findViewById<TextView>(R.id.text_view_time).text = gridLayoutList.timeText
            itemView.findViewById<TextView>(R.id.text_view_views).text =
                    gridLayoutList.viewsCount.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}