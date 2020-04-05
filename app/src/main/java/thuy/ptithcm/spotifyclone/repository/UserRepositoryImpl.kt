package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.data.User
import thuy.ptithcm.spotifyclone.utils.USER

class UserRepositoryImpl: UserRepository {

    private val firebaseAuth: FirebaseAuth? by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    private fun currentUser() = firebaseAuth?.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

    override fun getUserInfo(): ResultData<User> {
        val networkState = MutableLiveData<NetworkState>()
        val responseListSong = MutableLiveData<User>()
        networkState.postValue(NetworkState.LOADING)
        var user: User?
        val userID = currentUser()?.uid
        val query = userID?.let { databaseRef()?.child(USER)?.child(it) }
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        responseListSong.value = user
                        networkState.postValue(NetworkState.LOADED)
                    } else
                        networkState.postValue(NetworkState.error("Can't load info of your account!"))
                } else {
                    networkState.postValue(NetworkState.error("Can't find your account"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                networkState.postValue(NetworkState.error(databaseError.toException().toString()))
        }
        query?.addValueEventListener(valueEventListener)
        return ResultData(
            data = responseListSong,
            networkState = networkState
        )
    }

    override fun signOut(): ResultData<Boolean> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.postValue(NetworkState.LOADING)
        firebaseAuth?.signOut()
        networkState.postValue(NetworkState.LOADED)
        return ResultData(
            networkState = networkState
        )
    }


}