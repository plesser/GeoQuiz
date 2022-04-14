package com.bignerdranch.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0


class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private var isCheater: Boolean = false

    private val activityCheatLauncher = registerForActivityResult(CheatActivityContract()){
        result ->
            if (result != null){
                Log.d(TAG, " result from CheatActivity " + result)
                isCheater = result
            } else {
                Log.d(TAG, " result from CheatActivity is empty")
            }
    }

//    private var currentIndex = 0
//
//    private val questionBank = listOf(
//        Question(R.string.question_australia, true),
//        Question(R.string.question_oceans, true),
//        Question(R.string.question_mideast, false),
//        Question(R.string.question_africa, false),
//        Question(R.string.question_americas, true),
//        Question(R.string.question_asia, true)
//    )

    private val quizViewModel: QuizViewModel by lazy{
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0)?:0
//            quizViewModel.currentIndex = currentIndex

//        val provider: ViewModelProvider = ViewModelProvider(this)
//        val quizViewModel = provider.get(QuizViewModel::class.java)
//        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener(){
//            var toast: Toast
//            toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.TOP, 0, 200)
//            toast.show()
            checkAnswer(true)
        }

        falseButton.setOnClickListener(){
//            var toast: Toast
//            toast = Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.TOP, 0, 200)
//            toast.show()
            checkAnswer(false)
        }

        nextButton.setOnClickListener(){
            quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener(){
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        questionTextView.setOnClickListener(){
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener(){
            //val intent = Intent(this, CheatActivity::class.java)
            val isAnserIsTrue = quizViewModel.currentQustionAnswer
            //val intent = CheatActivity.newIntent(this@MainActivity, isAnserIsTrue)
            //startActivityForResult(intent, REQUEST_CODE_CHEAT)
            activityCheatLauncher.launch(isAnserIsTrue)
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState(Bundle) called")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQustionAnswer
        quizViewModel.question.isAnswer = true

        val messageResId = if (userAnswer == correctAnswer) {
                quizViewModel.question.isCorrect = true
                R.string.correct_toast
            } else {
                quizViewModel.question.isCorrect = false
                R.string.incorrect_toast
            }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        var cntAnswer = 0
        var cntCorrect = 0;

        for (quiestion in quizViewModel.questionBank){
            if (quiestion.isAnswer){
                cntAnswer++
            }
            if (quiestion.isCorrect){
                cntCorrect++
            }
        }

        if (cntAnswer == quizViewModel.cntQuestion){
            //Toast.makeText(this, "Statistica " + , Toast.LENGTH_SHORT).show();
            Log.d(TAG, "" + cntCorrect + " / " + cntAnswer + " is correct")
        }

    }



    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}


