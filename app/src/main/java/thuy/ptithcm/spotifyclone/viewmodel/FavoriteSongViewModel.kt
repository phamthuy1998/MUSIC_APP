package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.repository.FavoriteSongRepository

class FavoriteSongViewModel(
    private val repository: FavoriteSongRepository
) : ViewModel() {
    private var requestSongOfAlbum = MutableLiveData<ResultData<ArrayList<Song>>>()

    init {
        getListFavoriteSong()
    }

    val listSong: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.networkState
        }

    fun getListFavoriteSong() {
        requestSongOfAlbum.value = repository.getListFavoriteSong()
    }
}


@Suppress("UNCHECKED_CAST")
class FavoriteSongViewModelFactory(
    private val repository: FavoriteSongRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        FavoriteSongViewModel(repository) as T

}