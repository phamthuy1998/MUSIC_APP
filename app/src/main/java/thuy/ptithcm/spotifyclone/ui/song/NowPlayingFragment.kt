package thuy.ptithcm.spotifyclone.ui.song


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_now_playing.*
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.FragmentNowPlayingBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.service.SoundService
import thuy.ptithcm.spotifyclone.viewmodel.NowPlayingViewModel


class NowPlayingFragment : Fragment() {

    private lateinit var binding: FragmentNowPlayingBinding
    private var song: Song? = null
    private lateinit var nowPlayingViewModel: NowPlayingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlayingViewModel = ViewModelProviders
            .of(this, Injection.provideSongViewModelFactory())
            .get(NowPlayingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val songID = requireActivity().intent.getStringExtra("SongID")
        if (songID != null)
            nowPlayingViewModel.getSongInfo(songID)
        else Toast.makeText(requireContext(), "Can't load info of this song!", Toast.LENGTH_LONG)
            .show()
        addEvents()
        bindingViewModel()
    }

    private fun addEvents() {
        binding.btnPlay.setOnClickListener {
            //            nowPlayingViewModel.songData.value?.id?.let { it1 -> appViewModel.playMediaId(it1) }
            stopAudio()
        }

        binding.btnFavorite.setOnClickListener { onLikeSong() }
    }

    private fun onLikeSong() {
        if (song != null && song?.id != null) {
            binding.btnFavorite.isSelected = !binding.btnFavorite.isSelected
            if (binding.btnFavorite.isSelected) nowPlayingViewModel.addFavoriteSong(song!!)
            else nowPlayingViewModel.removeFavoriteSong(song?.id!!)
        } else
            Toast.makeText(requireContext(), "Can't like this song!", Toast.LENGTH_LONG).show()
    }

    private fun playSong(song: Song?) {
        val objIntent = Intent(requireContext(), SoundService.getInstance()::class.java)
        objIntent.putExtra("uriSong", song?.fileName)
        requireContext().startService(objIntent)
    }

    fun stopAudio() {
        val objIntent = Intent(requireContext(), SoundService.getInstance()::class.java)
        requireContext().stopService(objIntent)
    }

    private fun updateUI(_song: Song?) {
        binding.btnFavorite.isSelected = song?.isLike ?: false
        song = _song
        binding.song = song
        binding.tvTotalTime.text = song?.time?.let { Song.timestampIntToMSS(requireContext(), it) }
        playSong(song)
        addSongIntoHistory(song)
    }

    private fun addSongIntoHistory(_song: Song?) {
        _song?.let { nowPlayingViewModel.addSongIntoHistory(it) }
    }

    private fun bindingViewModel() {
        nowPlayingViewModel.songData.observe(this, Observer {
            updateUI(it)
        })

        nowPlayingViewModel.currentTimeMedia.observe(this, Observer { positon ->
            binding.tvTimePlay.text = Song.timestampToMSS(requireContext(), positon)
        })

        nowPlayingViewModel.buttonPlayRes.observe(this, Observer { res ->
            btnPlay.setImageResource(res)
        })

        nowPlayingViewModel.networkStateSong.observe(this, Observer {
            binding.progressSongInfo.visibility =
                if (it != null && it.status == Status.RUNNING) View.VISIBLE else View.GONE
            if (it?.status == Status.FAILED)
                Toast.makeText(requireContext(), "Err: ${it.msg}", Toast.LENGTH_LONG).show()
        })

        nowPlayingViewModel.requestLikeSong.observe(requireActivity(), Observer { isLike ->
            if (isLike?.status == Status.SUCCESS)
                Toast.makeText(requireContext(), "Saved to your Library!", Toast.LENGTH_LONG).show()
        })
        nowPlayingViewModel.requestUnLikeSong.observe(requireActivity(), Observer { isUnlike ->
            if (isUnlike?.status == Status.SUCCESS) Toast
                .makeText(requireContext(), "Removed from your Library!", Toast.LENGTH_LONG).show()
        })

    }

}
