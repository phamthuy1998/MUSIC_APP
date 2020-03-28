package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.data.Advertise
import thuy.ptithcm.spotifyclone.data.ResultData

interface HomeRepository{
    fun getAdvertise(): ResultData<ArrayList<Advertise>>
}