package com.tatyanashkolnik.composer.domain.entity

data class GameSettings (
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPersentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)