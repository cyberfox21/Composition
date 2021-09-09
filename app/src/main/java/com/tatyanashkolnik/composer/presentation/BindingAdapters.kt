package com.tatyanashkolnik.composer.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
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
fun bindRequiredPercent(tv: TextView, percent: Int) {
    tv.text = String.format(tv.context.resources.getString(R.string.required_percentage), percent)
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



@BindingAdapter("intToString")
fun bindIntToString(tv: TextView, int: Int) {
    tv.text = int.toString()
}

@BindingAdapter("progressBar")
fun bindProgressBar(pb: ProgressBar, progressValue : Int){
    pb.setProgress(progressValue, true)
}

interface OnOptionClickListener{           // чтобы передать в макет функцию,
    fun onOptionClick(answer: Int)         // которую мы будем вызывать у viewmodel
}

@BindingAdapter("optionClickListener")
fun onOptionClickListener(tv: TextView, clickListener: OnOptionClickListener){
    tv.setOnClickListener {
        clickListener.onOptionClick(tv.text.toString().toInt())
    }
}

private fun calculateColor(expression: Boolean): Int {
    return when (expression) {
        true -> Color.GREEN
        false -> Color.RED
    }
}

@BindingAdapter("enoughCount")
fun enoughCount(tv: TextView, enoughCount: Boolean){
    val color = calculateColor(enoughCount)
    tv.setTextColor(color)
}

@BindingAdapter("enoughPercent")
fun enoughPercent(pb : ProgressBar, enoughPercent: Boolean){
    val color = calculateColor(enoughPercent)
    pb.progressTintList = ColorStateList.valueOf(color)
}