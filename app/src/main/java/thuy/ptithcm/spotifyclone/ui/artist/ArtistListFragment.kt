package thuy.ptithcm.spotifyclone.ui.artist


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_artist_list.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.artist.adapter.FavoriteArtistAdapter
import thuy.ptithcm.spotifyclone.utils.*
import thuy.ptithcm.spotifyclone.viewmodel.ArtistViewModel

class ArtistListFragment : Fragment(), TextWatcher {

    private val artistViewModel: ArtistViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideArtistViewModelFactory())
            .get(ArtistViewModel::class.java)
    }

    private val artistAdapter by lazy {
        FavoriteArtistAdapter(mutableListOf(), this::itemArtistClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistViewModel.getListArtistFollowing()
    }

    private fun itemArtistClick(id: String?) {
        val artistFragment = ArtistInfoFragment()
        val arguments = Bundle()
        arguments.putString("artistID", id)
        artistFragment.arguments = arguments
        showFragment(R.id.container, artistFragment, "ArtistInfoFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideKeyboard()
        hideSearch()
        bindViewModel()
        initViews()
        addEvents()
    }

    private fun addEvents() {
        btnBackFollowing.setOnClickListener { onButtonBackClick() }
        btnSearchFollowing.setOnClickListener { showSearch() }
        swRefreshFollowing.setOnRefreshListener {
            artistViewModel.getListArtistFollowing()
            swRefreshFollowing.isRefreshing = false
        }
        edtSearchFollowing.addTextChangedListener(this)
    }

    private fun onButtonBackClick() {
        if (tvTbFollowing.visibility == View.VISIBLE)
            activity?.onBackPressed()
        else hideSearch()
    }


    private fun showSearch() {
        requireActivity().showKeyboard()
        edtSearchFollowing.visible()
        edtSearchFollowing.requestFocus()
        layoutSearchFollowing.gone()
        tvTbFollowing.gone()
    }

    private fun hideSearch() {
        tvTbFollowing.visible()
        edtSearchFollowing.gone()
        layoutSearchFollowing.visible()
        activity?.hideKeyboard()
    }
    private fun hideSearchView() {
        tvTbFollowing.visible()
        edtSearchFollowing.gone()
        edtSearchFollowing.setText("")
        layoutSearchFollowing.gone()
        activity?.hideKeyboard()
    }


    private fun initViews() {
        rvArtistFollowing.adapter = artistAdapter
        rvArtistFollowing.setHasFixedSize(true)
        rvArtistFollowing.setItemViewCacheSize(20)
    }

    private fun bindViewModel() {
        artistViewModel.listArtist.observe(this, Observer {
            artistAdapter.addDataArtist(it)
        })

        artistViewModel.statusListArtist.observe(this, Observer {
            activity?.hideKeyboard()
            when (it.status) {
                Status.RUNNING -> {
                    progressFollowing.visible()
                }
                Status.SUCCESS -> {
                    llSearchArtistNotFound.gone()
                    progressFollowing.gone()
                    llListArtistEmpty.gone()
                    hideSearch()
                }
                Status.FAILED -> {
                    artistAdapter.removeAllData()
                    llSearchArtistNotFound.gone()
                    progressFollowing.gone()
                    hideSearchView()
                    llListArtistEmpty.visible()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        artistAdapter.search(s.toString(), {
            llSearchArtistNotFound.visible()
            artistAdapter.removeAllData()
        }, {
            artistAdapter.addDataSearch(it)
            llSearchArtistNotFound.gone()
        })
    }


}
