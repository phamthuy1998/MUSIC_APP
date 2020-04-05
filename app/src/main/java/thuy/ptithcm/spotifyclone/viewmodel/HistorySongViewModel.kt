package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.repository.HistorySongRepository

class HistorySongViewModel(
    private val repository: HistorySongRepository
) : ViewModel() {
    private var requestHistorySong = MutableLiveData<ResultData<ArrayList<Song>>>()

    init {
        getHistorySong()
    }

    val listSong: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestHistorySong) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestHistorySong) {
            it.networkState
        }

    fun getHistorySong() {
        requestHistorySong.value = repository.getHistoryListSong()
    }
}


@Suppress("UNCHECKED_CAST")
class HistorySongViewModelFactory(
    private val repository: HistorySongRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        HistorySongViewModel(repository) as T

}