package thuy.ptithcm.spotifyclone.ui.song

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_history.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.*
import thuy.ptithcm.spotifyclone.viewmodel.HistorySongViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class HistorySongFragment : Fragment(), TextWatcher {

    private val historyViewModel: HistorySongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideHistorySongViewModelFactory())
            .get(HistorySongViewModel::class.java)
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideMainViewModelFactory())
            .get(MainViewModel::class.java)
    }
    private val songAdapter by lazy {
        SongAdapter(
            mutableListOf(),
            this::songEvents
        )
    }

    private fun songEvents(songId: String?, type: EventTypeSong) {
        when (type) {
            EventTypeSong.ITEM_CLICK -> {
                mainViewModel.songID.value = songId
                requireActivity().addFragment(fragment = SongFragment(), tag = "NowPlaying")
            }
            EventTypeSong.SHOW_MORE -> {
                // Todo() -> show dialog
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideKeyboard()
        initViews()
        addEvents()
        bindViewModel()
    }
    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
        rvAllHistorySong.adapter = songAdapter
        rvAllHistorySong.setHasFixedSize(true)
        rvAllHistorySong.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        historyViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        historyViewModel.networkStateListSong.observe(this, Observer {
            progressHistory.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE

            activity?.hideKeyboard()
            when (it.status) {
                Status.RUNNING -> {
                    progressHistory.visible()
                }
                Status.SUCCESS -> {
                    llSearchNotFoundHistory.gone()
                    progressHistory.gone()
                    hideSearch()
                }
                Status.FAILED -> {
                    songAdapter.removeAllData()
                    llSearchNotFoundHistory.gone()
                    progressHistory.gone()
                    hideSearchView()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
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
        requireActivity().showKeyboard()
        edtSearchHistory.visible()
        edtSearchHistory.requestFocus()
        layoutSearchHistory.gone()
        tvTBHistory.gone()
    }

    private fun hideSearchView() {
        tvTBHistory.visible()
        edtSearchHistory.gone()
        layoutSearchHistory.gone()
        activity?.hideKeyboard()
    }

    private fun hideSearch() {
        tvTBHistory.visible()
        edtSearchHistory.setText("")
        edtSearchHistory.gone()
        layoutSearchHistory.visible()
        requireActivity().hideKeyboard()
    }

    private fun onButtonBackClick() {
        if (tvTBHistory.visibility == View.VISIBLE)
            requireActivity().onBackPressed()
        else hideSearch()
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
