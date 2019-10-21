package com.rubenquadros.apitest.di.component

import com.rubenquadros.apitest.base.BaseActivity
import com.rubenquadros.apitest.di.module.ApiModule
import com.rubenquadros.apitest.di.module.RepositoryModule
import com.rubenquadros.apitest.di.module.RxJavaModule
import com.rubenquadros.apitest.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RepositoryModule::class,
                        RxJavaModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(baseActivity: BaseActivity)

}