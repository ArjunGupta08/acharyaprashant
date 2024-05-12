package com.arjungupta08.arjunassignment.model

data class Thumbnail(
    val aspectRatio: Double,
    val basePath: String,
    val domain: String,
    val id: String,
    val key: String,
    val qualities: List<Int>,
    val version: Int
)