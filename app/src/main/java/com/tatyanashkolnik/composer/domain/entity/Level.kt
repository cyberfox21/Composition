package com.tatyanashkolnik.composer.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable {
    TEST,
    EASY,
    NORMAL,
    HARD
}