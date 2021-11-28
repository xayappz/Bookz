package com.xayappz.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xayappz.repository.BooksRepository

 class BooksViewModelFactory(private val repository: BooksRepository,private val title:String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BooksViewModel(repository,title) as T
    }
}
