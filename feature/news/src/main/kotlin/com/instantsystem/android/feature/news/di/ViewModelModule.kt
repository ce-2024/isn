package com.instantsystem.android.feature.news.di

import androidx.lifecycle.SavedStateHandle
import com.instantsystem.android.feature.news.ui.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


internal val newsFeatureViewModelModule = module {
    viewModel { (savedStateHandle: SavedStateHandle) ->
        NewsViewModel(savedStateHandle, get())
    }
}