package thuy.ptithcm.spotifyclone.ui.acc


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.databinding.FragmentProfileBinding
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.auth.LoginActivity
import thuy.ptithcm.spotifyclone.viewmodel.UserViewModel


class ProfileFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders
            .of(requireActivity(), Injection.provideUserViewModelFactory())
            .get(UserViewModel::class.java)
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = userViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        addEvents()
    }

    private fun addEvents() {
        binding.btnSignOut.setOnClickListener { showDialogConfirmSignOut() }
    }

    private fun showDialogConfirmSignOut() {
        val builder = AlertDialog.Builder(requireContext())
        val optionDialog =builder.create()
        with(builder)
        {
            setMessage("Are you sure you want to log out this account?")
            setPositiveButton("Sign out", DialogInterface.OnClickListener { dialog, id ->
                openLoginActivity()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                optionDialog.dismiss()
            })
            show()
        }

    }

    private fun bindViewModel() {
        userViewModel.networkSignOut.observe(requireActivity(), Observer {
            binding.progressProfile.visibility =
                if (it?.status == Status.RUNNING) View.VISIBLE else View.GONE
            if (it?.status == Status.SUCCESS) openLoginActivity()
        })

        userViewModel.userInfo.observe(requireActivity(), Observer { user ->
            if (user != null)
                binding.user = user
        })
    }

    private fun openLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity().javaClass)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }
}
