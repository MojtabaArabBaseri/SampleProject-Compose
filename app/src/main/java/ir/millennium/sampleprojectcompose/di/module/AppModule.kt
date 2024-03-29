package ir.millennium.sampleprojectcompose.di.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.millennium.sampleprojectcompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.color.colorHalfTransparentPrimary)
                    .error(R.color.colorHalfTransparentPrimary)

            )
}