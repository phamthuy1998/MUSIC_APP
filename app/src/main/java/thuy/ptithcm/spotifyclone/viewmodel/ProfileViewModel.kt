package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.repository.LibraryRepository

class ProfileViewModel(
    private val repository: LibraryRepository
) : ViewModel() {
    private var requestUser = MutableLiveData<ResultData<User>>()
    private var requestSignOut = MutableLiveData<ResultData<Boolean>>()

    init {
        getUserInfo()
    }

    val userInfo: LiveData<User> =
        Transformations.switchMap(requestUser) {
            it.data
        }

    val networkStateUserInfo: LiveData<NetworkState> =
        Transformations.switchMap(requestUser) {
            it.networkState
        }

    val networkSignOut: LiveData<NetworkState> =
        Transformations.switchMap(requestSignOut) {
            it.networkState
        }

    fun getUserInfo() {
        requestUser.value = repository.getUserInfo()
    }

    fun signOut() {
        requestSignOut.value = repository.signOut()
    }
}


@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val repository: LibraryRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = ProfileViewModel(repository) as T

}