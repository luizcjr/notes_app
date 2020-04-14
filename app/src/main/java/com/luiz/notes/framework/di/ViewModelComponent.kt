package com.luiz.notes.framework.di

import com.luiz.notes.presentation.base.BaseViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(listViewModel: BaseViewModel)
}