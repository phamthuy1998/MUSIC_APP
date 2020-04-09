package thuy.ptithcm.spotifyclone.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.repository.LibraryRepository
import thuy.ptithcm.spotifyclone.ui.download.DownloadActivity
import thuy.ptithcm.spotifyclone.ui.records.RecordActivity

class LibraryViewModel(
    private val repository: LibraryRepository
) : ViewModel() {
    private var requestUser = MutableLiveData<ResultData<User>>()
    private var requestSongHistory = MutableLiveData<ResultData<ArrayList<Song>>>()

    val listSongHistory: LiveData<ArrayList<Song>> =
        Transformations.switchMap(requestSongHistory) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestSongHistory) {
            it.networkState
        }

    fun getListSongHistory() {
        requestSongHistory.value = repository.getSongHistoryList()
    }

    init {
        getUserInfo()
    }

    val userInfo: LiveData<User> =
        Transformations.switchMap(requestUser) {
            it.data
        }

    private fun getUserInfo() {
        requestUser.value = repository.getUserInfo()
    }

//    fun showPlaylist(view: View) {
//        val intent = Intent(view.context, PlaylistDetailFragment().javaClass)
//        view.context.startActivity(intent)
//    }

    fun showRecords(view: View) {
        val intent = Intent(view.context, RecordActivity().javaClass)
        view.context.startActivity(intent)
    }

    fun showDownloads(view: View) {
        val intent = Intent(view.context, DownloadActivity().javaClass)
        view.context.startActivity(intent)
    }

}


@Suppress("UNCHECKED_CAST")
class LibraryViewModelFactory(
    private val repository: LibraryRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = LibraryViewModel(repository) as T

}