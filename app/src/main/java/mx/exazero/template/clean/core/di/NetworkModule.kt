package mx.exazero.template.clean.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import mx.exazero.template.clean.core.platform.NetworkHandler
import mx.exazero.template.clean.framework.api.ApiProvider
import mx.exazero.template.clean.framework.api.AuthorizationInterceptor
import javax.inject.Singleton

/**
 *  Created by JAzcorra96 on 10/11/2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkHandler(@ApplicationContext context: Context) = NetworkHandler(context)

    @Provides
    @Singleton
    fun provideNetworkProvider() = ApiProvider(AuthorizationInterceptor())
}