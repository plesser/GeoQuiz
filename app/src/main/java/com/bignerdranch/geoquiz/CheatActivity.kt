package com.bignerdranch.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract

private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.geoquiz.answer_is_true"
private const val TAG = "CheatActivity"

class CheatActivity : AppCompatActivity() {

    private var anserIsTrue = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        anserIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        Log.d(TAG, "We got " + anserIsTrue)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener(){
            val answerText = when {
                anserIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            val result = Intent().putExtra(CheatActivityContract.EXTRA_ANSWER_SHOWN, true)
            setResult(Activity.RESULT_OK, result)
        }

    }

    companion object {
        fun newIntent(packageContext: Context, anserIsTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, anserIsTrue)
            }
        }
    }
}