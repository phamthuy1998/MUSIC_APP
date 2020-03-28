package thuy.ptithcm.spotifyclone.ui.home


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_banner.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.home.adapter.BannerAdapter
import thuy.ptithcm.spotifyclone.ui.song.SongActivity
import thuy.ptithcm.spotifyclone.viewmodel.HomeViewModel

class BannerFragment : Fragment() {

    companion object {
        private var instance: BannerFragment? = null
        fun getInstance() = instance ?: BannerFragment()
    }

    private val bannerAdapter by lazy {
        BannerAdapter(this::itemBannerClick)
    }

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private fun itemBannerClick(songId: String?) {
        val intent = Intent(requireContext(), SongActivity().javaClass)
        intent.putExtra("SongID", songId)
        requireContext().startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProviders
            .of(this, Injection.provideAdvertiseViewModelFactory())
            .get(HomeViewModel::class.java)
        homeViewModel.getAdvertise()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
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

    private fun bindViewModel() {
        homeViewModel.listAdvertise.observe(this, Observer {
            bannerAdapter.addDataAdvertise(it)
        })

        homeViewModel.networkState.observe(this, Observer {
            //            spinnerHome.visibility = if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
        })

    }
}
