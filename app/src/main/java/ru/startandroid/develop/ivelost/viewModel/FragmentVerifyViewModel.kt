package ru.startandroid.develop.ivelost.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentVerifyViewModel : ViewModel() {
    private val user = FirebaseAuth.getInstance().currentUser ?: null

    private val _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean>
        get() = _navigateUp

    fun checkUserState() {
        _navigateUp.value = false
        if (user == null) return
        if (!user.isEmailVerified) {
            user.sendEmailVerification()
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while (!user.isEmailVerified) {
                    delay(1000)
                    user.reload()
                }
                withContext(Dispatchers.Main) {
                    if (user.isEmailVerified) {
                        _navigateUp.value = true
                    }
                }
            }
        }
    }
}