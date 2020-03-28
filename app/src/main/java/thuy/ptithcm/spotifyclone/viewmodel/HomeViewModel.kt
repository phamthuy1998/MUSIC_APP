package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import thuy.ptithcm.spotifyclone.data.Advertise
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.repository.HomeRepository

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    var requestAdvertise = MutableLiveData<ResultData<ArrayList<Advertise>>>()

    val listAdvertise = Transformations.switchMap(requestAdvertise) {
        it.data
    }

    val networkState = Transformations.switchMap(requestAdvertise) {
        it.networkState
    }

    fun getAdvertise() {
        requestAdvertise.value = repository.getAdvertise()
    }
}


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = HomeViewModel(repository) as T

}