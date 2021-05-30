package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.FragmentSelectedModule

//TODO: implement viewBinding with viewHolders
class FragmentSelectedAdapter(private val listener: SelectedAddItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var dataList: List<FragmentSelectedModule>

    companion object {
        const val NO_VIEW_COUNT_HOLDER = 0
        const val WITH_VIEW_COUNT_HOLDER = 1
    }


    fun inputData(list: List<FragmentSelectedModule>) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NO_VIEW_COUNT_HOLDER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_add_item, parent, false)
            SelectedAddViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_add_item_with_views, parent, false)
            SelectedAddWithViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == NO_VIEW_COUNT_HOLDER) {
            (holder as SelectedAddViewHolder).bind(dataList[position])
        } else {
            (holder as SelectedAddWithViewHolder).bind(dataList[position])
        }
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].viewCount == 0) {
            NO_VIEW_COUNT_HOLDER
        } else {
            WITH_VIEW_COUNT_HOLDER
        }
    }

    inner class SelectedAddViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fragmentSelectedModule: FragmentSelectedModule) {
            itemView.findViewById<ImageView>(R.id.image_holder_selected).setImageResource(fragmentSelectedModule.imageResource)
            itemView.findViewById<TextView>(R.id.text_view_header_selected).text = fragmentSelectedModule.headerText
            itemView.findViewById<TextView>(R.id.text_view_location_selected).text = fragmentSelectedModule.descriptionText
            itemView.findViewById<TextView>(R.id.text_view_time_selected).text = fragmentSelectedModule.timeText
        }

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClickListenerAdd(position)
                }
            }
        }
    }

    inner class SelectedAddWithViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fragmentSelectedModule: FragmentSelectedModule) {
            itemView.findViewById<ImageView>(R.id.image_holder_selected_wview).setImageResource(fragmentSelectedModule.imageResource)
            itemView.findViewById<TextView>(R.id.text_view_header_selected_wview).text = fragmentSelectedModule.headerText
            itemView.findViewById<TextView>(R.id.text_view_location_selected_wview).text = fragmentSelectedModule.descriptionText
            itemView.findViewById<TextView>(R.id.text_view_time_selected_wview).text = fragmentSelectedModule.timeText
            itemView.findViewById<TextView>(R.id.text_view_views_selected_wview).text = fragmentSelectedModule.viewCount.toString()
        }

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClickListenerAdd(position)
                }
            }
        }
    }

    interface SelectedAddItemClickListener {
        fun onClickListenerAdd(position: Int)
    }
}