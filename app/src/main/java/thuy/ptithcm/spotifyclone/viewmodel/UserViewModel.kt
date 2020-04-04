package thuy.ptithcm.spotifyclone.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.*
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.repository.UserRepository
import thuy.ptithcm.spotifyclone.ui.album.AlbumListActivity
import thuy.ptithcm.spotifyclone.ui.artist.ArtistActivity
import thuy.ptithcm.spotifyclone.ui.download.DownloadActivity
import thuy.ptithcm.spotifyclone.ui.favorite.FavoriteSongActivity
import thuy.ptithcm.spotifyclone.ui.playlist.PlaylistActivity
import thuy.ptithcm.spotifyclone.ui.records.RecordActivity

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private var requestUser = MutableLiveData<ResultData<User>>()
    private var requestSignOut = MutableLiveData<ResultData<Boolean>>()

    init {
        getUserInfo()
    }

    fun refresh() = getUserInfo()

    val userInfo: LiveData<User> =
        Transformations.switchMap(requestUser) {
            it.data
        }

    val networkStateListSong: LiveData<NetworkState> =
        Transformations.switchMap(requestUser) {
            it.networkState
        }

    val networkSignOut: LiveData<NetworkState> =
        Transformations.switchMap(requestSignOut) {
            it.networkState
        }

    private fun getUserInfo() {
        requestUser.value = repository.getUserInfo()
    }

    fun signOut() {
        requestSignOut.value = repository.signOut()
    }

    fun showFavoriteSongs(view: View) {
        val intent = Intent(view.context, FavoriteSongActivity().javaClass)
        view.context.startActivity(intent)
    }

    fun showAlbumLike(view: View) {
        val intent = Intent(view.context, AlbumListActivity().javaClass)
        view.context.startActivity(intent)
    }

    fun showFragmentProfile(view: View) {
        val intent = Intent(view.context, AlbumListActivity().javaClass)
        view.context.startActivity(intent)
    }

    fun showArtistFollowing(view: View) {
        val intent = Intent(view.context, ArtistActivity().javaClass)
        view.context.startActivity(intent)
    }

    fun showPlaylist(view: View) {
        val intent = Intent(view.context, PlaylistActivity().javaClass)
        view.context.startActivity(intent)
    }

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
class UserViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = UserViewModel(repository) as T

}