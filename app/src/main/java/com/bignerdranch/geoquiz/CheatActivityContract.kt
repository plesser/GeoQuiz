package com.bignerdranch.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class CheatActivityContract : ActivityResultContract<Boolean, Boolean>() {

    override fun createIntent(context: Context, input: Boolean): Intent {
        return Intent(context, CheatActivity::class.java).apply {
            putExtra(EXTRA_ANSWER_IS_TRUE, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean? {
        if (resultCode != Activity.RESULT_OK) return null
        return intent?.extras?.getBoolean(EXTRA_ANSWER_SHOWN)
    }

    companion object{
        const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.geoquiz.answer_shown"
    }


}