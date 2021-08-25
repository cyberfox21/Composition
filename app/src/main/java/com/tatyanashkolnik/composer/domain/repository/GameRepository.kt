package com.tatyanashkolnik.composer.domain.repository

import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int) : Question

    fun getGameSettings(level: Level): GameSettings
}