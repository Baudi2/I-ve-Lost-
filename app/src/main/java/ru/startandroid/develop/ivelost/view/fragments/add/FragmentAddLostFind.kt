package ru.startandroid.develop.ivelost.view.fragments.add

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentChooseLostFoundBinding
import ru.startandroid.develop.testprojectnavigation.utils.lockDrawer

class FragmentAddLostFind : Fragment(R.layout.fragment_choose_lost_found) {
    private lateinit var binding: FragmentChooseLostFoundBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseLostFoundBinding.bind(view)
        val sentStringLost = context?.getString(R.string.what_you_lost)
        val sentStringFound = context?.getString(R.string.what_you_found)
        var sentIsLost: Boolean

        //? в зависимости от того нажали мы на "потерял" или "нашел" передаем нужную строку и меняем значение
        //? sentIsLost на true если нажали на "потерял"
        binding.apply {
            textLost.setOnClickListener {
                sentIsLost = true
                val action = FragmentAddLostFindDirections.actionFragmentAddLostFind2ToFragmentAdd(
                        sentStringLost!!,
                        sentIsLost
                )
                findNavController().navigate(action)
            }

            textFound.setOnClickListener {
                sentIsLost = false
                val action = FragmentAddLostFindDirections.actionFragmentAddLostFind2ToFragmentAdd(
                        sentStringFound!!,
                        sentIsLost
                )
                findNavController().navigate(action)
            }
        }
    }

    //? загружаем и присваимваем анимации также блокируем drawer layout чтобы его нельзя было вытянуть
    override fun onResume() {
        super.onResume()
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up)
        val animDelay230 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_230)

        binding.apply {
            textLost.startAnimation(anim)
            textFound.startAnimation(animDelay230)
        }
        lockDrawer()
    }
}















