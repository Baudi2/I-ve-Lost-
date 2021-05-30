package ru.startandroid.develop.ivelost.view.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.module.data.HorizontalUriItem

class AddUriHorizontalAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //! Вносим данные через отдельный метод а не конструктор чтобы уведомить
    //! recyclerView об изменениях в списке данных для перерисование листа
    private lateinit var itemList: List<HorizontalUriItem>
    fun inputData(item: List<HorizontalUriItem>) {
        itemList = item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_recycler_view_item, parent, false)
        return UriViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UriViewHolder).bind(itemList[position])

    }

    override fun getItemCount() = itemList.size


    class UriViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun bind(horizontalUriItem: HorizontalUriItem){
            itemView.findViewById<ImageView>(R.id.horizontal_image_recycle).setImageURI(horizontalUriItem.imageView)
        }
    }
}