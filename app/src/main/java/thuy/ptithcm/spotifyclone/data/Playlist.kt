package thuy.ptithcm.spotifyclone.data

data class Playlist(
    var id: String? = null,
    var playlistName: String? = null,
    var playlistImage: String? = null,
    var dayCreate: String? = null,
    var description: String? = null,
    var typeSong: ArrayList<SongType>? = null
)