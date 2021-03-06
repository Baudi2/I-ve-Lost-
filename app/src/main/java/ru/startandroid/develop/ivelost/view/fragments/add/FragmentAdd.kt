package ru.startandroid.develop.ivelost.view.fragments.add


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.DatePicker
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.esafirm.imagepicker.features.*
import com.esafirm.imagepicker.model.Image
import kotlinx.android.synthetic.main.fragment_review.*
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentAddBinding
import ru.startandroid.develop.ivelost.module.data.HorizontalUriItem
import ru.startandroid.develop.ivelost.module.data.SelectDialogAdd
import ru.startandroid.develop.ivelost.view.recyclerView.AddUriHorizontalAdapter
import ru.startandroid.develop.ivelost.view.recyclerView.LinePagerIndicatorDecoration
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY
import ru.startandroid.develop.testprojectnavigation.utils.monthToString
import ru.startandroid.develop.testprojectnavigation.utils.shortToast
import java.util.*
import kotlin.collections.ArrayList


class FragmentAdd : Fragment(R.layout.fragment_add), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentAddBinding
    private val args: FragmentAddArgs by navArgs()
    private lateinit var array: ArrayList<HorizontalUriItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AddUriHorizontalAdapter
    private var isRecyclerViewDecoration = false

    private var day = 0
    private var month = 0
    private var year = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0

    //! ???????????????? ?????????????? ???? ???? ???????? ?????? launcher ???? ?????????????? ????????????????
    //! ?? ?? val ???????????????? ???? var
    private lateinit var launcher: ImagePickerLauncher


    private val images = arrayListOf<Image>()


    //! ?????????? launcher ?????? ???????????????? ???????????? ?????? ?????? ?????????????? ???????????????????? ???? ???????????????????? ???????????? ?? ???? ?????????????? ??????????????????
    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerImagePicker {
            images.clear()
            recyclerView.removeAllViews()
            array.clear()
            adapter.notifyDataSetChanged()
            if (it.isEmpty()) binding.addPhotoFragment.visibility = View.VISIBLE
            if (it.isNotEmpty()) binding.addPhotoFragment.visibility = View.GONE
            images.addAll(it)
            displayImages(images)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)

        recyclerView = binding.addPhotoRecyclerView
        array = ArrayList()
        adapter = AddUriHorizontalAdapter()

        val manager = LinearLayoutManager(APP_ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        recyclerView.layoutManager = manager
        snapHelper.attachToRecyclerView(recyclerView)

        binding.addPhotoFloatingButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    APP_ACTIVITY,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    APP_ACTIVITY,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
                return@setOnClickListener
            }
            start()
        }

        //! ???????????? ?????? ?????????????? ?????????????? ???????????? ???????? ?????????????????? ???????????? ?? ???????? ????????????
        binding.apply {
            buttonAddFragment.setOnClickListener {
                shortToast("???????????? ??????????????????")
            }

            selectTypeAddImageView.setColorFilter(R.color.black)
            mapsAddImageView.setColorFilter(R.color.black)
            timeAddImageView.setColorFilter(R.color.black)

            if (args.topic == "????????????" || args.topic == "???????????? ????????") {
                specifyAnAnimal.visibility = View.GONE
            }
            if (!args.isLost && args.topic == "????????") specifyAnAnimal.visibility = View.GONE
            selectTypeAddTextView.text = args.selectedItemString
            selectTypeAddImageView.setImageResource(decideImage())

            if (!args.isLost) {
                textViewReward.visibility = View.GONE
                switchReward.visibility = View.GONE
                rewardDescription.visibility = View.GONE
            }


            //! ?????????? ???????? ?????????????????????? ???? ??????????????????
            setMapLocation.setOnClickListener {
                val northPoint = 43.317596.toString()
                val eastPoint = 45.693837.toString()
                val location = "???????????? ??????????"
                val action = FragmentAddDirections.actionFragmentAddToMapsFragment(
                        location,
                        northPoint,
                        eastPoint
                )
                findNavController().navigate(action)
            }

            indicateTheTime.setOnClickListener {
                pickDate()
            }

            specifyAnAnimal.setOnClickListener {
                val dialogSent = SelectDialogAdd(
                    decideTypeOfAdd(), decideHeaderText(),
                    args.topic, args.isLost, binding.selectTypeAddTextView.text.toString()
                )
                val action =
                        FragmentAddDirections.actionFragmentAddToFragmentDialogSelectAdd(dialogSent)
                findNavController().navigate(action)
            }
            if (args.textViewColor != 0) binding.selectTypeAddTextView.setTextColor(args.textViewColor)
        }
    }

    private fun decideTypeOfAdd(): Int {
        return when (args.topic) {
            "????????????????" -> 1
            "??????????????????" -> 2
            "??????????????????????????" -> 3
            "????????" -> 4
            else -> 5
        }
    }

    private fun decideHeaderText(): String {
        return when (args.topic) {
            "??????????????????" -> "?????? ??????????????????"
            "????????????" -> "?????????? ??????????"
            "????????????????" -> "?????? ??????????????????"
            "????????" -> "???????? ??????????????"
            else -> "?????? ??????????????????????????"
        }
    }


    private fun decideImage(): Int {
        return when (args.topic) {
            "??????????????????" -> R.drawable.icon_documents
            "????????????" -> R.drawable.icon_money
            "????????????????" -> R.drawable.icon_paw
            "????????" -> R.drawable.icon_people
            else -> R.drawable.icon_jewelry
        }
    }

    private fun start() {
        launcher.launch(createConfig())
    }

    private fun createConfig(): ImagePickerConfig {
        return ImagePickerConfig {
            ImagePickerMode.MULTIPLE
            language = "ru"
            isFolderMode = true
            folderTitle = "??????????????????"
            imageTitle = "???????????????? ????????"
            doneButtonText = "??????????????????"
            showDoneButtonAlways = true
            limit = 6
            isShowCamera = false
            savePath = ImagePickerSavePath(
                Environment.getExternalStorageDirectory().path,
                isRelative = false
            ) // can be a full path
            selectedImages = images
        }

    }

    private fun displayImages(images: List<Image>?) {
        if (images == null) return
        for (element in images) {
            val temp = HorizontalUriItem(element.uri)
            array.add(temp)
        }

        adapter.inputData(array)
        recyclerView.adapter = adapter
        if (!isRecyclerViewDecoration) recyclerView.addItemDecoration(LinePagerIndicatorDecoration())
        isRecyclerViewDecoration = true
    }

    private fun pickDate() {
        getDate()
        DatePickerDialog(APP_ACTIVITY, this, year, month, day).show()
    }

    private fun getDate() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        val savedMonthString = monthToString(savedMonth)
        savedYear = year

        binding.timeAddTextView.text = "$savedDay/$savedMonthString/$savedYear ????????"
        if (!binding.timeAddTextView.text.contains("?????????????? ??????????")) binding.timeAddTextView.setTextColor(
            ContextCompat.getColor(APP_ACTIVITY,R.color.black)
        )
    }
}




