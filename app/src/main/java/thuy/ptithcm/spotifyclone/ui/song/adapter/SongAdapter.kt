package thuy.ptithcm.spotifyclone.ui.song.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thuy.ptithcm.spotifyclone.base.DynamicSearchAdapter
import thuy.ptithcm.spotifyclone.data.EventTypeSong
import thuy.ptithcm.spotifyclone.data.Song
import thuy.ptithcm.spotifyclone.databinding.ItemSongBinding
import thuy.ptithcm.spotifyclone.listener.PopupMenuListener

class SongAdapter(
    private var listSong: MutableList<Song>? = arrayListOf(),
    private val songEvents: (songId: String?, type: EventTypeSong) -> Unit
) : DynamicSearchAdapter<Song>(listSong) {

    var popupMenuListener: PopupMenuListener? = null

    fun addDataListSong(arr: MutableList<Song>) {
        listSong?.apply {
            clear()
            addAll(arr)
            updateData(arr)
            notifyDataSetChanged()
        }
    }

    fun addDataSearch(arr: MutableList<Song>) {
        listSong?.apply {
            clear()
            addAll(arr)
            notifyDataSetChanged()
        }
    }

    fun removeAllData() {
        listSong?.apply {
            clear()
            notifyDataSetChanged()
        }
    }


    override fun getItemCount(): Int = listSong?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SongViewHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemId(position: Int): Long {
        return listSong?.get(position)?.id.hashCode().toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listSong?.get(position)?.let { (holder as SongViewHolder).bind(it) }
    }

    inner class SongViewHolder(
        private val binding: ItemSongBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Song) {
            binding.apply {
                song = item
                executePendingBindings()
                itemSongAlbum.setOnClickListener {
                    songEvents(item.id, EventTypeSong.ITEM_CLICK)
                }
//                btnSongShowMore.setOnClickListener {
//                    listSong
//                    songEvents(item.id, EventTypeSong.SHOW_MORE)
//                }
                btnSongShowMore.run {
                    setupMenu(popupMenuListener,item)
                }
            }
        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence): FilterResults {
//                if (constraint.isEmpty()) {
//                    listSearch = listSong
//                } else {
//                    val resultList = ArrayList<Song>()
//                    for (row in listSong) {
//                        if (row.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))) {
//                            resultList.add(row)
//                        }
//                    }
//                    listSearch = resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = listSearch
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                listSearch = results?.values as ArrayList<Song>
//                notifyDataSetChanged()
//            }
//
//        }
//    }
}


