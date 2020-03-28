package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.firebase.FirebaseAuth

class UserRepository(private val firebase: FirebaseAuth) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun sendMailResetPassword(email: String) = firebase.sendMailResetPassword(email)

    fun register(
        email: String,
        username: String,
        dayOfBirth: String,
        gender: Boolean,
        password: String
    ) = firebase.register(email, username, dayOfBirth, gender, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}