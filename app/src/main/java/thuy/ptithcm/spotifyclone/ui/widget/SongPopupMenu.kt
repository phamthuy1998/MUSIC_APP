package thuy.ptithcm.spotifyclone.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.listener.PopupMenuListener

class SongPopupMenu constructor(context: Context, attrs: AttributeSet) :
    AppCompatImageView(context, attrs) {

    private var popupMenuListener: PopupMenuListener? = null
    private lateinit var song: Song

    init {
        setImageResource(R.drawable.ic_more)
        setOnClickListener {
            val popupMenu = PopupMenu(context, this)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.popup_song_play -> popupMenuListener?.play(song)
                    R.id.popup_song_goto_album -> popupMenuListener?.goToAlbum(song)
                    R.id.popup_song_goto_artist -> popupMenuListener?.goToArtist(song)
                    R.id.popup_song_play_next -> popupMenuListener?.playNext(song)
                    R.id.popup_song_addto_playlist -> popupMenuListener?.addToPlaylist(
                        context,
                        song
                    )
                }
                true
            }
            popupMenu.inflate(R.menu.menu_popup_song)
            popupMenu.show()
        }
    }

    fun setupMenu(listener: PopupMenuListener?, _song:Song) {
        this.popupMenuListener = listener
        this.song = _song
    }
}
