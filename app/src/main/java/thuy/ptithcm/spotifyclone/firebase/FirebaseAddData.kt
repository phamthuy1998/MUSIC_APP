package thuy.ptithcm.spotifyclone.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import thuy.ptithcm.spotifyclone.data.*
import thuy.ptithcm.spotifyclone.utils.*


class FirebaseAddData {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firebaseDatabase: FirebaseDatabase? by lazy {
        FirebaseDatabase.getInstance()
    }

    fun addArtist(artist: Artist) {
        val idPush = databaseRef()?.child(ARTIST)?.push()?.key
        artist.id = idPush
        databaseRef()?.child(ARTIST)?.child(idPush.toString())?.setValue(artist)
    }

    fun addSong(song: Song) {
        val idPush = databaseRef()?.child(SONG)?.push()?.key
        song.id = idPush
        databaseRef()?.child(SONG)?.child(idPush.toString())?.setValue(song)
    }

    fun saveData() {
        val dataRef = databaseRef()?.child("data")
        dataRef?.setValue("I'm writing data")
            ?.addOnSuccessListener {
                // Write was successful!
                // ...
            }?.addOnFailureListener {
                // Write failed
                // ...
            }
    }


    fun addHistorySong(song: Song) {
        val query = databaseRef()?.child(HISTORY)?.child(currentUser()?.uid.toString())
            ?.child(song.id.toString())
        query?.setValue(song)
    }

    fun addFavoriteSong(
        song: Song,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val query = databaseRef()?.child(FAVORITE_SONG)?.child(currentUser()?.uid.toString())
            ?.child(song.id.toString())
        query?.setValue(song)?.addOnSuccessListener {
            onSuccess()
        }?.addOnFailureListener { err ->
            onError(err.message.toString())
        }
    }

    fun removeFavoriteSong(
        songID: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val query = databaseRef()?.child(FAVORITE_SONG)?.child(currentUser()?.uid.toString())
            ?.child(songID)
        query?.removeValue()?.addOnSuccessListener {
            onSuccess()
        }?.addOnFailureListener { err ->
            onError(err.message.toString())
        }
    }

    fun addAdvertise(advertise: Advertise) {
        val idPush = databaseRef()?.child(ADVERTISE)?.push()?.key
        advertise.id = idPush
        databaseRef()?.child(ADVERTISE)?.child(idPush.toString())?.setValue(advertise)
    }

    fun addAlbum(album: Album) {
        val idPush = databaseRef()?.child(ALBUM)?.push()?.key
        album.id = idPush
        databaseRef()?.child(ALBUM)?.child(idPush.toString())?.setValue(album)
    }

    fun addComment(comment: Comment) {
        val idPush = databaseRef()?.child(COMMENT)?.push()?.key
        comment.id = idPush
        databaseRef()?.child(COMMENT)?.child(idPush.toString())?.setValue(comment)
    }

    fun addPlayList(playlist: Playlist) {
        val idPush = databaseRef()?.child(PLAYLIST)?.push()?.key
        playlist.id = idPush
        databaseRef()?.child(PLAYLIST)?.child(idPush.toString())?.setValue(playlist)
    }

    fun addSongType(songType: SongType) {
        val idPush = databaseRef()?.child(SONG_TYPE)?.push()?.key
        songType.id = idPush
        databaseRef()?.child(SONG_TYPE)?.child(idPush.toString())?.setValue(songType)
    }

    fun addCountry(country: Country) {
        val idPush = databaseRef()?.child(COUNTRY)?.push()?.key
        country.id = idPush
        databaseRef()?.child(COUNTRY)?.child(idPush.toString())?.setValue(country)
    }

    private fun currentUser() = firebaseAuth.currentUser

    private fun databaseRef() = firebaseDatabase?.reference

}