package com.sign.movietoday.models.movielistrequest

data class MovieResponse(
    val dates : Date?,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)