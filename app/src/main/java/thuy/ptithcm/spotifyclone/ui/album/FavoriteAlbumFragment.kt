package thuy.ptithcm.spotifyclone.ui.album

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_favorite_album.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Status
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.ui.album.adapter.FavoriteAlbumAdapter
import thuy.ptithcm.spotifyclone.utils.*
import thuy.ptithcm.spotifyclone.viewmodel.FavoriteAlbumViewModel


class FavoriteAlbumFragment : Fragment() {

    private val favoriteAlbumViewModel: FavoriteAlbumViewModel by lazy {
        ViewModelProviders
            .of(this, Injection.provideFavoriteAlbumViewModelFactory())
            .get(FavoriteAlbumViewModel::class.java)
    }
    private val albumAdapter by lazy {
        FavoriteAlbumAdapter(requireContext(), this::itemAlbumClick)
    }

    private fun itemAlbumClick(id: String?) {
        val albumFragment = AlbumFragment()
        val arguments = Bundle()
        arguments.putString("albumID", id)
        albumFragment.arguments = arguments
        showFragment(R.id.container, albumFragment, "AlbumFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvents()
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        gvFavoriteAlbum.adapter = albumAdapter
    }

    private fun addEvents() {
        swRefreshFavoriteAlbum.setOnRefreshListener {
            swRefreshFavoriteAlbum.isRefreshing = false
            favoriteAlbumViewModel.getListFavoriteAlbum()
        }
        btnBackFavoriteAlbum.setOnClickListener { requireActivity().onBackPressed() }
        btnFilterAlbum.setOnClickListener { showDialogFilter() }
    }

    private fun showDialogFilter() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose an animal")
        val sortType = arrayOf("Recently added", "Oldest", "Title A - Z")
        var checkedItem = favoriteAlbumViewModel.getFilterType() ?: 0

        builder.setSingleChoiceItems(
            sortType,
            checkedItem
        ) { _, which ->
            checkedItem = which
        }
        builder.setPositiveButton(
            "Done"
        ) { _, _ ->
            favoriteAlbumViewModel.setFilterType(checkedItem)
            requireContext().setInt(FILTER_TYPE, checkedItem)
            albumAdapter.filter(checkedItem)
        }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun bindViewModel() {
        if (requireContext().getInt(FILTER_TYPE) != -1) {
            favoriteAlbumViewModel.setFilterType(requireContext().getInt(FILTER_TYPE))
            favoriteAlbumViewModel.getFilterType()?.let { albumAdapter.filter(it) }
        }

        favoriteAlbumViewModel.listFavoriteAlbum.observe(this, Observer {
            albumAdapter.addDataAlbum(it)
        })
        favoriteAlbumViewModel.networkStateFavoriteAlbum.observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> {
                    progressFavoriteAlbum.visible()
                }
                Status.SUCCESS -> {
                    progressFavoriteAlbum.gone()
                    llListFavoriteAlbumEmpty.gone()
                    layoutFilterAlbum.visible()
                }
                Status.FAILED -> {
                    albumAdapter.removeAllData()
                    progressFavoriteAlbum.gone()
                    layoutFilterAlbum.gone()
                    llListFavoriteAlbumEmpty.visible()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
