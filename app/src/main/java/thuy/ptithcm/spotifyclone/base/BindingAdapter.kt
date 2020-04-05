package thuy.ptithcm.spotifyclone.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import thuy.ptithcm.spotifyclone.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}
@BindingAdapter("imageFromUrlForAvt")
fun bindImageFromUrlAvatar(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .error(R.drawable.ic_account)
            .into(view)
    }else
       view.setImageResource(R.drawable.ic_account)

}

@BindingAdapter("imageRoundedFromUrl")
fun bindImageRoundedFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(7)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}