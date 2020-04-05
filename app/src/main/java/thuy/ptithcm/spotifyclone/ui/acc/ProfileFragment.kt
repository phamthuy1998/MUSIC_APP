package thuy.ptithcm.spotifyclone.ui.acc


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.FragmentProfileBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.auth.LoginActivity
import thuy.ptithcm.spotifyclone.viewmodel.ProfileViewModel


class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideUserViewModelFactory())
            .get(ProfileViewModel::class.java)
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = profileViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        addEvents()
    }

    private fun addEvents() {
        binding.btnSignOut.setOnClickListener { showDialogConfirmSignOut() }
        binding.swProfile.setOnRefreshListener { refreshProfile() }
    }

    private fun refreshProfile() {
        profileViewModel.getUserInfo()
        binding.swProfile.isRefreshing = false
    }

    private fun showDialogConfirmSignOut() {
        val builder = AlertDialog.Builder(requireContext())
        val optionDialog = builder.create()
        with(builder)
        {
            setMessage("Are you sure you want to log out this account?")
            setPositiveButton("Sign out", DialogInterface.OnClickListener { dialog, id ->
                profileViewModel.signOut()
                openLoginActivity()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                optionDialog.dismiss()
            })
            show()
        }

    }

    private fun bindViewModel() {
        profileViewModel.networkSignOut.observe(requireActivity(), Observer {
            binding.progressProfile.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
            if (it?.status == Status.SUCCESS) openLoginActivity()
        })

        profileViewModel.userInfo.observe(requireActivity(), Observer { user ->
            if (user != null)
                binding.user = user
        })
        profileViewModel.networkStateUserInfo.observe(this, Observer {
            if (it?.status == Status.FAILED) Toast
                .makeText(requireContext(), "Can't load info of your profile!", Toast.LENGTH_LONG)
                .show()
        })
        profileViewModel.networkSignOut.observe(this, Observer {
            if (it?.status == Status.SUCCESS) Toast
                .makeText(requireContext(), "Log out success!", Toast.LENGTH_LONG)
                .show()
        })
    }

    private fun openLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity().javaClass)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }
}
