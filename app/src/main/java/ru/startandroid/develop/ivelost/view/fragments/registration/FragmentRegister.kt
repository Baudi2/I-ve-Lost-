package ru.startandroid.develop.ivelost.view.fragments.registration

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentRegisterBinding
import ru.startandroid.develop.ivelost.module.data.User
import ru.startandroid.develop.ivelost.view.dialogs.ProgressDialog
import ru.startandroid.develop.testprojectnavigation.utils.*
import java.util.*

//? макет фрагмента готов, остается подключить логику.
class FragmentRegister : Fragment(R.layout.fragment_register) {

    companion object {
        const val LOG_TAG = "myLogs"
    }

    private lateinit var binding: FragmentRegisterBinding
    private var selectedPhotoUri: Uri? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDb: DatabaseReference
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        dialog = ProgressDialog.progressDialog(requireContext())

        binding.apply {
            alreadyHaveAccountTextView.setOnClickListener {
                val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentLogin()
                findNavController().navigate(action)
            }

            registerButtonRegister.setOnClickListener {
                performRegister(it)
            }

            selectphotoButtonRegister.setOnClickListener {
                forPhotoButton()
            }
        }

        //? firebase authenticator
        mAuth = FirebaseAuth.getInstance()
        mDb = Firebase.database.reference
    }

    //? метод для проведения регистрации
    private fun performRegister(view: View) {
        //? получаем данные из edit text которые ввел юзер
        val userName = binding.tIETName.text.toString()
        val email = binding.tIETEmail.text.toString()
        val password = binding.tIETPassword.text.toString()

        dialog.show()



        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (!it.isSuccessful) return@addOnCompleteListener

                    // here the user is successfully created
                    Log.d(LOG_TAG, "Successfully created user with uid: ${it.result?.user?.uid}")

                    //? загрузка фотографии пользователя на сервер в firebase
                    if (binding.selectphotoImageviewRegister.drawable != null) {
                        uploadImageToFirebaseStorage(userName, email)
                    } else {
                        saveUserToFirebaseDatabase(null, userName, email)
                    }
                }.addOnFailureListener {
                    dialog.dismiss()
                    firebaseEnglishExceptionToRussian(it.message)
                }.addOnSuccessListener {
                    //dialog.show()
                }
            hideKeyboard(view)
        } else {
            //? если поля пустым предупреждаем юзера что их надо заполнить
            Toast.makeText(
                requireContext(),
                R.string.fragment_register_login_toast_fill_data,
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }
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

            //? в зависимости от версии устройства используем два разных метода для обработки и выставления
            //? в imageView картинки которую выбрал юзер
            try {
                selectedPhotoUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            selectedPhotoUri
                        )
                        binding.selectphotoImageviewRegister.setImageBitmap(bitmap)
                        binding.selectphotoButtonRegister.alpha = 0f
                    } else {
                        val source = ImageDecoder.createSource(
                            requireActivity().contentResolver,
                            selectedPhotoUri!!
                        )
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        binding.selectphotoImageviewRegister.setImageBitmap(bitmap)
                        binding.selectphotoButtonRegister.alpha = 0f
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //? прячем клавиатуру при выходе с фрагмента
    override fun onStop() {
        super.onStop()
        hideKeyboard(requireView())
    }


    private fun uploadImageToFirebaseStorage(name: String, email: String) {
        //? проверяем что фото есть
        if (selectedPhotoUri == null) return
        //? случайно длинная строка для создания уникального имени хранения images
        val filename = UUID.randomUUID().toString()
        //? получаем доступ к хранилищу и создаем там нод с для изображений
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        //? загружаем фото
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(LOG_TAG, "Successfully uploaded image: ${it.metadata?.path}")

                //? получаем доступ к file location
                ref.downloadUrl.addOnSuccessListener {
                    Log.d(LOG_TAG, "File location: $it")
                    saveUserToFirebaseDatabase(it.toString(), name, email)
                }
            }.addOnFailureListener {
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String?, name: String, email: String) {
        //? получаем доступ к юзер айди
        val uid = FirebaseAuth.getInstance().uid ?: ""
        //? сохраняем данные юзера пользователя в нод
        val user = User(uid, name, email, profileImageUrl)
        val currentUser = FirebaseAuth.getInstance().currentUser ?: null

        //? сохраняем юзер объект на firebase database
        mDb.child("users").child(uid).setValue(user)
            .addOnCompleteListener {
                dialog.dismiss()
                if (!currentUser!!.isEmailVerified) {
                    val action = FragmentRegisterDirections.actionFragmentRegisterToFragmentVerify()
                    findNavController().navigate(action)
                }
            }
    }
}









