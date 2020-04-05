package thuy.ptithcm.spotifyclone.ui.history

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_history.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.album.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.utils.gone
import thuy.ptithcm.spotifyclone.utils.hideKeyboard
import thuy.ptithcm.spotifyclone.utils.showKeyboard
import thuy.ptithcm.spotifyclone.utils.visible
import thuy.ptithcm.spotifyclone.viewmodel.HistorySongViewModel

class HistoryActivity : AppCompatActivity(), TextWatcher {

    private val historyViewModel: HistorySongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideHistorySongViewModelFactory())
            .get(HistorySongViewModel::class.java)
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
        setContentView(R.layout.activity_history)
        hideKeyboard()
        initViews()
        addEvents()
        bindViewModel()
    }

    private fun initViews() {
        rvAllHistorySong.adapter = songAdapter
        rvAllHistorySong.setHasFixedSize(true)
        rvAllHistorySong.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        historyViewModel.listSong.observe(this, Observer {
            hideKeyboard()
            songAdapter.addDataListSong(it)
        })

        historyViewModel.networkStateListSong.observe(this, Observer {
            progressHistory.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
    }

    private fun addEvents() {
        btnBackHistory.setOnClickListener { onButtonBackClick() }
        btnSearchHistory.setOnClickListener { showSearch() }
        btnPlayAllHistorySong.setOnClickListener { /* Todo() --> play all music on service*/ }
        refreshHistorySong.setOnRefreshListener {
            historyViewModel.getHistorySong(); refreshHistorySong.isRefreshing = false
        }
        edtSearchHistory.addTextChangedListener(this)
    }

    private fun showSearch() {
        showKeyboard()
        edtSearchHistory.visible()
        layoutSearchHistory.gone()
        tvTBHistory.gone()
    }

    override fun onBackPressed() {
        onButtonBackClick()
    }

    private fun onButtonBackClick() {
        if (tvTBHistory.visibility == View.VISIBLE)
            finish()
        else {
            tvTBHistory.visible()
            edtSearchHistory.gone()
            layoutSearchHistory.visible()
            hideKeyboard()
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        songAdapter.search(s.toString(), {
            llSearchNotFoundHistory.visible()
            songAdapter.removeAllData()
        }, {
            songAdapter.addDataSearch(it)
            llSearchNotFoundHistory.gone()
        })
    }
}
