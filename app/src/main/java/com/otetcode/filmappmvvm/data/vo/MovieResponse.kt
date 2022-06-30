package com.otetcode.filmappmvvm.data.vo


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    @SerializedName("results") // kalau tidak sesuai di api akan error = "invoke interface util.List"
    val movieList: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)