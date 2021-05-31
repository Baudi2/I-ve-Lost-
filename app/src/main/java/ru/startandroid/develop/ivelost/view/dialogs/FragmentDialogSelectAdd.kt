package ru.startandroid.develop.ivelost.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.PickerFragmentDialogBinding
import ru.startandroid.develop.ivelost.module.data.HeaderItem
import ru.startandroid.develop.ivelost.view.recyclerView.HeaderAdapter
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY
import ru.startandroid.develop.testprojectnavigation.utils.selectedListOfTypes

class FragmentDialogSelectAdd : BottomSheetDialogFragment(), HeaderAdapter.HeaderItemListener {
    private lateinit var binding: PickerFragmentDialogBinding
    private lateinit var adapter: HeaderAdapter
    private lateinit var data: ArrayList<HeaderItem>
    private var returnTextView = 0
    private val args: FragmentDialogSelectAddArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = PickerFragmentDialogBinding.inflate(layoutInflater)

        val manager = LinearLayoutManager(requireContext())
        adapter = HeaderAdapter(this)
        chooseTopic()
        binding.recyclerViewPicker.layoutManager = manager
        binding.recyclerViewPicker.adapter = adapter


        binding.apply {
            buttonClose.setOnClickListener {
                val action =
                    FragmentDialogSelectAddDirections.actionFragmentDialogSelectAddToFragmentAdd(
                        args.dialogObject.topic,
                        args.dialogObject.isLost, args.dialogObject.textViewDefaultText,
                    )
                findNavController().navigate(action)
            }
        }
        return binding.root
    }

    override fun onHeaderItemListener(position: Int) {
        val action =
            FragmentDialogSelectAddDirections.actionFragmentDialogSelectAddToFragmentAdd(
                args.dialogObject.topic,
                args.dialogObject.isLost, data[position].headerTopic,
                ContextCompat.getColor(APP_ACTIVITY, R.color.black)
            )
        findNavController().navigate(action)
    }


    private fun chooseTopic() {
        when (args.dialogObject.typeOfAdd) {
            1 -> {
                data = selectedListOfTypes(1)
                returnTextView = 1
                adapter.inputData(data)
                binding.textViewHeaderDialog.text = args.dialogObject.headerText
            }
            2 -> {
                data = selectedListOfTypes(2)
                returnTextView = 2
                adapter.inputData(data)
                binding.textViewHeaderDialog.text = args.dialogObject.headerText
            }
            3 -> {
                data = selectedListOfTypes(3)
                returnTextView = 3
                adapter.inputData(data)
                binding.textViewHeaderDialog.text = args.dialogObject.headerText
            }
            4 -> {
                data = selectedListOfTypes(4)
                returnTextView = 4
                adapter.inputData(data)
                binding.textViewHeaderDialog.text = args.dialogObject.headerText
            }
            5 -> {
                data = selectedListOfTypes(5)
                returnTextView = 5
                adapter.inputData(data)
                binding.textViewHeaderDialog.text = args.dialogObject.headerText
            }
        }
    }
}