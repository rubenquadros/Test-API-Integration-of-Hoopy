package com.rubenquadros.apitest.di.module

import com.rubenquadros.apitest.utils.ApplicationConstants
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class RxJavaModule {

    @Provides
    @Named(ApplicationConstants.SUBSCRIBER_ON)
    @Singleton
    fun provideSubscriberOn(): Scheduler = Schedulers.io()

    @Provides
    @Named(ApplicationConstants.OBSERVER_ON)
    @Singleton
    fun provideObserverOn(): Scheduler = Schedulers.io()
}