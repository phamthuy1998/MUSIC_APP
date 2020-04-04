package thuy.ptithcm.spotifyclone.ui.home


import android.content.Intent
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
import thuy.ptithcm.spotifyclone.ui.album.AlbumActivity
import thuy.ptithcm.spotifyclone.ui.home.adapter.AlbumAdapter
import thuy.ptithcm.spotifyclone.ui.home.adapter.BannerAdapter
import thuy.ptithcm.spotifyclone.ui.home.adapter.PlayListAdapter
import thuy.ptithcm.spotifyclone.ui.home.adapter.SongTypeAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.ui.songtype.SongTypeActivity
import thuy.ptithcm.spotifyclone.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance() = instance ?: HomeFragment()
    }

    private val albumAdapter by lazy {
        AlbumAdapter(this::itemAlbumClick)
    }
    private val bannerAdapter by lazy {
        BannerAdapter(this::itemBannerClick)
    }
    private val playlistAdapter by lazy {
        PlayListAdapter(this::songTypeClick)
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

    private fun itemBannerClick(songId: String?) {
        val intent = Intent(requireContext(), SongActivity().javaClass)
        intent.putExtra("SongID", songId)
        requireContext().startActivity(intent)
    }

    private fun songTypeClick(songType: SongType?) {
        val intent = Intent(requireContext(), SongTypeActivity().javaClass)
        intent.putExtra("SongType", songType)
        requireContext().startActivity(intent)
    }

    private fun songTypeClick(id: String?) {
        val intent = Intent(requireContext(), SongTypeActivity().javaClass)
        intent.putExtra("SongTypeID", id)
        requireContext().startActivity(intent)
    }

    private fun itemAlbumClick(id: String?) {
        val intent = Intent(requireContext(), AlbumActivity().javaClass)
        intent.putExtra("albumID", id)
        requireContext().startActivity(intent)
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
        initAlbum()
        initSongType()
        initPlaylist()
        viewModelBinding()
    }

    private fun initSongType() {
        rvSongType.adapter = songTypeAdapter
    }

    private fun initPlaylist() {
        rvPlayList.adapter = playlistAdapter
    }

    private fun initBanner() {
        viewPagerBanner.adapter = bannerAdapter
        indicatorBanner.setViewPager(viewPagerBanner)
        handler = Handler()
        var currentPage = 0
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
            if (it?.status == Status.FAILED)
                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
        })

        //Songtype
        homeViewModel.listSongType.observe(requireActivity(), Observer {
            tvTitleSongType.visibility = if (it != null && it.size != 0) View.VISIBLE else View.GONE
            songTypeAdapter.addDataSongType(it)
        })

        homeViewModel.networkStateSongType.observe(requireActivity(), Observer {
            if (it.status == Status.FAILED)
                Toast.makeText(requireActivity(), it.msg, Toast.LENGTH_LONG).show()
        })
    }

}
