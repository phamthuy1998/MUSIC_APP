package thuy.ptithcm.spotifyclone.viewmodel

import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.*
import thuy.ptithcm.spotifyclone.repository.HomeRepository

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    private var requestAdvertise = MutableLiveData<ResultData<ArrayList<Advertise>>>()
    private var requestSongType = MutableLiveData<ResultData<ArrayList<SongType>>>()
    private var requestPlaylist = MutableLiveData<ResultData<ArrayList<Playlist>>>()
    private var requestAlbum = MutableLiveData<ResultData<ArrayList<Album>>>()
    private var requestUser = MutableLiveData<ResultData<User>>()


    init {
        getAdvertise()
        getSongType()
        getPlaylist()
        getUserInfo()
        getListAlbum()
    }

    fun refresh(){
        getAdvertise()
        getUserInfo()
        getSongType()
        getPlaylist()
        getListAlbum()
    }


    val userInfo: LiveData<User> =
        Transformations.switchMap(requestUser) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestUser) {
            it.networkState
        }

    private fun getUserInfo() {
        requestUser.value = repository.getUserInfo()
    }

    val listAdvertise: LiveData<java.util.ArrayList<Advertise>> =
        Transformations.switchMap(requestAdvertise) {
            it.data
        }

    val networkStateAdv: LiveData<NetworkState> = Transformations.switchMap(requestAdvertise) {
        it.networkState
    }

    val listSongType: LiveData<java.util.ArrayList<SongType>> =
        Transformations.switchMap(requestSongType) {
            it.data
        }

    val networkStateSongType: LiveData<NetworkState> = Transformations.switchMap(requestSongType) {
        it.networkState
    }

    val listPlaylist: LiveData<java.util.ArrayList<Playlist>> =
        Transformations.switchMap(requestPlaylist) {
            it.data
        }

    val networkStatePlaylist: LiveData<NetworkState> = Transformations.switchMap(requestPlaylist) {
        it.networkState
    }

    val listAlbum: LiveData<java.util.ArrayList<Album>> =
        Transformations.switchMap(requestAlbum) {
            it.data
        }

    val networkStateAlbum: LiveData<NetworkState> = Transformations.switchMap(requestAlbum) {
        it.networkState
    }

    private fun getAdvertise() {
        requestAdvertise.value = repository.getAdvertise()
    }

    private fun getSongType() {
        requestSongType.value = repository.getSongType()
    }

    private fun getPlaylist() {
        requestPlaylist.value = repository.getPlaylist()
    }
    private fun getListAlbum() {
        requestAlbum.value = repository.getListAlbum()
    }
}


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = HomeViewModel(repository) as T

}