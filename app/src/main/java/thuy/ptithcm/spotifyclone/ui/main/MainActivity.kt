package thuy.ptithcm.spotifyclone.ui.main

import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.di.Injection
import thuy.ptithcm.spotifyclone.listener.BottomSheetListener
import thuy.ptithcm.spotifyclone.ui.acc.ProfileFragment
import thuy.ptithcm.spotifyclone.ui.song.BottomPlayingFragment
import thuy.ptithcm.spotifyclone.ui.home.HomeFragment
import thuy.ptithcm.spotifyclone.ui.library.LibraryFragment
import thuy.ptithcm.spotifyclone.ui.search.SearchFragment
import thuy.ptithcm.spotifyclone.utils.gone
import thuy.ptithcm.spotifyclone.utils.replaceFragment
import thuy.ptithcm.spotifyclone.utils.visible
import thuy.ptithcm.spotifyclone.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var count = 0

    private val homeFragment: HomeFragment by lazy {
        HomeFragment.getInstance()
    }

    private var bottomSheetListener: BottomSheetListener? = null
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private val searchFragment by lazy {
        SearchFragment.getInstance()
    }

    private val accFragment by lazy {
        LibraryFragment.getInstance()
    }

    fun setBottomSheetListener(bottomSheetListener: BottomSheetListener) {
        this.bottomSheetListener = bottomSheetListener
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders
            .of(this, Injection.provideMainViewModelFactory())
            .get(MainViewModel::class.java)
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() {

    }

    private fun initViews() {
        replaceFragment(R.id.frmBotPlaying,
            BottomPlayingFragment(), "BottomPlayingFragment")
        showFragment(homeFragment)
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    count++
//                    if (count == 2) {
//                        // Todo() --> call event bus to scroll up recycler view
//                    }
//                    if (count == 3) {
//                        // Todo --> call event bus to scroll up recycler view
//                        count = 0
//                    }
                    showFragment(homeFragment)
                    true
                }
                R.id.menu_search -> {
                    count = 0
                    showFragment(searchFragment)
                    true
                }
                R.id.menu_lib -> {
                    count = 0
                    showFragment(accFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, fragmentName: String? = null) {
        if (!fragment.isAdded)
            if (fragmentName == null)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            else
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragmentName)
                    .commit()
    }

    fun showFragmentSearch(view: View) {
        showFragment(searchFragment)
        botNavigation.selectedItemId = R.id.menu_search
    }

    fun showProfileFragment(view: View) {
        showFragment(ProfileFragment(), "ProfileFragment")
    }

    fun onBack(view: View) = onBackPressed()

    override fun onBackPressed() {
        super.onBackPressed()
        bottomSheetBehavior?.let {
            if (it.state == BottomSheetBehavior.STATE_EXPANDED) {
                collapseBottomSheet()
            } else {
                super.onBackPressed()
            }
        }
    }
    private inner class BottomSheetCallback : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING || newState == BottomSheetBehavior.STATE_EXPANDED) {
                dimOverlay.visible()
            } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                dimOverlay.gone()
            }
            bottomSheetListener?.onStateChanged(bottomSheet, newState)
        }

        override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
            if (slideOffset > 0) {
                dimOverlay.alpha = slideOffset
            } else if (slideOffset == 0f) {
                dimOverlay.gone()
            }
            bottomSheetListener?.onSlide(bottomSheet, slideOffset)
        }
    }

    fun collapseBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun showBottomSheet() {
        if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

}
