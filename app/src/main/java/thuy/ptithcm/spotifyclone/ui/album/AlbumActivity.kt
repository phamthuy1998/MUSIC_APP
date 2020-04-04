package thuy.ptithcm.spotifyclone.ui.album

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_album.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.ui.album.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.viewmodel.AlbumViewModel

class AlbumActivity : AppCompatActivity() {

    private val albumViewModel: AlbumViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideAlbumViewModelFactory())
            .get(AlbumViewModel::class.java)
    }
    private val songAdapter by lazy {
        SongAdapter(mutableListOf(),this::songEvents)
    }

    private var albumID: String? = null
    private fun songEvents(songId: String?, type: EventTypeSong) {
        when (type) {
            EventTypeSong.ITEM_CLICK -> {
                val intent = Intent(this, SongActivity().javaClass)
                intent.putExtra("SongID", songId)
                startActivity(intent)
            }
            EventTypeSong.SHOW_MORE -> {
                // Todo() -> show dialog
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        albumID = intent.getStringExtra("albumID")
        if (albumID != null) {
            albumViewModel.getListAlbum(albumID!!)
            albumViewModel.getAlbumInfo(albumID!!)
        } else
            Toast.makeText(this, "Can't load info of this album!", Toast.LENGTH_LONG).show()
        initViews()
        viewModelBinding()
        addEvents()
    }

    private fun addEvents() {
        swRefreshAlbum.setOnRefreshListener {
            refreshAlbum()
            swRefreshAlbum.isRefreshing = false
        }
        btnBackAlbum.setOnClickListener { finish() }
    }

    private fun updateUI(album: Album?) {
        collapsingTBAlbum.title = album?.albumName
        Glide.with(this)
            .load(album?.imageURL)
            .error(R.drawable.gradient_item_banner)
            .into(ivAlbum)
        Glide.with(this)
            .load(album?.imageURL)
            .error(R.drawable.gradient_item_banner)
            .into(ivAlbumCircle)
    }

    private fun refreshAlbum() {
        if (albumID != null) albumViewModel.getListAlbum(albumID!!)
    }

    private fun initViews() {
        rvSongAlbum.adapter = songAdapter
        rvSongAlbum.setHasFixedSize(true)
        rvSongAlbum.setItemViewCacheSize(20)
        collapsingTBAlbum.setExpandedTitleColor(getColor(R.color.colorText))
        collapsingTBAlbum.setCollapsedTitleTextColor(getColor(R.color.colorWhite))
    }

    private fun viewModelBinding() {
        albumViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        albumViewModel.networkStateListSong.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
            progressAlbum.visibility = if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

        albumViewModel.album.observe(this, Observer {
            updateUI(it)
        })

        albumViewModel.networkStateAlbum.observe(this, Observer {
            if (it?.status == Status.FAILED)
                Toast.makeText(this, "err: ${it.msg}", Toast.LENGTH_LONG).show()
        })
    }
}
