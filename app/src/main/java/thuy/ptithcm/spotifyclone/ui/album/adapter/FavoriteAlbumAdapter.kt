package thuy.ptithcm.spotifyclone.ui.album.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import thuy.ptithcm.spotifyclone.data.Album
import thuy.ptithcm.spotifyclone.databinding.ItemFavoriteAlbumBinding


class FavoriteAlbumAdapter(
    context: Context,
    private val itemAlbumClick: (albumID: String?) -> Unit
) : BaseAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    private var listAlbum: ArrayList<Album>? = arrayListOf()
    private var filterType: Int? = 0

    fun filter(type: Int) {
        when (type) {
            0 -> {// Newest
                if (filterType == 1) {
                    listAlbum?.reverse(); notifyDataSetChanged()
                } else if (filterType == 2) {
                    sortListAlbumByName();notifyDataSetChanged()
                }
                filterType = 0
            }
            1 -> { // Oldest
                if (filterType == 0) {
                    listAlbum?.reverse();notifyDataSetChanged()
                } else if (filterType == 2) {
                    sortListAlbumByName();notifyDataSetChanged()
                }
                filterType = 1
            }
            2 -> { // Album name A - Z
                sortListAlbumByName()
                notifyDataSetChanged()
                filterType = 2
            }
        }
    }

    private fun sortListAlbumByName() {
        if (listAlbum?.size != 0) {
            val list = listAlbum?.sortedWith(compareBy(Album::albumName, Album::description))
            if (list != null) {
                listAlbum = ArrayList(list)
                notifyDataSetChanged()
            }
        }
    }

    fun addDataAlbum(arr: ArrayList<Album>?) {
        if (arr != null) listAlbum?.apply { clear(); addAll(arr); notifyDataSetChanged() }
    }

    fun removeAllData() {
        listAlbum?.clear()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemBinding: ItemFavoriteAlbumBinding =
            if (convertView == null || convertView.tag == null)
                ItemFavoriteAlbumBinding.inflate(layoutInflater, parent, false)
            else convertView.tag as ItemFavoriteAlbumBinding

        val favoriteAlbum = listAlbum?.get(position)

        itemBinding.apply {
            album = favoriteAlbum
            executePendingBindings()
            itemFavoriteAlbum.setOnClickListener { itemAlbumClick(favoriteAlbum?.id) }
        }

        return itemBinding.root
    }

    override fun getItem(position: Int): Album? = listAlbum?.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = listAlbum?.size ?: 0

}