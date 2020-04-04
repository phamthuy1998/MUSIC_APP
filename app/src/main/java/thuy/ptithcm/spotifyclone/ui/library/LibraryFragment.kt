package thuy.ptithcm.spotifyclone.ui.library


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.spotifyclone.databinding.FragmentLibraryBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.viewmodel.UserViewModel

class LibraryFragment : Fragment() {

    companion object {
        private var instance: LibraryFragment? = null

        fun getInstance() = instance
            ?: LibraryFragment()
    }

    private lateinit var binding: FragmentLibraryBinding

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideUserViewModelFactory())
            .get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        binding.viewmodel = userViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        userViewModel.userInfo.observe(requireActivity(), Observer {
            binding.user = it
        })

    }


}
