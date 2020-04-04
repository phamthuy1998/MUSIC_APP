package thuy.ptithcm.spotifyclone.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import thuy.ptithcm.spotifyclone.data.*
import thuy.ptithcm.spotifyclone.utils.*

class FirebaseGetData {
    private val firebaseAuth: FirebaseAuth? by lazy {
        FirebaseAuth.getInstance()
    }
    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    fun getAdvertise(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Advertise>?) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        val listAdv = ArrayList<Advertise>()
        var advertise: Advertise?
        val query = databaseRef()?.child(ADVERTISE)?.limitToLast(ITEM_COUNT_BANNER)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        advertise = ds.getValue(Advertise::class.java)
                        advertise?.let { listAdv.add(it) }
                    }
                    onSuccess(listAdv)
                } else {
                    onError("List empty")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getSongType(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<SongType>?) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        val listSongType = ArrayList<SongType>()
        var songType: SongType?
        val query = databaseRef()?.child(SONG_TYPE)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        songType = ds.getValue(SongType::class.java)
                        songType?.let { listSongType.add(it) }
                    }
                    onSuccess(listSongType)
                } else {
                    onError("List empty")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getPlaylist(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Playlist>?) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        val listPlaylist = ArrayList<Playlist>()
        var playlist: Playlist?
        val query = databaseRef()?.child(PLAYLIST)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        playlist = ds.getValue(Playlist::class.java)
                        playlist?.let { listPlaylist.add(it) }
                    }
                    if (listPlaylist.size > 0)
                        onSuccess(listPlaylist)
                    else
                        onError("List empty")
                } else {
                    onError("List empty")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getListAlbum(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Album>?) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        val listAlbum = ArrayList<Album>()
        var album: Album?
        val query = databaseRef()?.child(ALBUM)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        album = ds.getValue(Album::class.java)
                        album?.let { listAlbum.add(it) }
                    }
                    if (listAlbum.size > 0)
                        onSuccess(listAlbum)
                    else
                        onError("List empty")
                } else {
                    onError("List empty")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getSongByID(
        songID: String,
        onPrepared: () -> Unit,
        onSuccess: (Song) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        var song: Song?
        val query = databaseRef()?.child(SONG)?.child(songID)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    song = dataSnapshot.getValue(Song::class.java)
                    song?.let { onSuccess(it) }
                } else {
                    onError("Can't load info this song!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getListSong(
        id: String,
        childName: String,
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Song>) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        val listSong = ArrayList<Song>()
        var song: Song?
        val query = databaseRef()?.child(SONG)
            ?.orderByChild(childName)
            ?.equalTo(id)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ds in dataSnapshot.children) {
                        song = ds.getValue(Song::class.java)
                        song?.let { listSong.add(it) }
                    }
                    onSuccess(listSong)
                } else {
                    onError("List empty!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getListFavoriteSong(
        onPrepared: () -> Unit,
        onSuccess: (ArrayList<Song>) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
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
                    if (listSong.size > 0)
                        onSuccess(listSong)
                    else
                        onError("List favorite song are empty!")
                } else {
                    onError("List favorite song are empty!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getAlbumInfoByID(
        albumID: String,
        onPrepared: () -> Unit,
        onSuccess: (Album) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        var album: Album?
        val query = databaseRef()?.child(ALBUM)?.child(albumID)

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    album = dataSnapshot.getValue(Album::class.java)
                    if (album != null)
                        onSuccess(album!!)
                    else
                        onError("Can't load info this album!")
                } else {
                    onError("Can't load info this album!")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun getUserInfo(
        onPrepared: () -> Unit,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        onPrepared()
        var user: User?
        val userID = currentUser()?.uid
        val query = userID?.let { databaseRef()?.child(USER)?.child(it) }
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User::class.java)
                    if (user != null)
                        onSuccess(user!!)
                    else
                        onError("Can't load info of your account!")
                } else {
                    onError("Can't find your account")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) =
                onError(databaseError.toException().toString())
        }
        query?.addValueEventListener(valueEventListener)
    }

    fun signOut(onPrepared: () -> Unit, onSuccess: () -> Unit) {
        onPrepared()
        firebaseAuth?.signOut()
        onSuccess()
    }

    private fun currentUser() = firebaseAuth?.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

}