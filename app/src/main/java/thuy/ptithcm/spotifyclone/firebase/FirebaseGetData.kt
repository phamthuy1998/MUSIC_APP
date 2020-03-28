package thuy.ptithcm.spotifyclone.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thuy.ptithcm.spotifyclone.data.Advertise
import thuy.ptithcm.spotifyclone.utils.ADVERTISE

class FirebaseGetData {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    fun getAdvertise(
        onPrepared : () -> Unit,
        onSuccess : (ArrayList<Advertise>?) -> Unit,
        onError : (String) -> Unit
    ){
        onPrepared()
        val listAdv= ArrayList<Advertise>()
        var advertise: Advertise?
        val query = databaseRef()?.child(ADVERTISE)?.limitToLast(30)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        advertise = ds.getValue(Advertise::class.java)
                        advertise?.let { listAdv.add(it) }
                    }
                    onSuccess(listAdv)
                }else{
                    onError("List empty")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    private fun currentUser() = firebaseAuth.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

}