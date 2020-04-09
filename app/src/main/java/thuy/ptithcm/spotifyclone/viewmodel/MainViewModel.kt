package thuy.ptithcm.spotifyclone.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.listener.PopupMenuListener
import thuy.ptithcm.spotifyclone.repository.AlbumRepository
import thuy.ptithcm.spotifyclone.utils.SONG

class MainViewModel(val repository: AlbumRepository) : ViewModel() {


    private val _currentData = MutableLiveData<Song>()
     val songID = MutableLiveData<String>().apply { value = null }
    val currentSong: LiveData<Song> = _currentData

    val mediaButtonPlayRes = MutableLiveData<Int>().apply {
        postValue(R.drawable.ic_btn_play)
    }

    val mediaButtonLikeRes = MutableLiveData<Int>().apply {
        postValue(R.drawable.ic_like_album)
    }

    fun playMediaId(mediaId: String) {

    }

    val popupMenuListener = object : PopupMenuListener {

        override fun play(song: Song) {
//            playMedia(song, null)
        }

        override fun goToAlbum(song: Song) {
//            browseToItem(albumRepository.getAlbum(song.albumId))
        }

        override fun goToArtist(song: Song) {
//            browseToItem(artistRepository.getArtist(song.artistId))
        }

        override fun addToPlaylist(context: Context, song: Song) {
//            AddToPlaylistDialog.show(context as AppCompatActivity, song)
        }

        override fun playNext(song: Song) {
//            mediaSessionConnection.transportControls.sendCustomAction(ACTION_PLAY_NEXT,
            Bundle().apply { putString(SONG, song.id) }
        }

    }
}


@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: AlbumRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = MainViewModel(repository) as T

}