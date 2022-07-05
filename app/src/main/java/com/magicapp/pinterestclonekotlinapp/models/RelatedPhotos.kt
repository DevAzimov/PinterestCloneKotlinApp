package com.magicapp.pinterestclonekotlinapp.models

data class RelatedPhotos (
    var total: Int? = null,
    var results: ArrayList<PhotoElements>? = null
)