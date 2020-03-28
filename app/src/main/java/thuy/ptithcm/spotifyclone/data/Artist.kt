package thuy.ptithcm.spotifyclone.data

data class Artist(
    var id: String? = null,
    var imagePhoto: String? = null,
    var artistName: String? = null,
    var introduce: String? = null,
    var dayOfBirth: String? = null,
    var gender: Boolean? = null,
    var typeMusic: ArrayList<SongType>? = null,
    var followCounter: Int? = null
)