package thuy.ptithcm.spotifyclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.databinding.ItemAlbumBinding

class AlbumAdapter(
    private val itemAlbumClick: (songTypeId: String?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listAlbum: ArrayList<Album>? = arrayListOf()

    fun addDataAlbum(arr: ArrayList<Album>?) {
        if (arr != null)
            listAlbum?.apply {
                clear()
                addAll(arr)
                notifyDataSetChanged()
            }
    }

    override fun getItemCount(): Int = listAlbum?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SongTypeViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listAlbum?.get(position)?.let { (holder as SongTypeViewHolder).bind(it) }
    }

    inner class SongTypeViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Album) {
            binding.apply {
                album = item
                executePendingBindings()
                itemPlaylist.setOnClickListener { itemAlbumClick(item.id) }
            }
        }
    }

}