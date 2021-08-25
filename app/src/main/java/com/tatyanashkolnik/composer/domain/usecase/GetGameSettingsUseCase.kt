package com.tatyanashkolnik.composer.domain.usecase

import com.tatyanashkolnik.composer.domain.entity.GameSettings
import com.tatyanashkolnik.composer.domain.entity.Level
import com.tatyanashkolnik.composer.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }

}