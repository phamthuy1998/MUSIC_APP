package thuy.ptithcm.spotifyclone.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_favorite_song.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.ui.album.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.utils.gone
import thuy.ptithcm.spotifyclone.utils.hideKeyboard
import thuy.ptithcm.spotifyclone.utils.showKeyboard
import thuy.ptithcm.spotifyclone.utils.visible
import thuy.ptithcm.spotifyclone.viewmodel.FavoriteSongViewModel

class FavoriteSongActivity : AppCompatActivity(), TextWatcher {

    private val favoriteSongViewModel: FavoriteSongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideFavoriteSongViewModelFactory())
            .get(FavoriteSongViewModel::class.java)
    }

    private val songAdapter by lazy {
        SongAdapter(mutableListOf(), this::songEvents)
    }

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
        setContentView(R.layout.activity_favorite_song)
        hideKeyboard()
        initViews()
        addEvents()
        bindViewModel()
    }

    private fun addEvents() {
        btnBackFavoriteSong.setOnClickListener { onButtonBackClick() }
        btnSearchFavorite.setOnClickListener { showSearch() }
        btnPlayAllFavoriteSong.setOnClickListener { /* Todo() --> play all music on service*/ }
        swRefreshFavorite.setOnRefreshListener {
            favoriteSongViewModel.getListFavoriteSong(); swRefreshFavorite.isRefreshing = false
        }
        edtSearchFavorite.addTextChangedListener(this)
    }

    private fun showSearch() {
        showKeyboard()
        layoutSearchFavorite.gone()
        tvTbFavorite.gone()
    }

    override fun onBackPressed() {
        onButtonBackClick()
    }

    private fun onButtonBackClick() {
        hideKeyboard()
        if (tvTbFavorite.visibility == View.VISIBLE)
            finish()
        else {
            tvTbFavorite.visible()
            layoutSearchFavorite.visible()
        }
    }

    private fun bindViewModel() {
        favoriteSongViewModel.listSong.observe(this, Observer {
            hideKeyboard()
            songAdapter.addDataListSong(it)
        })

        favoriteSongViewModel.networkStateListSong.observe(this, Observer {
            progressFavoriteSong.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
            if (it.status == Status.FAILED) {
                layoutSearchFavorite.gone()
                llListFavoriteSongEmpty.visible()
            } else {
                layoutSearchFavorite.visible()
                llListFavoriteSongEmpty.gone()
            }
        })
    }

    private fun initViews() {
        rvFavoriteSong.adapter = songAdapter
        rvFavoriteSong.setHasFixedSize(true)
        rvFavoriteSong.setItemViewCacheSize(20)
    }


    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        songAdapter.search(s.toString(),{
            llSearchNotFound.visible()
            songAdapter.removeAllData()
        },{
            songAdapter.addDataSearch(it)
            llSearchNotFound.gone()
        })
    }

}
