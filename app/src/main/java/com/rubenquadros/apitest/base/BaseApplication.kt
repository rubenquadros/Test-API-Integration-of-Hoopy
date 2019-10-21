package com.rubenquadros.apitest.base

import android.app.Application
import com.rubenquadros.apitest.di.component.AppComponent
import com.rubenquadros.apitest.di.component.DaggerAppComponent
import com.rubenquadros.apitest.di.module.ApiModule
import com.rubenquadros.apitest.di.module.RepositoryModule
import com.rubenquadros.apitest.di.module.RxJavaModule
import com.rubenquadros.apitest.utils.ApplicationConstants

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = initDagger()
    }

    protected open fun initDagger(): AppComponent =
        DaggerAppComponent.builder()
            .apiModule(ApiModule(ApplicationConstants.BASE_URL, this))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}