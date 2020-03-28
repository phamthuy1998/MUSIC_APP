package thuy.ptithcm.spotifyclone.data

data class Album(
    var id: String? = null,
    var albumName: String? = null,
    var artistList: ArrayList<ArtistAlbum>? = null,
    var imageURL: String? = null,
    var description: String? = null
)

data class ArtistAlbum(
    var id: String? = null,
    var artistName: String? = null
)