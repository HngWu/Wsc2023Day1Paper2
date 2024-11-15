package com.example.wsc2023day1paper2app.models

import kotlinx.serialization.Serializable


@Serializable
data class ProductItem (
    val sku: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val image: String,
    val availability: String
)