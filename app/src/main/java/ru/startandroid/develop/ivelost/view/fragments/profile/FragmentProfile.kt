package ru.startandroid.develop.ivelost.view.fragments.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentProfileBinding
import ru.startandroid.develop.testprojectnavigation.utils.*

class FragmentProfile : Fragment(R.layout.fragment_profile) {

    //? переменная binding через которую мы можем безопасно получать доступ к view с xml layout который связан
    //? с этим фрагментом
    private lateinit var binding: FragmentProfileBinding

    //? тут мы принимаем аргумент из фрагменты регистрации или логина об успешной регистрации чтобы больше не показывать диалог
    private val args: FragmentProfileArgs by navArgs()
    private var selectedPhotoUri: Uri? = null
    private var dialogRegister: AlertDialog? = null
    private var dialogLogout: AlertDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        setHasOptionsMenu(true)

        binding.apply {

            profileChangePhoto.setOnClickListener {
                forPhotoButton()
            }

            bottomNavProfile.setupWithNavController(findNavController())
            fabProfile.setOnClickListener {
                val action = FragmentProfileDirections.actionGlobalFragmentAddLostFind2()
                findNavController().navigate(action)
            }

            profilePending.setOnClickListener {
                navigateSelectedAds("В ожидании", 3)
            }
            waitingCounter.text = "3"
            profileActive.setOnClickListener {
                navigateSelectedAds("Активные", 0)
            }
            activeCounter.text = "0"
            profileCompleted.setOnClickListener {
                navigateSelectedAds("Завершенные", 7)
            }
            completedCounter.text = "7"
            profileRejected.setOnClickListener {
                navigateSelectedAds("Отклоненные", 2)
            }
            rejectedCounter.text = "2"
            profileFavorites.setOnClickListener {
                navigateSelectedAds("Избранное", 12)
            }
            favoritesCounter.text = "12"
        }
    }


    //? запускаем диалоговое окно каждый когда переходим сюда, в дальнейшем поменяем это певедние.
    override fun onStart() {
        super.onStart()
        if (!args.isRegistered) {
            registerDialog()
        }
        //? прячем иконку бургер и убираем возможность вытягивать drawerLayout
        lockDrawer()
        hideDrawer()
    }

    //? Создаем алерт диалог который будет появляться если пользователь попытается зайти в
    //? фрагмент профеля без регистрации. Этот диалог должен перекинуть его в фрагмент для
    //? дальнейшей регистрации и логина.
    private fun registerDialog() {
        //? инициализируем alertDialog
        val alertDialog = AlertDialog.Builder(requireContext())
        //? заголовок
        alertDialog.setTitle(R.string.register_alert_dialog_header)
        //? основное содержения
        alertDialog.setMessage(R.string.register_alert_dialog_message)
        alertDialog.setIcon(R.drawable.icon_alert_dialog)
        //? убераем возможность закрывать диалоговое окно нажитием в сторону
        alertDialog.setCancelable(true)
        //? ставим кнопку в алерт сообщение для подтверждения
        //? в случае подтверждения кидаем на регистрацию
        alertDialog.setPositiveButton(R.string.register_alert_dialog_positive_button) { _, _ ->
            val action = FragmentProfileDirections.actionFragmentProfileToFragmentRegister()
            findNavController().navigate(action)
        }
        //? в случае отказа кидаем на фрагмент потерял и говорим что без регистрации доступ в профиль закрыт
        alertDialog.setNegativeButton(R.string.register_alert_dialog_negative_button) { _, _ ->
            val action = FragmentProfileDirections.actionFragmentProfileToFragmentLost()
            findNavController().navigate(action)
            Toast.makeText(
                    requireContext(),
                    R.string.register_alert_dialog_negative_toast,
                    Toast.LENGTH_LONG
            ).show()
        }

        //? создаем и запускаем диалог
        dialogRegister = alertDialog.create()
        dialogRegister!!.show()
    }


    //? метод через который мы будем получать фото для профиля юзера
    private fun forPhotoButton() {
        //? через этот интент мы переходим в выбор фото
        val intent = Intent(Intent.ACTION_PICK)
        //? ты мы указываем то что тип данных которые мы хотим получить это фото
        intent.type = "image/*"
        //? теперь нам нужен результат выбора юзером фото
        startActivityForResult(intent, 0)
    }


    //? этот метод вызывается всякий раз, когда мы завершаем наше intent из forPhotoButton ()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //? убеждаемся, что мы используем правильный код запроса, что получение результата из
        //? intent прошло успешно и что полученные нами данные не равны null
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //? продолжаем и проверяем, какое было выбранное изображение

            explainActivityForResultPhoto() //!.
            selectedPhotoUri = data.data

            try {
                selectedPhotoUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                                activity?.contentResolver,
                                selectedPhotoUri
                        )
                        binding.profileUserPhoto.setImageBitmap(bitmap)

                    } else {
                        val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                selectedPhotoUri!!
                        )
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        binding.profileUserPhoto.setImageBitmap(bitmap)

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard(requireView())
    }

    //? тут закрываем диалог если он был не закрыт на момент срабатывания этого метода
    override fun onPause() {
        super.onPause()
        if (dialogRegister != null) {
            dialogRegister!!.dismiss()
        }
        if (dialogLogout != null) {
            dialogLogout!!.dismiss()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //? объяснено в fragment profile
        inflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile_menu_logout -> {
                logoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logoutDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(R.string.dialog_logout_title)
        alertDialog.setMessage(R.string.dialog_logout_description)
        alertDialog.setIcon(R.drawable.icon_alert_dialog)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton(R.string.dialog_logout_positive) { _, _ ->
            val action = FragmentProfileDirections.actionFragmentProfileToFragmentLost()
            findNavController().navigate(action)
            shortToast(APP_ACTIVITY.getString(R.string.dialog_logout_toast))
        }
        alertDialog.setNegativeButton(R.string.dialog_logout_negative) { _, _ ->
            dialogLogout!!.dismiss()
        }

        dialogLogout = alertDialog.create()
        dialogLogout!!.show()
    }

    private fun navigateSelectedAds(topic: String, size: Int) {
        val action = FragmentProfileDirections.actionFragmentProfileToFragmentSelectedAds(topic, size)
        findNavController().navigate(action)
    }
}























