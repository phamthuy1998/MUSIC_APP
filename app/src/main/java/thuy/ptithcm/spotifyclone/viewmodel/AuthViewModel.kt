package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.repository.UserRepository
import thuy.ptithcm.spotifyclone.utils.isValidPassword

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email = MutableLiveData<String>().apply { value = "" }
    var password = MutableLiveData<String>().apply { value = "" }
    val resultData = MutableLiveData<NetworkState>()

    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    //function to perform login
    fun login() {
        resultData.postValue(NetworkState.LOADING)
        disposables.add(
            repository.login(email.value ?: "", password.value ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultData.postValue(NetworkState.LOADED)
                }, {
                    resultData.postValue(NetworkState.error(it.message))
                })
        )
    }

    fun forgotPw() {
        resultData.postValue(NetworkState.LOADING)
        disposables.add(
            repository.sendMailResetPassword(email.value ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultData.postValue(NetworkState.LOADED)
                }, {
                    resultData.postValue(NetworkState.error(it.message))
                })
        )
    }

    fun signUp(
        email: String,
        username: String,
        dayOfBirth: String,
        gender: Boolean,
        password: String
    ) {
        resultData.value = NetworkState.LOADING
        disposables.add(
            repository.register(email, username, dayOfBirth, gender, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    resultData.value = NetworkState.LOADED
                }, {
                    resultData.value = NetworkState.error(it.message)
                })
        )
    }

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun isValidate(email: String): Boolean = email.isNotEmpty()

    fun isValidateEmailPassword(email: String, password: String) =
        isValidate(email) && isValidPassword(password)

}

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = AuthViewModel(repository) as T

}