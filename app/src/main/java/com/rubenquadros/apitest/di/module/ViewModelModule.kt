package com.rubenquadros.apitest.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rubenquadros.apitest.factory.ViewModelFactory
import com.rubenquadros.apitest.viewmodel.EditViewModel
import com.rubenquadros.apitest.viewmodel.FetchViewModel
import com.rubenquadros.apitest.viewmodel.HomeViewModel
import com.rubenquadros.apitest.viewmodel.InsertViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun provideHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InsertViewModel::class)
    internal abstract fun provideInsertViewModel(viewModel: InsertViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FetchViewModel::class)
    internal abstract fun provideFetchViewModel(viewModel: FetchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel::class)
    internal abstract fun provideEditViewModel(viewModel: EditViewModel): ViewModel
}