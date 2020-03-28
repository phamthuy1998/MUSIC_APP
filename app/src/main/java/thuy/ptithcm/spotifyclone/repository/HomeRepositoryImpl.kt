package thuy.ptithcm.spotifyclone.repository

import androidx.lifecycle.MutableLiveData
import thuy.ptithcm.spotifyclone.data.Advertise
import thuy.ptithcm.spotifyclone.data.NetworkState
import thuy.ptithcm.spotifyclone.data.ResultData
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData

class HomeRepositoryImpl(private  val firebase: FirebaseGetData) : HomeRepository{
    override fun getAdvertise(): ResultData<ArrayList<Advertise>> {
        val networkState = MutableLiveData<NetworkState>()
        val responseAdvertise = MutableLiveData<ArrayList<Advertise>>()
        firebase.getAdvertise(
            onPrepared = {
                networkState.postValue(NetworkState.LOADING)
            },
            onSuccess = {response ->
                responseAdvertise.value = response ?: arrayListOf()
                networkState.postValue(NetworkState.LOADED)
            },
            onError = {errMessage ->
                networkState.postValue(NetworkState.error(errMessage))
            }
        )
        return ResultData(
            data = responseAdvertise,
            networkState = networkState
        )
    }


}