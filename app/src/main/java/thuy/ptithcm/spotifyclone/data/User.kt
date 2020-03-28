package thuy.ptithcm.spotifyclone.data

data class User(
    var id: String? = null,
    var email: String? = null,
    var username: String? = null,
    var dayOfBirth: String? = "",
    var gender: Boolean? = null,
    var password: String? = null,
    var profilePhoto: String? = null,
    var active: Boolean? = null,
    var dayCreateAcc: String? = null
)