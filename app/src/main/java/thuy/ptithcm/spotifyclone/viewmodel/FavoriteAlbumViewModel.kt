package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.repository.FavoriteAlbumRepository

class FavoriteAlbumViewModel(
    private val repository: FavoriteAlbumRepository
) : ViewModel() {
    private var requestFavoriteAlbum = MutableLiveData<ResultData<ArrayList<Album>>>()
    private var filterType = MutableLiveData<Int>().apply { value = 0}

    fun setFilterType(type: Int) {
        filterType.value= type
    }

    fun getFilterType(): Int? = filterType.value

    init {
        getListFavoriteAlbum()
    }

    var listFavoriteAlbum: LiveData<ArrayList<Album>> =
        Transformations.switchMap(requestFavoriteAlbum) {
            it.data
        }

    val networkStateFavoriteAlbum: LiveData<NetworkState> =
        Transformations.switchMap(requestFavoriteAlbum) {
            it.networkState
        }

    fun getListFavoriteAlbum() {
        requestFavoriteAlbum.value = repository.getListFavoriteAlbum()
    }


}


@Suppress("UNCHECKED_CAST")
class FavoriteAlbumViewModelFactory(
    private val repository: FavoriteAlbumRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        FavoriteAlbumViewModel(repository) as T

}