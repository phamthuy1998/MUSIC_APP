package thuy.ptithcm.spotifyclone.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_album.*
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.ActivityAlbumBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.song.SongFragment
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.addFragment
import thuy.ptithcm.spotifyclone.viewmodel.AlbumViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class AlbumFragment : Fragment() {

    private val albumViewModel: AlbumViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideAlbumViewModelFactory())
            .get(AlbumViewModel::class.java)
    }
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideMainViewModelFactory())
            .get(MainViewModel::class.java)
    }
    private lateinit var binding: ActivityAlbumBinding
    private val songAdapter by lazy {
        SongAdapter(
            mutableListOf(),
            this::songEvents
        )
    }

    private var albumID: String? = null
    private fun songEvents(songId: String?, type: EventTypeSong) {
        when (type) {
            EventTypeSong.ITEM_CLICK -> {
//                val intent = Intent(requireContext(), SongFragment().javaClass)
//                intent.putExtra("SongID", songId)
//                startActivity(intent)
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
        binding = ActivityAlbumBinding.inflate(inflater, container, false)
        binding.viewmodel = albumViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumID = arguments?.getString("albumID")
        if (albumID != null) {
            albumViewModel.getListAlbum(albumID!!)
            albumViewModel.getAlbumInfo(albumID!!)
        } else
            Toast.makeText(
                requireContext(),
                "Can't load info of this album!",
                Toast.LENGTH_LONG
            ).show()
        initViews()
        viewObserver()
        addEvents()
    }

    private fun addEvents() {
        swRefreshAlbum.setOnRefreshListener {
            refreshAlbum()
            swRefreshAlbum.isRefreshing = false
        }
        btnBackAlbum.setOnClickListener { requireActivity().onBackPressed() }
//        btnLikeAlbum.setOnClickListener { onLikeAlbumClick() }
    }

    private fun refreshAlbum() {
        if (albumID != null) albumViewModel.getListAlbum(albumID!!)
    }

    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
        rvSongAlbum.adapter = songAdapter
        rvSongAlbum.setHasFixedSize(true)
        rvSongAlbum.setItemViewCacheSize(20)
    }

    private fun viewObserver() {
        albumViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        albumViewModel.networkStateListSong.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
            progressAlbum.visibility = if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

        albumViewModel.album.observe(this, Observer {
            binding.album = it
        })

        albumViewModel.networkStateAlbum.observe(this, Observer {
            if (it?.status == Status.FAILED)
                Toast.makeText(requireContext(), "err: ${it.msg}", Toast.LENGTH_LONG).show()
        })

        albumViewModel.statusLikeAlbum.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> Toast
                    .makeText(
                        requireContext(),
                        "Failed to add to favorite album!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.SUCCESS -> Toast
                    .makeText(
                        requireContext(),
                        "Add to favorite album successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.RUNNING -> {
                }
            }
        })
        albumViewModel.statusUnLikeAlbum.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> Toast
                    .makeText(
                        requireContext(),
                        "Failed to remove favorite album!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.SUCCESS -> Toast
                    .makeText(
                        requireContext(),
                        "Remove favorite album successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.RUNNING -> {
                }
            }
        })

        albumViewModel.checkAlbumIsLike.observe(this, Observer {
            binding.btnLikeAlbum.isSelected = it ?: false
        })
    }
}
