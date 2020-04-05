package thuy.ptithcm.spotifyclone.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.ui.auth.LoginActivity
import thuy.ptithcm.spotifyclone.ui.main.MainActivity


class SplashActivity : AppCompatActivity(), Animation.AnimationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ivLogo.apply {
            val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_splash)
            startAnimation(animation)
            animation?.setAnimationListener(this@SplashActivity)
        }

//        val firebaseAddData = FirebaseAddData()
//        firebaseAddData.addPlayList(Playlist(
//            null,
//            "",
//            "",
//            "",
//            ""
//        ))
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        val intent =
            if (com.google.firebase.auth.FirebaseAuth.getInstance().currentUser == null)
                Intent(this, LoginActivity::class.java)
            else
                Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAnimationStart(animation: Animation?) {
    }
}
