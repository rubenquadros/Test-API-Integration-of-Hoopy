package com.rubenquadros.apitest.di.module

import com.rubenquadros.apitest.data.api.ApiService
import com.rubenquadros.apitest.data.repository.HoopyRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideHoopyRepo(apiService: ApiService): HoopyRepo {
        return HoopyRepo(apiService)
    }
}