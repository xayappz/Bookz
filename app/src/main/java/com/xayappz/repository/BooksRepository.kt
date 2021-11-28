package com.xayappz.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xayappz.apis.BookService
import com.xayappz.apis.Response
import com.xayappz.models.BookList
import com.xayappz.util.isOnline

class BooksRepository(private val bookService: BookService, private val application: Application) {

    private val booksLiveData = MutableLiveData<Response<BookList>>()
    private val newbooksLiveData = MutableLiveData<Response<BookList>>()

    val netbooks: LiveData<Response<BookList>>
        get() = newbooksLiveData


    val books: LiveData<Response<BookList>>
        get() = booksLiveData

    suspend fun getBooks(topic: String) {
        if (isOnline.isOnline(application)) {
            val result = bookService.getBooksByGenre(topic)
            try {
                if (result.body() != null) {
                    booksLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                booksLiveData.postValue(Response.Error(e.message.toString()))

            }

        }


    }

    suspend fun getBooksBySearch(query: String) {

        if (isOnline.isOnline(application)) {
            val result = bookService.searchBooks(query)
            try {
                if (result.body() != null) {
                    newbooksLiveData.postValue(Response.Success(result.body()))
                }
            } catch (e: Exception) {
                newbooksLiveData.postValue(Response.Error(e.message.toString()))

            }

        }
    }
}
