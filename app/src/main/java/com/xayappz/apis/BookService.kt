package com.xayappz.apis

import com.xayappz.models.BookList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {

    @GET("books")
    suspend fun getBooksByGenre(@Query("topic") topic: String): Response<BookList>

    @GET("books")
    suspend fun searchBooks(@Query("search") topic: String): Response<BookList>
}