package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.repository.AlbumRepository

class AlbumViewModel(
    private val repository: AlbumRepository
) : ViewModel() {
    private var requestSongOfAlbum = MutableLiveData<ResultData<ArrayList<Song>>>()
    private var requestAlbum = MutableLiveData<ResultData<Album>>()

    val listSong: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.networkState
        }


    val album: LiveData<Album> =
        Transformations.switchMap(requestAlbum) {
            it.data
        }

    val networkStateAlbum: LiveData<NetworkState> =
        Transformations.switchMap(requestAlbum) {
            it.networkState
        }

    fun getListAlbum(albumID: String) {
        requestSongOfAlbum.value = repository.getListSongOfAlbum(albumID)
    }

    fun getAlbumInfo(albumID: String) {
        requestAlbum.value = repository.getAlbumInfoByID(albumID)
    }
}


@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory(
    private val repository: AlbumRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = AlbumViewModel(repository) as T

}