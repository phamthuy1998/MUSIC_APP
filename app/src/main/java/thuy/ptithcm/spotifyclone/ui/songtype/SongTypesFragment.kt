package thuy.ptithcm.spotifyclone.ui.songtype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_song_types.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.SongType
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.song.SongFragment
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.addFragment
import thuy.ptithcm.spotifyclone.utils.gone
import thuy.ptithcm.spotifyclone.utils.visible
import thuy.ptithcm.spotifyclone.viewmodel.KindOfSongViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class SongTypesFragment : Fragment() {

    private val songTypeViewModel: KindOfSongViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideKindOfSongViewModelFactory())
            .get(KindOfSongViewModel::class.java)
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
    private var songType: SongType? = null

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_types, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songType = arguments?.getParcelable("SongType")
        if (songType != null) {
            songType?.id?.let { songTypeViewModel.getListSongInType(it) }
            initViews()
            viewModelBinding()
            addEvents()
        } else {
            Toast.makeText(requireContext(), "Can't load info of this album!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
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
        btnBackKindOfSong.setOnClickListener { requireActivity().onBackPressed() }
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
                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                llListSongEmpty.visible()
            } else llListSongEmpty.gone()
            progressSongType.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
    }
}
