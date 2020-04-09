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
import kotlinx.android.synthetic.main.fragment_favorite_songs.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.*
import thuy.ptithcm.spotifyclone.viewmodel.FavoriteSongViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel


class FavoriteSongsFragment : Fragment(), TextWatcher {

    private val favoriteSongViewModel: FavoriteSongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideFavoriteSongViewModelFactory())
            .get(FavoriteSongViewModel::class.java)
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
                activity.addFragment(fragment = SongFragment(), tag = "NowPlaying")
            }
            EventTypeSong.SHOW_MORE -> {
                // Todo() -> show dialog
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onListFavoriteChanged(checkChanged: Boolean) {
        if (checkChanged)
            favoriteSongViewModel.getListFavoriteSong()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().hideKeyboard()
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
        requireActivity().showKeyboard()
        layoutSearchFavorite.gone()
        tvTbFavorite.gone()
        edtSearchFavorite.requestFocus()
    }

    private fun hideSearch() {
        tvTbFavorite.visible()
        edtSearchFavorite.gone()
        layoutSearchFavorite.visible()
        activity?.hideKeyboard()
    }
    private fun hideSearchView() {
        tvTbFavorite.visible()
        edtSearchFavorite.gone()
        edtSearchFavorite.setText("")
        layoutSearchFavorite.gone()
        activity?.hideKeyboard()
    }

    private fun onButtonBackClick() {
        requireActivity(). hideKeyboard()
        if (tvTbFavorite.visibility == View.VISIBLE)
            requireActivity().onBackPressed()
        else {
            tvTbFavorite.visible()
            layoutSearchFavorite.visible()
        }
    }

    private fun bindViewModel() {
        favoriteSongViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        favoriteSongViewModel.networkStateListSong.observe(this, Observer {
            requireActivity().hideKeyboard()
            when (it.status) {
                Status.RUNNING -> {
                    progressFavoriteSong.visible()
                }
                Status.SUCCESS -> {
                    llSearchNotFound.gone()
                    progressFavoriteSong.gone()
                    llListFavoriteSongEmpty.gone()
                    hideSearch()
                }
                Status.FAILED -> {
                    songAdapter.removeAllData()
                    llSearchNotFound.gone()
                    progressFavoriteSong.gone()
                    hideSearchView()
                    llListFavoriteSongEmpty.visible()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
        rvFavoriteSong.adapter = songAdapter
        rvFavoriteSong.setHasFixedSize(true)
        rvFavoriteSong.setItemViewCacheSize(20)
    }


    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        songAdapter.search(s.toString(), {
            llSearchNotFound.visible()
            songAdapter.removeAllData()
        }, {
            songAdapter.addDataSearch(it)
            llSearchNotFound.gone()
        })
    }

}
