package com.tatyanashkolnik.composer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatyanashkolnik.composer.data.GameRepositoryImpl
import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.entity.Question
import com.tatyanashkolnik.composer.domain.usecase.GenerateQuestionUseCase
import com.tatyanashkolnik.composer.domain.usecase.GetGameSettingsUseCase

class GameViewModel(private val level: Level) : ViewModel() {



}