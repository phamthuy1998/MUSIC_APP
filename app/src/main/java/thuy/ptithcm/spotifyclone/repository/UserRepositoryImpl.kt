package thuy.ptithcm.spotifyclone.repository

import thuy.ptithcm.spotifyclone.firebase.FirebaseAuth

class UserRepositoryImpl(private val firebase: FirebaseAuth) {

//    //disposable to dispose the Completable
//    private val compo = CompositeDisposable()
//
//     fun login(email: String, password: String): NetworkState {
//        var networkState = NetworkState.LOADING
//        compo.add(
//            firebase.login(email, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {
//                       return@subscribe (NetworkState.LOADED)
//                    },
//                    {
//                        networkState = (NetworkState.error(it.message))
//                    }
//                )
//        )
//        return networkState
//    }
//
//     fun register(email: String, password: String): NetworkState {
//        var networkState = NetworkState.LOADING
//        compo.add(
//            firebase.register(email, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {
//                        networkState = (NetworkState.LOADED)
//                    },
//                    {
//                        networkState = (NetworkState.error(it.message))
//                    }
//                )
//        )
//        return networkState
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        compo.clear()
//    }
//
//    override fun currentUser() {
//        firebase.currentUser()
//    }
//
//    override fun logout() {
//        firebase.logout()
//    }
}