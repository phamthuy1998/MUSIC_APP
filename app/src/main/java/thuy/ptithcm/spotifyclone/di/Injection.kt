package thuy.ptithcm.spotifyclone.di

import androidx.lifecycle.ViewModelProvider
import thuy.ptithcm.spotifyclone.firebase.FirebaseAuth
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData
import thuy.ptithcm.spotifyclone.repository.HomeRepositoryImpl
import thuy.ptithcm.spotifyclone.repository.UserRepository
import thuy.ptithcm.spotifyclone.viewmodel.AuthViewModelFactory
import thuy.ptithcm.spotifyclone.viewmodel.HomeViewModelFactory

object Injection {
    fun provideAuthViewModelFactory(): ViewModelProvider.Factory {
        return AuthViewModelFactory(UserRepository(FirebaseAuth()))
    }

    fun provideAdvertiseViewModelFactory(): ViewModelProvider.Factory {
        return HomeViewModelFactory(HomeRepositoryImpl(FirebaseGetData()))
    }

}