package thuy.ptithcm.spotifyclone.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class ResultData<T>(
    val data: MutableLiveData<T>? = null,
    val networkState: LiveData<NetworkState>
)