package thuy.ptithcm.spotifyclone.ui.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.spotifyclone.base.DynamicSearchAdapter
import thuy.ptithcm.spotifyclone.data.Artist
import thuy.ptithcm.spotifyclone.databinding.ItemArtistFollowingBinding

class FavoriteArtistAdapter(
    var listArtist: MutableList<Artist>? = arrayListOf(),
    private val itemArtistClick: (artistId: String?) -> Unit
) :  DynamicSearchAdapter<Artist>(listArtist) {

    fun addDataArtist(arr: ArrayList<Artist>?) {
        if (arr != null)
            listArtist?.apply {
                clear()
                addAll(arr)
                updateData(arr)
                notifyDataSetChanged()
            }
    }

    fun addDataSearch(arr: MutableList<Artist>) {
        listArtist?.apply {
            clear()
            addAll(arr)
            notifyDataSetChanged()
        }
    }

    fun removeAllData() {
        listArtist?.apply {
            clear()
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int = listArtist?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AlbumViewHolder(
            ItemArtistFollowingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listArtist?.get(position)?.let { (holder as AlbumViewHolder).bind(it) }
    }

    inner class AlbumViewHolder(
        private val binding: ItemArtistFollowingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artist) {
            binding.apply {
                artist = item
                executePendingBindings()
                itemArtistFollowing.setOnClickListener { itemArtistClick(item.id) }
            }
        }
    }

}