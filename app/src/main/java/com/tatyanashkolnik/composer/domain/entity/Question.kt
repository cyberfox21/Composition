package com.tatyanashkolnik.composer.domain.entity

data class Question (
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
)