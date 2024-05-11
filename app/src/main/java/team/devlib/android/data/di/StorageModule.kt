package team.devlib.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.devlib.android.data.local.storage.AuthDataStorage
import team.devlib.android.data.local.storage.AuthDataStorageImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun provideStorageDataStorage(
        authDataStorageImpl: AuthDataStorageImpl
    ): AuthDataStorage

}