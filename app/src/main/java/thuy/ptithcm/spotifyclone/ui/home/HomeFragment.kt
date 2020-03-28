package thuy.ptithcm.spotifyclone.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thuy.ptithcm.spotifyclone.R

class HomeFragment : Fragment() {

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance() = instance ?: HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
