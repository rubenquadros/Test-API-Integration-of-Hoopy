package com.rubenquadros.apitest.di

import com.rubenquadros.apitest.base.BaseTest
import com.rubenquadros.apitest.di.module.RepositoryModule
import com.rubenquadros.apitest.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApiModule::class, TestRxJavaModule::class,
                        RepositoryModule::class, ViewModelModule::class])
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}