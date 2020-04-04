package thuy.ptithcm.spotifyclone.di

import androidx.lifecycle.ViewModelProvider
import thuy.ptithcm.spotifyclone.firebase.FirebaseAuth
import thuy.ptithcm.spotifyclone.firebase.FirebaseGetData
import thuy.ptithcm.spotifyclone.repository.*
import thuy.ptithcm.spotifyclone.viewmodel.*

object Injection {
    fun provideAuthViewModelFactory(): ViewModelProvider.Factory {
        return AuthViewModelFactory(AccRepository(FirebaseAuth()))
    }

    fun provideHomeViewModelFactory(): ViewModelProvider.Factory {
        return HomeViewModelFactory(HomeRepositoryImpl(FirebaseGetData()))
    }

    fun provideSongViewModelFactory(): ViewModelProvider.Factory {
        return SongViewModelFactory(SongRepositoryImpl(FirebaseGetData()))
    }

    fun provideAlbumViewModelFactory(): ViewModelProvider.Factory {
        return AlbumViewModelFactory(AlbumRepositoryImpl(FirebaseGetData()))
    }

    fun provideKindOfSongViewModelFactory(): ViewModelProvider.Factory {
        return KindOfSongViewModelFactory(KindOfSongRepositoryImpl(FirebaseGetData()))
    }

    fun provideUserViewModelFactory(): ViewModelProvider.Factory {
        return UserViewModelFactory(UserRepositoryImpl(FirebaseGetData()))
    }

    fun provideFavoriteSongViewModelFactory(): ViewModelProvider.Factory {
        return FavoriteSongViewModelFactory(FavoriteSongRepositoryImpl(FirebaseGetData()))
    }

}