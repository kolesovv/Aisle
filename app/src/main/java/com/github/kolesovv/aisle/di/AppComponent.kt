package com.github.kolesovv.aisle.di

import android.content.Context
import com.github.kolesovv.aisle.presentation.ShopItemFragment
import com.github.kolesovv.aisle.presentation.screens.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun setContext(context: Context): ComponentBuilder

        fun build(): AppComponent
    }
}