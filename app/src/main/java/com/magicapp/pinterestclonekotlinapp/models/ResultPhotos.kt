package com.magicapp.pinterestclonekotlinapp.models

data class ResultPhotos(
    var total: Int? = null,
    var total_pages: Int? = null,
    var results: ArrayList<PhotoElements>? = null
)
