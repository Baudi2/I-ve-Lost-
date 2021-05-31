package ru.startandroid.develop.ivelost.view.fragments.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.VerfifyFragmentBinding
import ru.startandroid.develop.ivelost.viewModel.FragmentVerifyViewModel
import ru.startandroid.develop.testprojectnavigation.utils.stringGet


class FragmentVerify : Fragment(R.layout.verfify_fragment) {
    private lateinit var binding: VerfifyFragmentBinding

    private lateinit var viewModel: FragmentVerifyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = VerfifyFragmentBinding.bind(view)

        with(binding) {
            textInfo.text = stringGet(R.string.confirm_email_text_view_text)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel = FragmentVerifyViewModel()
        viewModel.checkUserState()

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            if (it == true) {
                val action = FragmentVerifyDirections.actionFragmentVerifyToFragmentProfile2(true)
                findNavController().navigate(action)
            }
        }
    }
}








