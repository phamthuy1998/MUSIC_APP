package thuy.ptithcm.spotifyclone.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showFragment(parent: Int, fragment: Fragment, fragmentName: String? = null) {
    if (fragmentName.isNullOrEmpty())
        supportFragmentManager.beginTransaction()
            .replace(parent, fragment)
            .commit()
    else
        supportFragmentManager.beginTransaction()
            .replace(parent, fragment)
            .addToBackStack(fragmentName)
            .commit()
}

fun Fragment.showFragment(parent: Int, fragment: Fragment, fragmentName: String? = null) {
    if (fragmentName.isNullOrEmpty())
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(parent, fragment)
            .commit()
    else
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(parent, fragment)
            .addToBackStack(fragmentName)
            .commit()
}

fun Context.startActivity(activity: Activity) =
    Intent(this, activity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun isValidEmail(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
}

fun isValidUsername(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.USER_NAME.matcher(input).matches()
}

fun isValidPassword(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.PASSWORD.matcher(input).matches()
}

fun isValidPhoneNumber(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.PHONE_NUMBER.matcher(input).matches()
}

fun isValidUrl(input: String): Boolean {
    return input.trim().isNotEmpty() && Patterns.URL.matcher(input).matches()
}


fun EditText.getTextTrim(): String {
    return text.trim().toString()
}

