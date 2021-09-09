package com.tatyanashkolnik.composer.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tatyanashkolnik.composer.R
import com.tatyanashkolnik.composer.domain.entity.GameResult

// как выглядит в макете
// внутри тега TextView
// app:requiredAnswers="@{gameResult.gameSettings.minCountOfRightAnswers}"

@BindingAdapter("requiredAnswers") // имя атрибута, которое мы будем использовать в макете
fun bindRequiredAnswers(tv: TextView, count: Int) { // первый пар - какое view, 2 пар - значение
    tv.text = String.format(tv.context.resources.getString(R.string.required_score), count)
}

@BindingAdapter("yourScore")
fun bindYourScore(tv: TextView, count: Int) {
    tv.text = String.format(tv.context.resources.getString(R.string.score_answers), count)
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(tv: TextView, count: Int) {
    tv.text = String.format(tv.context.resources.getString(R.string.required_percentage), count)
}

@BindingAdapter("yourPercent")
fun bindYourPercent(tv: TextView, gameResult: GameResult) {
    tv.text = String.format(
        tv.context.resources.getString(R.string.score_percentage),
        getRightAnswersPersent(gameResult)
    )
}

private fun getRightAnswersPersent(gameResult: GameResult): Int {
    val right = gameResult.countOfRightAnswers.toDouble()
    val count = gameResult.countOfQuestions
    if (count == 0) return 0
    return ((right / count) * 100).toInt()
}

@BindingAdapter("humanImage")
fun bindImage(iv: ImageView, winner: Boolean){
    iv.setImageResource(getResultImageResource(winner))
}

private fun getResultImageResource(win: Boolean): Int {
    return when (win) {
        true -> R.drawable.winner
        false -> R.drawable.loser
    }
}