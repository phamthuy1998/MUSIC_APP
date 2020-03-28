package thuy.ptithcm.spotifyclone.ui.notification


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import thuy.ptithcm.spotifyclone.R

class NotificationFragment : Fragment() {

    companion object {
        private var instance: NotificationFragment? = null

        fun getInstance() = instance ?: NotificationFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }


}
