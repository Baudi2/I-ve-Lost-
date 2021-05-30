package ru.startandroid.develop.ivelost.view.fragments.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentReviewBinding
import ru.startandroid.develop.testprojectnavigation.utils.explainExpandableViews
import ru.startandroid.develop.testprojectnavigation.utils.shortToast
import ru.startandroid.develop.testprojectnavigation.utils.showHome

class FragmentReview : Fragment(R.layout.fragment_review) {

    private lateinit var binding: FragmentReviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        explainExpandableViews()    //!..
        binding.apply {
            textView7.setOnClickListener {
                if (expandSupportProject.isExpanded) {
                    expandSupportProject.collapse()
                } else {
                    if (expandBetterProject.isExpanded) {
                        expandBetterProject.collapse()
                    }
                    if (expandErrorProject.isExpanded) {
                        expandErrorProject.collapse()
                    }
                    expandSupportProject.expand()
                }
            }
            textViewSupportFinancial.setOnClickListener {
                shortToast("Перекидываем чтобы сделать оплату")
            }

            textView8.setOnClickListener {
                shortToast("Отправляем в play market поставить оценку")
            }

            textView9.setOnClickListener {
                if (expandBetterProject.isExpanded) {
                    expandBetterProject.collapse()
                } else {
                    if (expandSupportProject.isExpanded) {
                        expandSupportProject.collapse()
                    }
                    if (expandErrorProject.isExpanded) {
                        expandErrorProject.collapse()
                    }
                    expandBetterProject.expand()
                }
            }

            textViewBetter.setOnClickListener {
                shortToast("Направляем либо в майл либо в фрагмент для написания письма.")
            }

            textView10.setOnClickListener {
                if (expandErrorProject.isExpanded) {
                    expandErrorProject.collapse()
                } else {
                    if (expandBetterProject.isExpanded) {
                        expandBetterProject.collapse()
                    }
                    if (expandSupportProject.isExpanded) {
                        expandSupportProject.collapse()
                    }
                    expandErrorProject.expand()
                }
            }

            textViewError.setOnClickListener {
                shortToast("Направляем либо в майл либо в фрагмент для написания письма.")
            }
        }
    }

    //? показываем иконку домой в drawerLayout
    override fun onStart() {
        super.onStart()
        showHome()
    }
}













