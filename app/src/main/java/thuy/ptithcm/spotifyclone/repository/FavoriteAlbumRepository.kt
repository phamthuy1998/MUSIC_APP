package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.utils.FAVORITE_ALBUM

interface FavoriteAlbumRepository{
    fun getListFavoriteAlbum(): ResultData<ArrayList<Album>>
}


class FavoriteAlbumRepositoryImpl : FavoriteAlbumRepository {

    private val firebaseDatabase: FirebaseDatabase? by lazy { FirebaseDatabase.getInstance() }
    private val firebaseAuth: FirebaseAuth? by lazy { FirebaseAuth.getInstance() }
    private fun currentUser() = firebaseAuth?.currentUser
    private fun databaseRef() = firebaseDatabase?.reference

    override fun getListFavoriteAlbum(): ResultData<ArrayList<Album>>{
        val networkState = MutableLiveData<NetworkState>()
        val responseListFavoriteAlbum = MutableLiveData<ArrayList<Album>>()
        networkState.postValue(NetworkState.LOADING)
        val listFavoriteAlbum = ArrayList<Album>()
        var album: Album?
        val query = databaseRef()?.child(FAVORITE_ALBUM)?.child(currentUser()?.uid.toString())

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        album = ds.getValue(Album::class.java)
                        album?.let { listFavoriteAlbum.add(it) }
                    }
                    listFavoriteAlbum.reverse()
                    responseListFavoriteAlbum.value = listFavoriteAlbum
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.error("List empty!"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                networkState.postValue(NetworkState.error(databaseError.toException().toString()))
        }
        query?.addValueEventListener(valueEventListener)
        return ResultData(
            data = responseListFavoriteAlbum,
            networkState = networkState
        )
    }

}