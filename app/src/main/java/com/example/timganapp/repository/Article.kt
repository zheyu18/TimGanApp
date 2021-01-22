package com.example.timganapp.repository

data class Article(
    var _id: String, var createdAt: String, var desc: String,
    var images:List<String>,
    var publishedAt: String, var source: String, var type: String,
    var url: String, var used: Boolean, var who: String
)