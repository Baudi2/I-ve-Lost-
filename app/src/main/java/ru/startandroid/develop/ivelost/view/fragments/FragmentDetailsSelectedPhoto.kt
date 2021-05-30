package ru.startandroid.develop.ivelost.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.DetailsSelectedPhotoBinding
import ru.startandroid.develop.ivelost.module.data.HorizontalLayoutItem
import ru.startandroid.develop.ivelost.view.recyclerView.HorizontalAdapter
import ru.startandroid.develop.ivelost.view.recyclerView.LinePagerIndicatorDecoration
import ru.startandroid.develop.testprojectnavigation.utils.shortToast

class FragmentDetailsSelectedPhoto : Fragment(R.layout.details_selected_photo),
        HorizontalAdapter.HorizontalItemClickListener {
    private lateinit var binding: DetailsSelectedPhotoBinding
    private val args: FragmentDetailsSelectedPhotoArgs by navArgs()
    private val dummyData = generateItemList(6)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsSelectedPhotoBinding.bind(view)

        val adapter = HorizontalAdapter(dummyData, this)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper: SnapHelper = PagerSnapHelper()

        binding.apply {
            detailsSelectedRecyclerView.adapter = adapter
            detailsSelectedRecyclerView.layoutManager = manager
            detailsSelectedRecyclerView.setHasFixedSize(true)
            snapHelper.attachToRecyclerView(detailsSelectedRecyclerView)
            detailsSelectedRecyclerView.addItemDecoration(LinePagerIndicatorDecoration())
            respondToPhotoTextView.setOnClickListener {
                shortToast("Перенаправляем на фрагмент с сообещниями")
            }
        }
    }

    //? при входе в этот фрагмент мы перемещаем recyclerView на ту картинку через которую
    //? юзер перешел сюда
    override fun onResume() {
        super.onResume()
        binding.detailsSelectedRecyclerView.layoutManager?.scrollToPosition(args.startPosition)
        if (args.isYourAdd) binding.respondToPhotoTextView.visibility = View.GONE
    }

    //? этот метод ничего не делает но нам нужно его переопределить т.к. мы пользуемся адаптером
    //? для которого необходимо реализация данного метода
    override fun onHorizontalItemClickListener(position: Int) {}

    //? генерирует временные данные
    private fun generateItemList(size: Int): ArrayList<HorizontalLayoutItem> {
        // the we create new empty arrayList<>
        val list = ArrayList<HorizontalLayoutItem>()

        // and it uses the size value in the for loop to fill this list with data
        // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
        for (i in 0 until size) {
            // this part is only responsible for alternating between our 5 drawables
            val drawable = when (i % 6) {
                0 -> R.drawable.stone
                1 -> R.drawable.charger
                2 -> R.drawable.headphones
                3 -> R.drawable.outside
                4 -> R.drawable.pen
                5 -> R.drawable.ring
                else -> R.drawable.ring
            }

            // creates new ExampleItem and passes through its constructor the necessary data
            val item = HorizontalLayoutItem(drawable, 1)
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }
}