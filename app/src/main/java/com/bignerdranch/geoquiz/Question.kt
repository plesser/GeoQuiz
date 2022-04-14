package com.bignerdranch.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {
    var isAnswer: Boolean = false
    var isCorrect: Boolean = false
}