package com.github.kolesovv.aisle.di

import androidx.lifecycle.ViewModel
import com.github.kolesovv.aisle.presentation.screens.main.MainViewModel
import com.github.kolesovv.aisle.presentation.screens.shopItem.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}