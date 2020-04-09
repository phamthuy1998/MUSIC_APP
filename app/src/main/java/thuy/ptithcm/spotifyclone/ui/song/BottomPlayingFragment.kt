package thuy.ptithcm.spotifyclone.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thuy.ptithcm.spotifyclone.databinding.FragmentBottomPlayingBinding
import thuy.ptithcm.spotifyclone.listener.BottomSheetListener
import thuy.ptithcm.spotifyclone.utils.addFragment

class BottomPlayingFragment : Fragment(), BottomSheetListener {

    private lateinit var binding: FragmentBottomPlayingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rootView.setOnClickListener {
            activity.addFragment(fragment = SongFragment(), tag = "NowPlaying")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {

    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {

    }
}