package thuy.ptithcm.spotifyclone.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.FragmentArtistBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.song.SongFragment
import thuy.ptithcm.spotifyclone.ui.song.adapter.SongAdapter
import thuy.ptithcm.spotifyclone.utils.addFragment
import thuy.ptithcm.spotifyclone.viewmodel.ArtistViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class ArtistInfoFragment : Fragment() {


    private val artistViewModel: ArtistViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideArtistViewModelFactory())
            .get(ArtistViewModel::class.java)
    }
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideMainViewModelFactory())
            .get(MainViewModel::class.java)
    }
    private var artistID: String? = null
    private lateinit var binding: FragmentArtistBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistBinding.inflate(inflater, container, false)
        binding.viewmodel = artistViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistID = arguments?.getString("artistID")
        if (artistID != null) {
            artistViewModel.getListSongOfArtist(artistID!!)
            artistViewModel.getArtistInfo(artistID!!)
            bindViewModel()
            initViews()
            addEvents()
        } else
            Toast.makeText(
                requireContext(),
                "Can't load info of artist!",
                Toast.LENGTH_LONG
            ).show()
    }

    private fun addEvents() {
        binding.swListSongArtist.setOnRefreshListener {
            artistID?.let { artistViewModel.getListSongOfArtist(it) }
            binding.swListSongArtist.isRefreshing = false
        }
    }

    private fun initViews() {
        songAdapter.popupMenuListener = mainViewModel.popupMenuListener
        binding.rvSongArtist.adapter = songAdapter
        binding.rvSongArtist.setHasFixedSize(true)
        binding.rvSongArtist.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        artistViewModel.artistInfo.observe(this, Observer { artistInfo ->
            artistViewModel.followCounter = artistInfo?.followCounter
            binding.artist = artistInfo
        })

        artistViewModel.listSong.observe(this, Observer {
            songAdapter.addDataListSong(it)
        })

        artistViewModel.networkStateListSong.observe(this, Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
            binding.progressArtist.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

        artistViewModel.networkStateArtist.observe(this, Observer {
            if (it?.status == Status.FAILED)
                Toast.makeText(requireContext(), "err: ${it.msg}", Toast.LENGTH_LONG).show()
        })

        artistViewModel.statusFollowArtist.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> Toast
                    .makeText(
                        requireContext(),
                        "Can't follow artist ${it.msg}!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Following artist!", Toast.LENGTH_LONG).show()
                    artistViewModel.updateCounter(
                        artistID.toString(),
                        artistViewModel.followCounter?.plus(1) ?: 0
                    )
                }
                Status.RUNNING -> {
                }
            }
        })
        artistViewModel.statusUnFollowArtist.observe(this, Observer {
            when (it?.status) {
                Status.FAILED -> Toast
                    .makeText(
                        requireContext(),
                        "Can't unfollow artist ${it.msg}!",
                        Toast.LENGTH_LONG
                    ).show()
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Unfollow artist!", Toast.LENGTH_LONG).show()
                    artistViewModel.updateCounter(
                        artistID.toString(),
                        artistViewModel.followCounter?.minus(1) ?: 0
                    )
                }
                Status.RUNNING -> {
                }
            }
        })

        artistViewModel.checkFollowArtist.observe(this, Observer {
            binding.btnFollowArtist.text = if (it == true) "Following" else "Follows"
            binding.btnFollowArtist.isSelected = it ?: false
        })
    }

    private fun updateUI() {

    }
}
