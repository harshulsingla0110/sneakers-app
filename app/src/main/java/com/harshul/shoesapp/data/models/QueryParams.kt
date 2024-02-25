package com.harshul.shoesapp.data.models

data class QueryParams(
    var searchQuery: String = "",
    var sortBy: SortBy = SortBy.Popularity
)
