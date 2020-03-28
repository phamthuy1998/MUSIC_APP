package thuy.ptithcm.spotifyclone.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.ui.acc.AccountFragment
import thuy.ptithcm.spotifyclone.ui.home.HomeFragment
import thuy.ptithcm.spotifyclone.ui.notification.NotificationFragment
import thuy.ptithcm.spotifyclone.ui.search.SearchFragment
import thuy.ptithcm.spotifyclone.utils.showFragment

class MainActivity : AppCompatActivity() {

    private var count = 0

    private val homeFragment: HomeFragment by lazy {
        HomeFragment.getInstance()
    }

    private val searchFragment by lazy {
        SearchFragment.getInstance()
    }

    private val notificationFragment by lazy {
        NotificationFragment.getInstance()
    }

    private val accFragment by lazy {
        AccountFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        showFragment(R.id.frm_main, homeFragment)
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    count++
                    if (count == 2) {
                        // Todo --> call event bus to scroll up recycler view
                        count = 0
                    }
                    showFragment(R.id.frm_main, homeFragment)
                    true
                }
                R.id.menu_search -> {
                    count = 0
                    showFragment(R.id.frm_main, searchFragment)
                    true
                }
                R.id.menu_notification -> {
                    count = 0
                    showFragment(R.id.frm_main, notificationFragment)
                    true
                }
                R.id.menu_acc -> {
                    count = 0
                    showFragment(R.id.frm_main, accFragment)
                    true
                }
                else -> false
            }
        }
    }

//    private fun showFragment(R.id.frm_main,fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.frm_main, fragment)
//            .commit()
//    }
}
