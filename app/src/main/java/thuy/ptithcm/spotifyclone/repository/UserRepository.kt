package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.User

interface UserRepository{
    fun getUserInfo(): ResultData<User>
    fun signOut(): ResultData<Boolean>
}