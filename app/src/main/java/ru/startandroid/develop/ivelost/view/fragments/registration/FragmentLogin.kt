package ru.startandroid.develop.ivelost.view.fragments.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentLoginBinding
import ru.startandroid.develop.ivelost.view.fragments.registration.FragmentRegister.Companion.LOG_TAG
import ru.startandroid.develop.testprojectnavigation.utils.hideKeyboard
import ru.startandroid.develop.testprojectnavigation.utils.longToast
import ru.startandroid.develop.testprojectnavigation.utils.shortToast

//? макет фрагмента готов, остается подключить логику.
class FragmentLogin : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.apply {
            backToRegisterTextView.setOnClickListener {
                activity?.onBackPressed()
            }

            loginButtonLogin.setOnClickListener {
                performLogin(it)
            }
        }
        mAuth = FirebaseAuth.getInstance()
    }

    //? почти тоже самое что и при регистрации, проверяем поля, перекидываем в профиль
    private fun performLogin(view: View) {
        val email = binding.tIETLoginMail.text.toString()
        val password = binding.tIETLoginPassword.text.toString()
        val isRegistered = true

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        //TODO: add progress bar & hide in onComplete
                        Log.d(LOG_TAG, "processing")
                    }.addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        Log.d(LOG_TAG, "successfully logged in with uid: ${it.result?.user?.uid}")
                        shortToast("You've logged in!")
                    }.addOnFailureListener {
                        Log.d(LOG_TAG, "something's wrong")
                        longToast("Failed login process due to: ${it.message}")
                    }
//            val action = FragmentLoginDirections.actionFragmentLoginToFragmentProfile(isRegistered)
//            findNavController().navigate(action)
            hideKeyboard(view)
        } else {
            Toast.makeText(requireContext(), R.string.fragment_register_login_toast_fill_data, Toast.LENGTH_SHORT).show()
        }
    }
}