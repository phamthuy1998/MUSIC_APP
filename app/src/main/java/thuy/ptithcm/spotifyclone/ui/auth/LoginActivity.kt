package thuy.ptithcm.spotifyclone.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.utils.showFragment

class LoginActivity : AppCompatActivity() {

    private val landingFragment: LaunchFragment by lazy {
        LaunchFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showFragment(R.id.frmLogin, landingFragment)
    }

    fun btnCreateAccClick(view: View) = showFragment(R.id.frmLogin, SignupFragment(), "SignupFragment")

    fun btnLoginClick(view: View) = showFragment(R.id.frmLogin, SignInFragment(), "SignInFragment")
}
