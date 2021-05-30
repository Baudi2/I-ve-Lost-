package ru.startandroid.develop.ivelost.view.fragments.found

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
import ru.startandroid.develop.ivelost.databinding.FragmentFoundBinding
import ru.startandroid.develop.ivelost.module.data.ClickedItemDescription
import ru.startandroid.develop.ivelost.module.data.GridLayoutItem
import ru.startandroid.develop.ivelost.module.data.YourAddNavigate
import ru.startandroid.develop.ivelost.view.recyclerView.GridLayoutAdapter
import ru.startandroid.develop.testprojectnavigation.utils.*

class FragmentFound : Fragment(R.layout.fragment_found), GridLayoutAdapter.OnItemClickListener {
    //? binding; apply; bottomNavigation; fab clickListener, все это законментировано в FragmentProfile.kt
    private lateinit var binding: FragmentFoundBinding
    private lateinit var exampleItem: ArrayList<GridLayoutItem>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFoundBinding.bind(view)

        binding.apply {
            bottomNavFound.setupWithNavController(findNavController())

            fabFound.setOnClickListener {
                val action = FragmentFoundDirections.actionGlobalFragmentAddLostFind2()
                findNavController().navigate(action)
            }
        }

        //? подключаем поиск на тулбар с иконкой поиска
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exampleItem = generateItemList(30)

        val manager = GridLayoutManager(activity, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = when (position) {
                0 -> 2
                else -> 1
            }
        }
        val adapter = GridLayoutAdapter(exampleItem, this, requireContext())

        binding.apply {
            recyclerFoundView.layoutManager = manager
            recyclerFoundView.adapter = adapter
            //? оптимизирует работу recyclerView если у его items размер фиксированный
            recyclerFoundView.setHasFixedSize(true)
        }
    }

    //? убираем видимость у иконки home из drawerLayout и разблокируем его выдвижение
    override fun onStart() {
        super.onStart()
        unlockDrawer()
        hideHome()
    }

    //? создаем меню с лупой которая при нажатии становится поисковым меню в toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        //? инициализируем поисковое меню
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Поиск..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    //? пока просто показываем тост и отображаем строку которую пользователь ввел в поиск
                    shortToast(query)

                    //? убирает клавиатуру с видимости как только запрос был завершен
                    searchView.clearFocus()
                }
                return true
            }

            //? здесь ничего не делаем потому что мы не хотем выполнять запрос пока мы все ещё пишем
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun generateItemList(size: Int): ArrayList<GridLayoutItem> {
        // the we create new empty arrayList<>
        val list = ArrayList<GridLayoutItem>()

        // and it uses the size value in the for loop to fill this list with data
        // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
        for (i in 0 until size) {
            // this part is only responsible for alternating between our 5 drawables
            val drawable = when (i % 5) {
                0 -> R.drawable.icon_perosn
                1 -> R.drawable.ic_airplane
                2 -> R.drawable.ic_money
                3 -> R.drawable.ic_wallet
                else -> R.drawable.ic_anchor
            }

            val header = when (i % 5) {
                0 -> stringGet(R.string.found_passport)
                1 -> stringGet(R.string.found_money)
                2 -> stringGet(R.string.found_cat)
                3 -> stringGet(R.string.found_airplane)
                else -> stringGet(R.string.found_phone)
            }

            val description = when (i % 5) {
                0 -> stringGet(R.string.found_description_passport)
                1 -> stringGet(R.string.found_description_money)
                2 -> stringGet(R.string.found_description_cat)
                3 -> stringGet(R.string.found_description_airplane)
                else -> stringGet(R.string.found_description_phone)
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
                    drawable, header, description, time, views, location, northPoint, eastPoint,
                    adType = adType, adTypeObject = adTypeObject, category = category
            )
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }

    //? для перехода на фрагмент детали нам нужно передать следующие аргументы туда
    override fun onItemClick(position: Int) {
        explainClickedArguments()   //!.
        val clickedItem = ClickedItemDescription(
                exampleItem[position].headerText,
                exampleItem[position].descriptionText,
                exampleItem[position].location,
                exampleItem[position].northPoint,
                exampleItem[position].eastPoint,
                exampleItem[position].adType,
                exampleItem[position].adTypeObject,
                exampleItem[position].category,
        )
        val emptyObject = YourAddNavigate(null, 0, 0)
        val action = FragmentFoundDirections.actionFragmentFoundToFragmentDetailsFound2(clickedItem, emptyObject)
        findNavController().navigate(action)
    }
}