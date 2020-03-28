package thuy.ptithcm.spotifyclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_banner.view.*
import thuy.ptithcm.spotifyclone.R
import thuy.ptithcm.spotifyclone.data.Advertise

class BannerAdapter(
    private val bannerItemClick: (songId: String?) -> Unit
) : PagerAdapter() {

    private var arrAdvertise: ArrayList<Advertise>? = arrayListOf()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    fun addDataAdvertise(arr: ArrayList<Advertise>?) {
        if (arr != null)
            arrAdvertise?.apply {
                addAll(arr)
                notifyDataSetChanged()
            }
    }

    override fun getCount(): Int {
        return arrAdvertise?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_banner, container, false)
        val item = arrAdvertise?.get(position)
        view.apply {
            Glide.with(this)
                .load(item?.image_url)
                .error(R.drawable.gradient_item_banner)
                .into(imgBanner)

            Glide.with(this)
                .load(item?.image_artist)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .error(R.drawable.acc_img)
                .into(imgAvtBanner)

            tvTitleSongBanner.text = item?.song_name
            tvContentSongBanner.text = item?.content

            this.setOnClickListener { bannerItemClick(item?.id) }

            container.addView(this)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}