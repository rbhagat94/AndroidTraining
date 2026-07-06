package com.rohanbhagat.androidtraining.model

data class ProductListingResponse(
    val products: List<Product>?,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val tags: List<String> = emptyList(),
    val brand: String? = null,
    val reviews: List<Review> = emptyList(),
    val images: List<String> = emptyList(),
    val thumbnail: String = ""
)

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
)
