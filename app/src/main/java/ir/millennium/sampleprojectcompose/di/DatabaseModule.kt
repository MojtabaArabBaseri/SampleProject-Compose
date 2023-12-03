package ir.millennium.sampleprojectcompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.millennium.sampleprojectcompose.data.dataSource.local.database.SampleProjectDatabase
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.MOVIE_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, SampleProjectDatabase::class.java, MOVIE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: SampleProjectDatabase) = db.roomServiceDao()

}