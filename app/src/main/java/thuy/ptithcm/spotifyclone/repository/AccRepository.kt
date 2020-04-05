package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData

interface AccRepository {
    fun login(email: String, password: String): ResultData<Boolean>
    fun sendMailResetPassword(email: String): ResultData<Boolean>
    fun register(
        email: String,
        username: String,
        dayOfBirth: String,
        gender: Boolean,
        password: String
    ): ResultData<Boolean>
}