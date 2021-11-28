package com.xayappz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xayappz.apis.Response
import com.xayappz.models.BookList
import com.xayappz.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BooksRepository, private val topic: String) :
    ViewModel() {

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getBooks(topic)

        }

    }

    val books: LiveData<Response<BookList>>
        get() = repository.books


    private val newbooks: LiveData<Response<BookList>>
        get() = repository.netbooks


    suspend fun getSearchedBooks(query: String): LiveData<Response<BookList>> {
        repository.getBooksBySearch(query)

        return newbooks
    }

}
