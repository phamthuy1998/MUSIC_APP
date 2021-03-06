package thuy.ptithcm.spotifyclone.viewmodel

import android.view.View
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
    var statusLikeAlbum = MutableLiveData<NetworkState>()
    var statusUnLikeAlbum = MutableLiveData<NetworkState>()
    var checkAlbumIsLike = MutableLiveData<Boolean>()
    var albumID = MutableLiveData<String>()

    val listSong: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestSongOfAlbum) {
            it.networkState
        }


    var album: LiveData<Album> =
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

    fun getAlbumInfo(_albumID: String) {
        requestAlbum.value = repository.getAlbumInfoByID(_albumID)
        this.albumID.value = _albumID
        checkAlbumIsLike= repository.checkLikeAlbum(_albumID)
    }

    fun onLikeAlbumClick(view: View) {
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            if (album.value != null)
                statusLikeAlbum = repository.addAlbumFavorite(album.value!!)
        } else {
            if (albumID.value != null)
                statusUnLikeAlbum = repository.removeAlbumFavorite(albumID.value!!)
        }
    }

    fun onPlayAllAlbumClick(view: View) {
        view.isSelected = !view.isSelected
    }

    fun onShareAlbumClick(view: View) {

    }
}


@Suppress("UNCHECKED_CAST")
class AlbumViewModelFactory(
    private val repository: AlbumRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = AlbumViewModel(repository) as T

}