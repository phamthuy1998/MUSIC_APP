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

//    private fun addSong(
//        _id: String?,
//        _songName: String?,
//        _fileName: String?,
//        _imageURL: String?,
//        _artistID: String?,
//        _artistInfor: Artist?,
//        _albumID: String?,
//        _album: Album?,
//        _lyric: String?,
//        _dayRelease: String?,
//        _typeID: String?,
//        _type: SongType?,
//        _description: String?,
//        _likeCounter: Int?,
//        _viewsCounter: Int?,
//        _country: Country?,
//        _countryID: String?
//    ) {
//        val currentUserDb = databaseRef()?.child(SONG)?.push()
//        val id = currentUserDb.
//        val song = Song().apply {
//            id = id
//            songName = ""
//            fileName = ""
//            imageURL = ""
//            artistID = ""
//            artistInfor = Artist()
//            albumID = ""
//            album = ""
//            lyric = ""
//            dayRelease = ""
//            typeID = ""
//            type = ""
//            description = ""
//            likeCounter = ""
//            viewsCounter = ""
//            country = ""
//            countryID = ""
//        }
//        databaseRef()?.child(SONG)?.child(id))?.setValue(user)
//    }

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


//    val artist = Artist().apply {
//            id = idPush
//            artistName = _artistName
//            introduce = _introduce
//            dayOfBirth = _dayOfBirth
//            gender = _gender
//            typeMusic = _typeMusic
//            isFollow = _isFollow
//            followCounter = _followCounter
//        }


     fun currentUser() = firebaseAuth.currentUser

     fun databaseRef() = firebaseDatabase?.reference

}