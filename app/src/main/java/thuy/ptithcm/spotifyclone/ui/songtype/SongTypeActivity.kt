package thuy.ptithcm.spotifyclone.ui.songtype

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_song_type.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.SongType
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.ui.album.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.utils.gone
import thuy.ptithcm.spotifyclone.utils.visible
import thuy.ptithcm.spotifyclone.viewmodel.KindOfSongViewModel

class SongTypeActivity : AppCompatActivity() {

    private val songTypeViewModel: KindOfSongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideKindOfSongViewModelFactory())
            .get(KindOfSongViewModel::class.java)
    }
    private val songAdapter by lazy {
        SongAdapter(mutableListOf(),this::songEvents)
    }

    private var isLoadFail = false
    private var songType: SongType? = null

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
        setContentView(R.layout.activity_song_type)
        songType = intent.getParcelableExtra("SongType")
        if (songType != null) {
            songType?.id?.let { songTypeViewModel.getListSongInType(it) }
        } else {
            isLoadFail = true
            Toast.makeText(this, "Can't load info of this album!", Toast.LENGTH_LONG).show()
        }
        initViews()
        viewModelBinding()
        addEvents()
    }

    private fun initViews() {
        if (isLoadFail) progressSongType.gone()
        rvKindOfSong.adapter = songAdapter
        rvKindOfSong.setItemViewCacheSize(20)
        rvKindOfSong.setHasFixedSize(true)
        tvTypeName.text = songType?.typeName
    }

    private fun addEvents() {
        refreshLayoutSongType.setOnRefreshListener {
            refreshAlbum()
            refreshLayoutSongType.isRefreshing = false
        }
        btnBackKindOfSong.setOnClickListener { finish() }
    }

    private fun refreshAlbum() {
        if (songType != null) songType?.id?.let { songTypeViewModel.getListSongInType(it) }
    }


    private fun viewModelBinding() {
        songTypeViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        songTypeViewModel.networkStateListSong.observe(this, Observer {
            if (it.status == Status.FAILED) {
                Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                llListSongEmpty.visible()
            } else llListSongEmpty.gone()
            progressSongType.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
    }
}
