package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.HorizontalLayoutItem

class HorizontalAdapter(
        private val horizontalItem: List<HorizontalLayoutItem>,
        private val listener: HorizontalItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ZOOMABLE_PHOTO_VIEW_HOLDER = 0
        private const val DETAILS_REGULAR_VIEW_HOLDER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DETAILS_REGULAR_VIEW_HOLDER) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.horizontal_recycler_view_item, parent, false)
            DetailsViewHolder(view, listener)
        } else {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.selected_photo_row_item, parent, false)
            SelectedDetailsPhotoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DETAILS_REGULAR_VIEW_HOLDER) {
            (holder as DetailsViewHolder).bind(horizontalItem[position])
        } else {
            (holder as SelectedDetailsPhotoViewHolder).bind(horizontalItem[position])
        }
    }

    override fun getItemCount() = horizontalItem.size

    override fun getItemViewType(position: Int): Int {
        return if (horizontalItem[position].isZoomable == 0) {
            DETAILS_REGULAR_VIEW_HOLDER
        } else {
            ZOOMABLE_PHOTO_VIEW_HOLDER
        }
    }

    class DetailsViewHolder(itemView: View, listener: HorizontalItemClickListener) :
            RecyclerView.ViewHolder(itemView) {
        fun bind(horizontalItem: HorizontalLayoutItem) {
            itemView.findViewById<ImageView>(R.id.horizontal_image_recycle)
                    .setImageResource(horizontalItem.image)
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onHorizontalItemClickListener(position)
                }
            }
        }
    }

    class SelectedDetailsPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(horizontalItem: HorizontalLayoutItem) {
            itemView.findViewById<ImageView>(R.id.details_selected_zoomable_image_view)
                    .setImageResource(horizontalItem.image)
        }
    }

    interface HorizontalItemClickListener {
        fun onHorizontalItemClickListener(position: Int)
    }
}