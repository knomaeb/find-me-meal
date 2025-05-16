package com.example.findmemeal.di

import com.example.findmemeal.navigation.NavigationSubGraph
import com.example.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraph(searchFeatureApi: SearchFeatureApi): NavigationSubGraph{
        return NavigationSubGraph(searchFeatureApi)
    }
}