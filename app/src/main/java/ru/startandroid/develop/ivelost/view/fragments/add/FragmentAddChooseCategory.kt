package ru.startandroid.develop.ivelost.view.fragments.add

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentAddChooseCategoryBinding
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY

class FragmentAddChooseCategory : Fragment(R.layout.fragment_add_choose_category) {

    private lateinit var binding: FragmentAddChooseCategoryBinding
    private val args: FragmentAddChooseCategoryArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddChooseCategoryBinding.bind(view)
        val sentBoolean = args.isLost

        //? устанавливаем слушатели нажатий на каждую кнопку которая производит навигацию
        binding.apply {
            textViewLostDocument.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_document_text)
                val documentType = "Укажите тип документа"
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, documentType)
                findNavController().navigate(action)
            }

            textViewLostMoney.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_money_text)
                val moneyType = "Укажите сумму"
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, moneyType)
                findNavController().navigate(action)
            }

            textViewLostAnimals.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_animal_text)
                val animalType = "Укажите тип животного"
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, animalType)
                findNavController().navigate(action)
            }

            textViewLostPeople.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_people_text)
                val peopleType = "Укажите ваше родство"
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, peopleType)
                findNavController().navigate(action)
            }

            textViewLostJewelry.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_jewelry_text)
                val jewelryType = "Укажите тип драгоценности"
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, jewelryType)
                findNavController().navigate(action)
            }

            textViewLostEquipment.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_personal_belongings_text)
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, "")
                findNavController().navigate(action)
            }

            textViewLostOther.setOnClickListener {
                val sent = APP_ACTIVITY.getString(R.string.lost_other_text)
                val action =
                        FragmentAddChooseCategoryDirections.actionFragmentAddToFragmentAddEdit(sent, sentBoolean, "")
                findNavController().navigate(action)
            }
        }
    }

    //? тут мы включаем анимации на наши textView каждая анимация с разной задержкой
    override fun onResume() {
        super.onResume()
        //? определеяем анимации
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up)
        val animDelay230 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_230)
        val animDelay260 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_260)
        val animDelay290 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_290)
        val animDelay320 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_320)
        val animDelay350 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_350)
        val animDelay380 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_380)
        val animDelay410 = AnimationUtils.loadAnimation(requireContext(), R.anim.text_show_up_delay_410)


        //? присваиваем их к нужным textView
        binding.apply {
            textViewWhatYouLost.startAnimation(anim)
            textViewLostDocument.startAnimation(animDelay230)
            textViewLostMoney.startAnimation(animDelay260)
            textViewLostAnimals.startAnimation(animDelay290)
            textViewLostPeople.startAnimation(animDelay320)
            textViewLostJewelry.startAnimation(animDelay350)
            textViewLostEquipment.startAnimation(animDelay380)
            textViewLostOther.startAnimation(animDelay410)

            //? меняем назваие в зависимости от того зашли мы через нашел или потерял
            textViewWhatYouLost.text = args.lostFound
        }
    }
}