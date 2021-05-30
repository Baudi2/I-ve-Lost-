package ru.startandroid.develop.ivelost.view.fragments.found

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentDetailsFoundBinding
import ru.startandroid.develop.ivelost.module.data.HorizontalLayoutItem
import ru.startandroid.develop.ivelost.view.recyclerView.HorizontalAdapter
import ru.startandroid.develop.ivelost.view.recyclerView.LinePagerIndicatorDecoration
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY
import ru.startandroid.develop.testprojectnavigation.utils.lockDrawer
import ru.startandroid.develop.testprojectnavigation.utils.longToast

class FragmentDetailsFound : Fragment(R.layout.fragment_details_found),
    HorizontalAdapter.HorizontalItemClickListener {

    private lateinit var binding: FragmentDetailsFoundBinding
    private val args: FragmentDetailsFoundArgs by navArgs()
    private val dummyData = generateItemList(6)
    private var dialogDeleteAdd: AlertDialog? = null
    private var isYourAdd: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsFoundBinding.bind(view)

        val header = args.clickedItem.header
        val description = args.clickedItem.description

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper: SnapHelper = PagerSnapHelper()

        if (args.youradddata.yourAdDistinction != 0) {
            setHasOptionsMenu(true)
        } else {
            setHasOptionsMenu(false)
        }

        setInfo(args.youradddata.yourAdDistinction)

        binding.detailtFoundRecyclerView.adapter = HorizontalAdapter(dummyData, this)
        binding.apply {
            detailtFoundRecyclerView.setHasFixedSize(true)
            detailtFoundRecyclerView.layoutManager = manager
            //? при перемещении элементы четко встают в свою позицию т.е. не гуляют свободно
            snapHelper.attachToRecyclerView(detailtFoundRecyclerView)
            //? связываем наш индикатор элементов с recyclerView
            detailtFoundRecyclerView.addItemDecoration(LinePagerIndicatorDecoration())

            headerFoundDetails.text = header
            descriptionFoundDetails.text = description

            //? при нажатии показать на карте передаем в фрагмент с картой имя места и его координаты
            detailsFoundShowMap.setOnClickListener {
                val action =
                        FragmentDetailsFoundDirections.actionFragmentDetailsFoundToMapsFragment(
                                args.clickedItem.location,
                                args.clickedItem.northPoint.toString(),
                                args.clickedItem.eastPoint.toString()
                        )
                findNavController().navigate(action)
            }

            //? меняем тип описания в зависимости от того на какое объявление мы перешли
            //? если объявление о документах то будет описание про то что потерян докумен и его тип и т.д.
            chooseCategoryFoundFragment.text = args.clickedItem.category
            adTypeFoundTextView.text = args.clickedItem.adType
            adTypeObjectFoundTextView.text = args.clickedItem.adTypeObject
        }
    }

    //? блокируем появление drawerLayout при заходе в этот фрагмент
    override fun onStart() {
        super.onStart()
        lockDrawer()
    }

    //? слушатель нажатий на нашем recyclerView, при нажатии переходим на фрагмент где можно зумить фото
    //? также передаем позицию нажетого фото чтобы там recyclerView открылся на нужном месте.
    override fun onHorizontalItemClickListener(position: Int) {
        val clickedItem = dummyData[position].image
        val action =
                FragmentDetailsFoundDirections.actionFragmentDetailsFoundToFragmentDetailsSelectedPhoto(
                        clickedItem,
                        position,
                        isYourAdd
                )
        findNavController().navigate(action)
    }

    //? метод для генерации временных данных
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
            val item = HorizontalLayoutItem(drawable, 0)
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }

    override fun onPause() {
        super.onPause()
        if (dialogDeleteAdd != null) {
            dialogDeleteAdd!!.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //? объяснено в fragment profile
        inflater.inflate(R.menu.menu_you_add_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.your_add_delete_menu -> {
                deleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(R.string.your_add_menu_delete_header)
        alertDialog.setMessage(R.string.your_add_menu_delete_description)
        alertDialog.setIcon(R.drawable.icon_alert_dialog)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(R.string.your_add_menu_delete_positive) { _, _ ->
            val action =
                    FragmentDetailsFoundDirections.actionFragmentDetailsFoundToFragmentSelectedAds(
                            args.youradddata.topic!!,
                            args.youradddata.size
                    )
            findNavController().navigate(action)
            longToast("Объявление было удалено")
        }
        alertDialog.setNegativeButton(R.string.your_add_menu_delete_negative) { _, _ ->
            dialogDeleteAdd!!.dismiss()
        }

        dialogDeleteAdd = alertDialog.create()
        dialogDeleteAdd!!.show()
    }

    private fun setInfo(n: Int) {
        if (n == 1) {
            binding.backgroundYourAddInfo.setBackgroundColor(APP_ACTIVITY.resources.getColor(R.color.waiting_add))
            binding.infoYourAddImage.setImageResource(R.drawable.pending_icon)
            binding.infoYourAddText.text = "Ожидается подтверждение ващего объявления. Спасибо за понимание!"
            isYourAdd = true
        }
        if (n == 2) isYourAdd
        if (n == 3) {
            binding.backgroundYourAddInfo.setBackgroundColor(APP_ACTIVITY.resources.getColor(R.color.complete_add))
            binding.infoYourAddImage.setImageResource(R.drawable.l)
            binding.infoYourAddText.text = "Публикация была завершена"
            isYourAdd = true
        }
        if (n == 4) {
            binding.backgroundYourAddInfo.setBackgroundColor(APP_ACTIVITY.resources.getColor(R.color.rejected_add))
            binding.infoYourAddImage.setImageResource(R.drawable.rejected_icon)
            binding.infoYourAddText.text = "Ваша публкация была отклонена по причине: Не соответсвие менталитету!"
            isYourAdd = true
        }
        if (n == 5) isYourAdd = false
    }
}