package ru.startandroid.develop.ivelost.view.fragments.lost

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentLostBinding
import ru.startandroid.develop.ivelost.module.data.ClickedItemDescription
import ru.startandroid.develop.ivelost.module.data.GridLayoutItem
import ru.startandroid.develop.ivelost.view.recyclerView.GridLayoutAdapter
import ru.startandroid.develop.testprojectnavigation.utils.hideHome
import ru.startandroid.develop.testprojectnavigation.utils.shortToast
import ru.startandroid.develop.testprojectnavigation.utils.stringGet
import ru.startandroid.develop.testprojectnavigation.utils.unlockDrawer

class FragmentLost : Fragment(R.layout.fragment_lost), GridLayoutAdapter.OnItemClickListener {


    //? binding; apply; bottomNavigation; fab clickListener, все это законментировано в FragmentProfile.kt
    private lateinit var binding: FragmentLostBinding
    private lateinit var exampleList: ArrayList<GridLayoutItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLostBinding.bind(view)

        binding.apply {
            bottomNavLost.setupWithNavController(findNavController())

            fabLost.setOnClickListener {
                val action = FragmentLostDirections.actionGlobalFragmentAddLostFind2()
                findNavController().navigate(action)
            }
        }

        // подключаем поиск на тулбар с иконкой поиска
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exampleList = generateItemListGridLayout(30)

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }

        val adapter = GridLayoutAdapter(exampleList, this, requireContext())
        //? recyclerView закоментирован в FragmentFound тут тоже самое что и там
        binding.recyclerLostView.adapter = adapter
        binding.apply {
            recyclerLostView.layoutManager = manager
            recyclerLostView.setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        unlockDrawer()
        hideHome()
    }

    //? описание в FragmentFound
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // инициализируем поисковое меню
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Поиск..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // пока просто показываем тост и отображаем строку которую пользователь ввел в поиск
                    shortToast(query)

                    // убирает клавиатуру с видимости как только запрос был завершен
                    searchView.clearFocus()
                }
                return true
            }

            // здесь ничего не делаем потому что мы не хотем выполнять запрос пока мы все ещё пишем
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    //? метод для генерации временных данных
    private fun generateItemListGridLayout(size: Int): ArrayList<GridLayoutItem> {
        // the we create new empty arrayList<>
        val list = ArrayList<GridLayoutItem>()

        // and it uses the size value in the for loop to fill this list with data
        // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
        for (i in 0 until size) {
            // this part is only responsible for alternating between our 5 drawables
            val drawable = when (i % 5) {
                0 -> R.drawable.ic_airplane
                1 -> R.drawable.ic_money
                2 -> R.drawable.ic_wallet
                3 -> R.drawable.ic_anchor
                else -> R.drawable.icon_perosn
            }

            val header = when (i % 5) {
                0 -> stringGet(R.string.lost_passport)
                1 -> stringGet(R.string.lost_money)
                2 -> stringGet(R.string.lost_cat)
                3 -> stringGet(R.string.lost_airplane)
                else -> stringGet(R.string.lost_phone)
            }

            val description = when (i % 5) {
                0 -> stringGet(R.string.lost_description_passport)
                1 -> stringGet(R.string.lost_description_money)
                2 -> stringGet(R.string.lost_description_cat)
                3 -> stringGet(R.string.lost_description_airplane)
                else -> stringGet(R.string.lost_description_phone)
            }

            val time = when (i % 5) {
                0 -> stringGet(R.string.lost_time_stamp_one)
                1 -> stringGet(R.string.lost_time_stamp_two)
                2 -> stringGet(R.string.lost_time_stamp_three)
                3 -> stringGet(R.string.lost_time_stamp_four)
                else -> stringGet(R.string.lost_time_stamp_five)
            }

            val views = when (i % 5) {
                0 -> 900
                1 -> 619
                2 -> 532
                3 -> 238
                else -> 152
            }

            val location = when (i % 5) {
                0 -> stringGet(R.string.lost_location_one)
                1 -> stringGet(R.string.lost_location_two)
                2 -> stringGet(R.string.lost_location_three)
                3 -> stringGet(R.string.lost_location_four)
                else -> stringGet(R.string.lost_location_five)
            }

            val northPoint = when (i % 5) {
                0 -> 43.379217
                1 -> 43.292014
                2 -> 43.325446
                3 -> 43.328427
                else -> 43.322060
            }

            val eastPoint = when (i % 5) {
                0 -> 45.564101
                1 -> 45.684844
                2 -> 45.695388
                3 -> 45.705930
                else -> 45.694335
            }

            val adType = when (i % 5) {
                0 -> stringGet(R.string.ad_type_one)
                1 -> stringGet(R.string.ad_type_two)
                2 -> stringGet(R.string.ad_type_three)
                3 -> stringGet(R.string.ad_type_four)
                else -> stringGet(R.string.ad_type_five)
            }

            val adTypeObject = when (i % 5) {
                0 -> stringGet(R.string.ad_type_object_one)
                1 -> stringGet(R.string.ad_type_object_two)
                2 -> stringGet(R.string.ad_type_object_three)
                3 -> stringGet(R.string.ad_type_object_four)
                else -> stringGet(R.string.ad_type_object_five)
            }

            val category = when (i % 5) {
                0 -> stringGet(R.string.lost_document_text)
                1 -> stringGet(R.string.lost_money_text)
                2 -> stringGet(R.string.lost_animal_text)
                3 -> stringGet(R.string.lost_personal_belongings_text)
                else -> stringGet(R.string.lost_personal_belongings_text)
            }

            // creates new ExampleItem and passes through its constructor the necessary data
            val item = GridLayoutItem(
                    drawable,
                    header,
                    description,
                    time,
                    views,
                    location,
                    northPoint,
                    eastPoint,
                    adType = adType,
                    adTypeObject = adTypeObject,
                    category = category
            )
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }

    //? описание в FragmentFound
    override fun onItemClick(position: Int) {
        val clickedItem = ClickedItemDescription(
                exampleList[position].headerText,
                exampleList[position].descriptionText,
                exampleList[position].location,
                exampleList[position].northPoint,
                exampleList[position].eastPoint,
                exampleList[position].adType,
                exampleList[position].adTypeObject,
                exampleList[position].category,
        )
        val action = FragmentLostDirections.actionFragmentLostToОписание(clickedItem)
        findNavController().navigate(action)
    }
}