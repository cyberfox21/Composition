package com.tatyanashkolnik.composer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatyanashkolnik.composer.data.GameRepositoryImpl
import com.tatyanashkolnik.composer.domain.entity.GameResult
import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.entity.Question
import com.tatyanashkolnik.composer.domain.usecase.GenerateQuestionUseCase
import com.tatyanashkolnik.composer.domain.usecase.GetGameSettingsUseCase

class GameViewModel : ViewModel() {

    var level: Level = Level.TEST

    private var countOfUserAnswers = 0
    private var countOfRightAnswers = 0

    private lateinit var gameSettings : GameSettings // настройки игры
    private lateinit var question: Question // текущий вопрос

    private var _updateQuestion = MutableLiveData<Question>()  // вопрос
    val updateQuestion: LiveData<Question>
        get() = _updateQuestion

    private var _userProgressBar = MutableLiveData<Int>() // чтобы обновить ProgressBar
    val userProgressBar: LiveData<Int>
        get() = _userProgressBar

    private var _gameResult = MutableLiveData<GameResult>() // показать ResultFragment
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val repository = GameRepositoryImpl

    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)

    fun getGameSettings() : GameSettings{
        gameSettings = getGameSettingsUseCase.invoke(level)
        return gameSettings
    }

    fun generateQuestion() {
        question = generateQuestionUseCase.invoke(gameSettings.maxSumValue)
        _updateQuestion.value = question
    }

    fun userAnswered(answer: Int) {

        if (answer == (question.sum - question.visibleNumber)) {
            countOfRightAnswers++
        }

        countOfUserAnswers++
        _userProgressBar.value = ((countOfRightAnswers.toDouble() / countOfUserAnswers.toDouble()) * 100).toInt()

        if (countOfUserAnswers == gameSettings.minCountOfRightAnswers) { // на самом деле надо когда время закончится
            val winner = true
            _gameResult.value = GameResult(winner, countOfRightAnswers, countOfUserAnswers, gameSettings)
        } else {
            generateQuestion()
        }
    }

}