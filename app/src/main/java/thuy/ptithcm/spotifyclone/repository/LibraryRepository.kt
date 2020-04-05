package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.data.User

interface LibraryRepository{
    fun getUserInfo(): ResultData<User>
    fun getSongHistoryList(): ResultData<ArrayList<Song>>
    fun signOut(): ResultData<Boolean>
}