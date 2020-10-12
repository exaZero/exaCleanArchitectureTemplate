package mx.exazero.template.clean.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import mx.exazero.template.clean.core.platform.AuthManager
import javax.inject.Singleton

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object HelperModule {
    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context) = AuthManager(context)
}