package com.tatyanashkolnik.composer.data

import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.entity.Question
import com.tatyanashkolnik.composer.domain.repository.GameRepository
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.collections.HashSet
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    const val MIN_SUM_VALUE = 2
    const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAswer = sum - visibleNumber
        val options = HashSet<Int>()
        options.add(rightAswer)
        val from = max(rightAswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(rightAswer + countOfOptions, maxSumValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }


    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(
                10,
                3,
                50,
                8
            )
            Level.EASY -> GameSettings(
                10,
                10,
                70,
                60
            )
            Level.NORMAL -> GameSettings(
                20,
                20,
                80,
                40
            )
            Level.HARD -> GameSettings(
                30,
                30,
                90,
                40
            )
        }
    }
}