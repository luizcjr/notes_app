package com.luiz.notes.presentation.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.luiz.notes.framework.di.ApplicationModule
import com.luiz.notes.framework.di.DaggerViewModelComponent
import com.luiz.notes.framework.usecase.UseCases
import javax.inject.Inject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var context: Context

    val loadError by lazy { MutableLiveData<String>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder().applicationModule(ApplicationModule(getApplication()))
            .build().inject(this)
    }
}