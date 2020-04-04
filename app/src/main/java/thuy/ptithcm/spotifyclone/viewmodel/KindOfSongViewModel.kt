package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.repository.KindOfSongRepository

class KindOfSongViewModel(
    private val repository: KindOfSongRepository
) : ViewModel() {
    private var requestSong= MutableLiveData<ResultData<ArrayList<Song>>>()

    val listSong: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestSong) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestSong) {
            it.networkState
        }


    fun getListSongInType(albumID: String) {
        requestSong.value = repository.getListSongInKind(albumID)
    }

}


@Suppress("UNCHECKED_CAST")
class KindOfSongViewModelFactory(
    private val repository: KindOfSongRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = KindOfSongViewModel(repository) as T

}