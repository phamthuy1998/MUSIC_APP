package thuy.ptithcm.spotifyclone.ui.auth


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.spotifyclone.R

class LaunchFragment : Fragment() {

    companion object {
        private var instance: LaunchFragment? = null
        fun getInstance() = instance ?: LaunchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launch, container, false)
    }


}
