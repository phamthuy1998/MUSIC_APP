package thuy.ptithcm.spotifyclone.ui.acc


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thuy.ptithcm.spotifyclone.R

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    companion object {
        private var instance: AccountFragment? = null

        fun getInstance() = instance ?: AccountFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_acc, container, false)
    }


}
