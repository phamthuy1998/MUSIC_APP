package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.utils.FAVORITE_SONG

class FavoriteSongRepositoryImpl() : FavoriteSongRepository {
    private val firebaseAuth: FirebaseAuth? by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    private fun currentUser() = firebaseAuth?.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

    override fun getListFavoriteSong(): ResultData<ArrayList<Song>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListFavoriteSong = MutableLiveData<ArrayList<Song>>()
        networkState.postValue(NetworkState.LOADING)
        val listSong = ArrayList<Song>()
        var song: Song?
        val query = currentUser()?.uid?.let { databaseRef()?.child(FAVORITE_SONG)?.child(it) }

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        song = ds.getValue(Song::class.java)
                        song?.let { listSong.add(it) }
                    }
                    if (listSong.size > 0) {
                        responseListFavoriteSong.value = listSong
                        networkState.postValue(NetworkState.LOADED)
                    } else
                        networkState.postValue(NetworkState.error("List favorite song are empty!"))
                } else {
                    networkState.postValue(NetworkState.error("List favorite song are empty!"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                networkState.postValue(NetworkState.error(databaseError.toException().toString()))
        }
        query?.addValueEventListener(valueEventListener)
        return ResultData(
            data = responseListFavoriteSong,
            networkState = networkState
        )
    }


}