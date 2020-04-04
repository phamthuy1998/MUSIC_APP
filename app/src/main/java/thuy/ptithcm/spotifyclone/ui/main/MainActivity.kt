package thuy.ptithcm.spotifyclone.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.ui.acc.ProfileFragment
import thuy.ptithcm.spotifyclone.ui.home.HomeFragment
import thuy.ptithcm.spotifyclone.ui.library.LibraryFragment
import thuy.ptithcm.spotifyclone.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var count = 0

    private val homeFragment: HomeFragment by lazy {
        HomeFragment.getInstance()
    }

    private val searchFragment by lazy {
        SearchFragment.getInstance()
    }

    private val accFragment by lazy {
        LibraryFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        showFragment(homeFragment)
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    count++
                    if (count == 2) {
                        // Todo() --> call event bus to scroll up recycler view
                    }
                    if (count == 3) {
                        // Todo --> call event bus to scroll up recycler view
                        count = 0
                    }
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
                    .replace(R.id.frmMain, fragment)
                    .commit()
            else
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frmMain, fragment)
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
}
