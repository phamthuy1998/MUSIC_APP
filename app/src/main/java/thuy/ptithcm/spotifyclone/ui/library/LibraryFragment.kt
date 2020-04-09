package thuy.ptithcm.spotifyclone.ui.library


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.FragmentLibraryBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.album.FavoriteAlbumFragment
import thuy.ptithcm.spotifyclone.ui.artist.ArtistListFragment
import thuy.ptithcm.spotifyclone.ui.song.FavoriteSongsFragment
import thuy.ptithcm.spotifyclone.ui.song.HistorySongFragment
import thuy.ptithcm.spotifyclone.ui.song.SongFragment
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.*
import thuy.ptithcm.spotifyclone.viewmodel.LibraryViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class LibraryFragment : Fragment() {

    companion object {
        private var instance: LibraryFragment? = null

        fun getInstance() = instance
            ?: LibraryFragment()
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideMainViewModelFactory())
            .get(MainViewModel::class.java)
    }

    private lateinit var binding: FragmentLibraryBinding
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

    private val libraryViewModel: LibraryViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideLibraryViewModelFactory())
            .get(LibraryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        binding.viewmodel = libraryViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initViews()
        addEvents()
    }

    private fun addEvents() {
        binding.btnShowListArtist.setOnClickListener {
            showFragment(R.id.container, ArtistListFragment(), "ArtistListFragment")
        }
        binding.btnAlbum.setOnClickListener {
            showFragment(R.id.container, FavoriteAlbumFragment(), "FavoriteAlbumFragment")
        }
        binding.btnFavoriteSong.setOnClickListener {
            showFragment(R.id.container, FavoriteSongsFragment(), "FavoriteSongsFragment")
        }
        binding.btnShowAllHistory.setOnClickListener {
            showFragment(R.id.container, HistorySongFragment(), "HistorySongFragment")
        }

    }

    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
        binding.rvHistorySong.adapter = songAdapter
        binding.rvHistorySong.setHasFixedSize(true)
        binding.rvHistorySong.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        libraryViewModel.getListSongHistory()
        libraryViewModel.userInfo.observe(requireActivity(), Observer {
            binding.user = it
        })
        libraryViewModel.listSongHistory.observe(this, Observer {
            songAdapter.addDataListSong(it)
            updateUI(it)
        })
        libraryViewModel.networkStateListSong.observe(this, Observer {
            binding.progressHistorySong.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
            if (it?.status == Status.FAILED) hideHistory()
        })

    }

    private fun hideHistory() {
        binding.tvListeningHistory.gone()
        binding.btnShowAllHistory.gone()
    }

    private fun updateUI(listSong: ArrayList<Song>?) {
        if (listSong?.size == 0) {
            binding.tvListeningHistory.gone()
            binding.btnShowAllHistory.gone()
        } else {
            binding.tvListeningHistory.visible()
        }
        binding.btnShowAllHistory.visibility =
            if (listSong?.size!! == ITEM_COUNT_HISTORY) View.VISIBLE else View.GONE
    }

}
