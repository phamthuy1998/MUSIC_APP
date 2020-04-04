package thuy.ptithcm.spotifyclone.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class SongType (
    var id: String? = null,
    var typeImage: String? = null,
    var typeName: String? = null,
    var description: String? = null
): Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "typeImage" to typeImage,
            "typeName" to typeName,
            "description" to description
        )
    }
}