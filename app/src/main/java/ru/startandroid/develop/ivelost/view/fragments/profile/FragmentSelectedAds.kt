package ru.startandroid.develop.ivelost.view.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentSelectedAdsBinding
import ru.startandroid.develop.ivelost.module.data.ClickedItemDescription
import ru.startandroid.develop.ivelost.module.data.FragmentSelectedModule
import ru.startandroid.develop.ivelost.module.data.YourAddNavigate
import ru.startandroid.develop.ivelost.view.recyclerView.FragmentSelectedAdapter
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY
import ru.startandroid.develop.testprojectnavigation.utils.stringGet

class FragmentSelectedAds : Fragment(R.layout.fragment_selected_ads),
        FragmentSelectedAdapter.SelectedAddItemClickListener {
    private lateinit var binding: FragmentSelectedAdsBinding
    private lateinit var dummyData: ArrayList<FragmentSelectedModule>
    private val args: FragmentSelectedAdsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectedAdsBinding.bind(view)




        dummyData = generateItemListGridLayout(args.size)
        val manager = LinearLayoutManager(APP_ACTIVITY)
        val adapter = FragmentSelectedAdapter(this)
        adapter.inputData(dummyData)
        binding.apply {
            selectedRecyclerView.visibility = View.VISIBLE
            emptyRecyclerTextView.visibility = View.GONE
            selectedRecyclerView.layoutManager = manager
            selectedRecyclerView.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()
        if (args.size == 0) {
            binding.apply {
                selectedRecyclerView.visibility = View.GONE
                emptyRecyclerTextView.visibility = View.VISIBLE
                emptyRecyclerTextView.text = "Нет объявлений"
            }
        }
    }

    override fun onClickListenerAdd(position: Int) {
        val sentObject = ClickedItemDescription(
                dummyData[position].headerText,
                dummyData[position].descriptionText,
                dummyData[position].location,
                dummyData[position].northPoint,
                dummyData[position].eastPoint,
                dummyData[position].adType,
                dummyData[position].adTypeObject,
                dummyData[position].category,
        )
        val sentYouAddObject = YourAddNavigate(
                args.topic,
                decideTopic(),
                dummyData.size
        )
        val action = FragmentSelectedAdsDirections.actionFragmentSelectedAdsToFragmentDetailsFound(
                sentObject,
                sentYouAddObject
        )
        findNavController().navigate(action)
    }

    private fun decideTopic(): Int {
        return when (args.topic) {
            "В ожидании" -> 1
            "Активные" -> 2
            "Завершенные" -> 3
            "Отклоненные" -> 4
            else -> 5
        }
    }

    private fun generateItemListGridLayout(size: Int): ArrayList<FragmentSelectedModule> {
        // the we create new empty arrayList<>
        val list = ArrayList<FragmentSelectedModule>()
        var viewCount: Int

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

            viewCount =
                    if (args.topic == "Активные" || args.topic == "Избранное" || args.topic == "Завершенные") {
                        when (i % 5) {
                            0 -> 900
                            1 -> 619
                            2 -> 532
                            3 -> 238
                            else -> 152
                        }
                    } else {
                        0
                    }


            // creates new ExampleItem and passes through its constructor the necessary data
            val item = FragmentSelectedModule(
                    drawable,
                    header,
                    description,
                    time,
                    location,
                    northPoint,
                    eastPoint,
                    adType = adType,
                    adTypeObject = adTypeObject,
                    category = category,
                    viewCount = viewCount
            )
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }
}