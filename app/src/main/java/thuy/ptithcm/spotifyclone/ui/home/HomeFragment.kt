package thuy.ptithcm.spotifyclone.ui.home


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.SongType
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.album.AlbumFragment
import thuy.ptithcm.spotifyclone.ui.album.adapter.AlbumAdapter
import thuy.ptithcm.spotifyclone.ui.artist.ArtistInfoFragment
import thuy.ptithcm.spotifyclone.ui.artist.adapter.ArtistAdapter
import thuy.ptithcm.spotifyclone.ui.home.adapter.BannerAdapter
import thuy.ptithcm.spotifyclone.ui.playlist.PlaylistDetailFragment
import thuy.ptithcm.spotifyclone.ui.playlist.adapter.PlayListAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongFragment
import thuy.ptithcm.spotifyclone.ui.songtype.SongTypesFragment
import thuy.ptithcm.spotifyclone.ui.songtype.adapter.SongTypeAdapter
import thuy.ptithcm.spotifyclone.utils.addFragment
import thuy.ptithcm.spotifyclone.utils.showFragment
import thuy.ptithcm.spotifyclone.viewmodel.HomeViewModel
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel


class HomeFragment : Fragment() {

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance() = instance ?: HomeFragment()
    }

    private val albumAdapter by lazy {
        AlbumAdapter(this::itemAlbumClick)
    }
    private val artistAdapter by lazy {
        ArtistAdapter(this::itemArtistClick)
    }
    private val bannerAdapter by lazy {
        BannerAdapter(this::itemBannerClick)
    }
    private val playlistAdapter by lazy {
        PlayListAdapter(this::playlistItemClick)
    }
    private val songTypeAdapter by lazy {
        SongTypeAdapter(this::songTypeClick)
    }

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideHomeViewModelFactory())
            .get(HomeViewModel::class.java)
    }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders
            .of(requireActivity(), Injection.provideHomeViewModelFactory())
            .get(MainViewModel::class.java)
    }

    private fun itemBannerClick(songId: String?) {
        mainViewModel.songID.value = songId
        activity.addFragment(fragment = SongFragment(), tag = "NowPlaying")
    }

    private fun songTypeClick(songType: SongType?) {
        val songTypesFragment = SongTypesFragment()
        val arguments = Bundle()
        arguments.putParcelable("SongType", songType)
        songTypesFragment.arguments = arguments
        showFragment(R.id.container, songTypesFragment, "SongTypesFragment")
    }

    private fun playlistItemClick(id: String?) {
        val playlistFragment = PlaylistDetailFragment()
        val arguments = Bundle()
        arguments.putString("PlaylistID", id)
        playlistFragment.arguments = arguments
        showFragment(R.id.container, playlistFragment, "PlaylistFragment")
    }

    private fun itemAlbumClick(id: String?) {
        val albumFragment = AlbumFragment()
        val arguments = Bundle()
        arguments.putString("albumID", id)
        albumFragment.arguments = arguments
        showFragment(R.id.container, albumFragment, "AlbumFragment")
    }

    private fun itemArtistClick(id: String?) {
        val artistFragment = ArtistInfoFragment()
        val arguments = Bundle()
        arguments.putString("artistID", id)
        artistFragment.arguments = arguments
        showFragment(R.id.container, artistFragment, "ArtistInfoFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner()
        initArtist()
        initAlbum()
        initSongType()
        initPlaylist()
        viewModelBinding()
    }

    private fun initArtist() {
        rvArtist.adapter = artistAdapter
        rvSongType.setHasFixedSize(true)
        rvSongType.setItemViewCacheSize(20)
    }

    private fun initSongType() {
        rvSongType.adapter = songTypeAdapter
        rvSongType.setHasFixedSize(true)
        rvSongType.setItemViewCacheSize(20)
    }

    private fun initPlaylist() {
        rvPlayList.adapter = playlistAdapter
        rvPlayList.setHasFixedSize(true)
        rvPlayList.setItemViewCacheSize(20)
    }

    private fun initBanner() {
        viewPagerBanner.adapter = bannerAdapter
        indicatorBanner.setViewPager(viewPagerBanner)
        handler = Handler()
        var currentPage: Int
        runnable = Runnable {
            kotlin.run {
                if (viewPagerBanner != null) {
                    currentPage = viewPagerBanner.currentItem
                    currentPage++
                    if (currentPage >= bannerAdapter.count) currentPage = 0
                    viewPagerBanner.setCurrentItem(currentPage, true)
                    handler.postDelayed(runnable, 3000)
                }
            }
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun initAlbum() {
        rvAlbum.adapter = albumAdapter
        rvAlbum.setHasFixedSize(true)
        rvAlbum.setItemViewCacheSize(20)
    }

    private fun viewModelBinding() {
        // update image view
        homeViewModel.userInfo.observe(requireActivity(), Observer {
            Glide.with(this)
                .load(it?.profilePhoto)
                .error(R.drawable.ic_account)
                .into(ivAvt)
        })

        // Banner
        homeViewModel.listAdvertise.observe(requireActivity(), Observer {
            bannerAdapter.addDataAdvertise(it)
        })

        homeViewModel.networkStateAdv.observe(requireActivity(), Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
            progressHome.visibility = if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

        // Album
        homeViewModel.listAlbum.observe(requireActivity(), Observer {
            tvTitleAlbum.visibility = if (it != null && it.size != 0) View.VISIBLE else View.GONE
            albumAdapter.addDataAlbum(it)
        })

        homeViewModel.networkStateAlbum.observe(requireActivity(), Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireActivity(), "Playlist err! $it.msg", Toast.LENGTH_LONG).show()
        })

        // Playlist
        homeViewModel.listPlaylist.observe(requireActivity(), Observer {
            tvTitlePlaylist.visibility = if (it != null && it.size != 0) View.VISIBLE else View.GONE
            playlistAdapter.addDataPlaylist(it)
        })

        homeViewModel.networkStatePlaylist.observe(requireActivity(), Observer {
            //            if (it?.status == Status.FAILED)
//                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
        })

        //Songtype
        homeViewModel.listSongType.observe(requireActivity(), Observer {
            tvTitleSongType.visibility = if (it != null && it.size != 0) View.VISIBLE else View.GONE
            songTypeAdapter.addDataSongType(it)
        })

        // Artist
        homeViewModel.listArtist.observe(requireActivity(), Observer {
//            if (it != null && it.size != 0) {
//                titleArtist.visible()
//            } else {
//                titleArtist.gone()
//            }
            artistAdapter.addDataArtist(it)
        })

        homeViewModel.networkStateSongType.observe(requireActivity(), Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
        })
    }

}
