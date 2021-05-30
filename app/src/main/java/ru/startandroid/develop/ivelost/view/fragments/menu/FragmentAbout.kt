package ru.startandroid.develop.ivelost.view.fragments.menu

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentAboutBinding
import ru.startandroid.develop.testprojectnavigation.utils.copyText
import ru.startandroid.develop.testprojectnavigation.utils.explainExpandableViews
import ru.startandroid.develop.testprojectnavigation.utils.shortToast
import ru.startandroid.develop.testprojectnavigation.utils.showHome

class FragmentAbout : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutBinding.bind(view)

        explainExpandableViews()    //!..
        binding.apply {
            fragmentAboutWhoAreWe.setOnClickListener {
                if (expandTextWhoAreWe.isExpanded) {
                    expandTextWhoAreWe.collapse()
                } else {
                    if (expandTextWhoAreWe2.isExpanded) {
                        expandTextWhoAreWe2.collapse()
                    }
                    if (expandTextWhoAreWe3.isExpanded) {
                        expandTextWhoAreWe3.collapse()
                    }
                    expandTextWhoAreWe.expand()
                }
            }

            fragmentAboutWhoAreWe2.setOnClickListener {
                if (expandTextWhoAreWe2.isExpanded) {
                    expandTextWhoAreWe2.collapse()
                } else {
                    if (expandTextWhoAreWe.isExpanded) {
                        expandTextWhoAreWe.collapse()
                    }
                    if (expandTextWhoAreWe3.isExpanded) {
                        expandTextWhoAreWe3.collapse()
                    }
                    expandTextWhoAreWe2.expand()
                }
            }

            fragmentAboutWhoAreWe3.setOnClickListener {
                if (expandTextWhoAreWe3.isExpanded) {
                    expandTextWhoAreWe3.collapse()
                } else {
                    if (expandTextWhoAreWe.isExpanded) {
                        expandTextWhoAreWe.collapse()
                    }
                    if (expandTextWhoAreWe2.isExpanded) {
                        expandTextWhoAreWe2.collapse()
                    }
                    expandTextWhoAreWe3.expand()
                }
            }

            fragmentAboutWhoAreWe4.setOnClickListener {
                if (expandTextWhoAreWe4.isExpanded) {
                    expandTextWhoAreWe4.collapse()
                } else {
                    expandTextWhoAreWe4.expand()
                }
            }

            fragmentAboutWhoAreWe5.setOnClickListener {
                if (expandTextWhoAreWe5.isExpanded) {
                    expandTextWhoAreWe5.collapse()
                } else {
                    expandTextWhoAreWe5.expand()
                }
            }

            fragmentAboutWhoAreWe6.setOnClickListener {
                if (expandTextWhoAreWe6.isExpanded) {
                    expandTextWhoAreWe6.collapse()
                } else {
                    expandTextWhoAreWe6.expand()
                }
            }

            copyEmailAbout.setOnClickListener {
                copyText(it as TextView)
                shortToast("Текст скопирован")
            }

            copyTelegramAbout.setOnClickListener {
                copyText(it as TextView)
                shortToast("Текст скопирован")
            }

            copyWhatsappAbout.setOnClickListener {
                copyText(it as TextView)
                shortToast("Текст скопирован")
            }
        }
    }

    //? показываем иконку home в drawerLayout
    override fun onStart() {
        super.onStart()
        showHome()
    }
}