package thuy.ptithcm.spotifyclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.spotifyclone.data.SongType
import thuy.ptithcm.spotifyclone.databinding.ItemSongTypeBinding

class SongTypeAdapter(
    private val songTypeClick: (songType: SongType?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listSongType: ArrayList<SongType>? = arrayListOf()

    fun addDataSongType(arr: ArrayList<SongType>?) {
        if (arr != null)
            listSongType?.apply {
                clear()
                addAll(arr)
                notifyDataSetChanged()
            }
    }

    override fun getItemCount(): Int = listSongType?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SongTypeViewHolder(
            ItemSongTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listSongType?.get(position)?.let { (holder as SongTypeViewHolder).bind(it) }
    }

    inner class SongTypeViewHolder(
        private val binding: ItemSongTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongType) {
            binding.apply {
                songtype = item
                executePendingBindings()
                itemSongType.setOnClickListener { songTypeClick(item) }
            }
        }
    }

}