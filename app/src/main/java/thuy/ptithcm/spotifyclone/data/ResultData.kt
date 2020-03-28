package thuy.ptithcm.spotifyclone.data

import androidx.lifecycle.LiveData

data class ResultData<T>(
    val data : LiveData<T> ?=null,
    val networkState: LiveData<NetworkState>
)