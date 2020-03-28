package thuy.ptithcm.spotifyclone.data

data class Song(
    var id: String? = null,
    var songName: String? = null,
    var fileName: String? = null,
    var imageURL: String? = null,
    var albumID: String? = null,
    var albumName: String? = null,
    var lyric: String? = null,
    var dayRelease: String? = null,
    var typeID: String? = null,
    var typeName: String? = null,
    var description: String? = null,
    var likeCounter: Int? = null,
    var viewsCounter: Int? = null,
    var countryName: String? = null,
    var countryID: String?=null
)

data class Country(
    var id: String? = null,
    var countryName: String? = null,
    var countryImage: String? = null
)
