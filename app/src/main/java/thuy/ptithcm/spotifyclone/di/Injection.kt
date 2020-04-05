package thuy.ptithcm.spotifyclone.di

import androidx.lifecycle.ViewModelProvider
import thuy.ptithcm.spotifyclone.repository.*
import thuy.ptithcm.spotifyclone.viewmodel.*

object Injection {
    fun provideHomeViewModelFactory(): ViewModelProvider.Factory {
        return HomeViewModelFactory(HomeRepositoryImpl())
    }

    fun provideSongViewModelFactory(): ViewModelProvider.Factory {
        return SongViewModelFactory(SongRepositoryImpl())
    }

    fun provideAccViewModelFactory(): ViewModelProvider.Factory {
        return AuthViewModelFactory(AccRepositoryImpl())
    }

    fun provideAlbumViewModelFactory(): ViewModelProvider.Factory {
        return AlbumViewModelFactory(AlbumRepositoryImpl())
    }

    fun provideKindOfSongViewModelFactory(): ViewModelProvider.Factory {
        return KindOfSongViewModelFactory(KindOfSongRepositoryImpl())
    }

    fun provideUserViewModelFactory(): ViewModelProvider.Factory {
        return UserViewModelFactory(UserRepositoryImpl())
    }

    fun provideFavoriteSongViewModelFactory(): ViewModelProvider.Factory {
        return FavoriteSongViewModelFactory(FavoriteSongRepositoryImpl())
    }

}