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

    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        return MainViewModelFactory(AlbumRepositoryImpl())
    }

    fun provideKindOfSongViewModelFactory(): ViewModelProvider.Factory {
        return KindOfSongViewModelFactory(KindOfSongRepositoryImpl())
    }

    fun provideHistorySongViewModelFactory(): ViewModelProvider.Factory {
        return HistorySongViewModelFactory(HistorySongRepositoryImpl())
    }

    fun provideLibraryViewModelFactory(): ViewModelProvider.Factory {
        return LibraryViewModelFactory(LibraryRepositoryImpl())
    }

    fun provideUserViewModelFactory(): ViewModelProvider.Factory {
        return UserViewModelFactory(LibraryRepositoryImpl())
    }

    fun provideFavoriteSongViewModelFactory(): ViewModelProvider.Factory {
        return FavoriteSongViewModelFactory(FavoriteSongRepositoryImpl())
    }

    fun provideFavoriteAlbumViewModelFactory(): ViewModelProvider.Factory {
        return FavoriteAlbumViewModelFactory(FavoriteAlbumRepositoryImpl())
    }

    fun provideArtistViewModelFactory(): ViewModelProvider.Factory {
        return ArtistViewModelFactory(ArtistRepositoryImpl())
    }

}