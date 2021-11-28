package com.xayappz.models

data class BookList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)