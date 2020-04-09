package thuy.ptithcm.spotifyclone.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_song.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.base.FragmentPagerAdapter
import thuy.ptithcm.string.support.ViewPagerListener
import kotlin.math.abs
import kotlin.math.max

class SongFragment : Fragment() {

    companion object {
        private const val MIN_SCALE = 0.65f
        private const val MIN_ALPHA = 0.3f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTabLayout()
        addEvents()
    }

    private fun addEvents() {
        btnBackNowPlaying.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun addTabLayout() {
        val adapter = FragmentPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(SongInfoFragment(), "Song info")
        adapter.addFragment(NowPlayingFragment(), "Now playing")
        adapter.addFragment(LyricFragment(), "Lyric of song")
        pageSongDetail.adapter = adapter
        pageSongDetail.currentItem = 1
//        pageSongDetail.setPageTransformer(true, this::zoomOutTransformation)
        pageSongDetail.addOnPageChangeListener(ViewPagerListener(this::onPageSelected))
    }


    private fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot_selected)
                secondDotImageView.setImageResource(R.drawable.ic_dot)
                thirdDotImageView.setImageResource(R.drawable.ic_dot)
            }
            1 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot)
                secondDotImageView.setImageResource(R.drawable.ic_dot_selected)
                thirdDotImageView.setImageResource(R.drawable.ic_dot)
            }
            2 -> {
                firstDotImageView.setImageResource(R.drawable.ic_dot)
                secondDotImageView.setImageResource(R.drawable.ic_dot)
                thirdDotImageView.setImageResource(R.drawable.ic_dot_selected)
            }
        }
    }

    private fun zoomOutTransformation(page: View, position: Float) {
        when {
            position < -1 ->
                page.alpha = 0f
            position <= 1 -> {
                page.scaleX = max(MIN_SCALE, 1 - abs(position))
                page.scaleY = max(MIN_SCALE, 1 - abs(position))
                page.alpha = max(MIN_ALPHA, 1 - abs(position))
            }
            else -> page.alpha = 0f
        }
    }

}
