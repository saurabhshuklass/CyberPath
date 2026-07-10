package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberpath.model.Question
import com.example.cyberpath.repository.QuestionRepository
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class QuizQuestionActivity : AppCompatActivity() {

    // Header
    private lateinit var btnBack: ImageButton
    private lateinit var txtQuizLevel: TextView
    private lateinit var txtQuestionNumber: TextView
    private lateinit var txtTimer: TextView
    private lateinit var txtProgress: TextView
    private lateinit var progressQuestions: ProgressBar

    // Question
    private lateinit var txtQuestion: TextView

    // Options
    private lateinit var cardOption1: MaterialCardView
    private lateinit var cardOption2: MaterialCardView
    private lateinit var cardOption3: MaterialCardView
    private lateinit var cardOption4: MaterialCardView

    private lateinit var txtOption1: TextView
    private lateinit var txtOption2: TextView
    private lateinit var txtOption3: TextView
    private lateinit var txtOption4: TextView

    // Buttons
    private lateinit var btnNext: Button
    private lateinit var btnSubmit: Button

    // Quiz Data
    private lateinit var quizLevel: String
    private lateinit var questions: List<Question>

    private var currentQuestion = 0
    private var selectedAnswer = -1
    private var score = 0
    private var answerLocked = false

    // Timer
    private var countDownTimer: CountDownTimer? = null
    private var timeInMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        initViews()

        quizLevel = intent.getStringExtra("QUIZ_LEVEL") ?: "BEGINNER"

        loadQuestions()

        setupOptionClickListeners()

        setupButtons()

        btnBack.setOnClickListener {
            showExitDialog()
        }
    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        txtQuizLevel = findViewById(R.id.txtQuizLevel)
        txtQuestionNumber = findViewById(R.id.txtQuestionNumber)
        txtTimer = findViewById(R.id.txtTimer)
        txtProgress = findViewById(R.id.txtProgress)

        progressQuestions = findViewById(R.id.progressQuestions)

        txtQuestion = findViewById(R.id.txtQuestion)

        cardOption1 = findViewById(R.id.cardOption1)
        cardOption2 = findViewById(R.id.cardOption2)
        cardOption3 = findViewById(R.id.cardOption3)
        cardOption4 = findViewById(R.id.cardOption4)

        txtOption1 = findViewById(R.id.txtOption1)
        txtOption2 = findViewById(R.id.txtOption2)
        txtOption3 = findViewById(R.id.txtOption3)
        txtOption4 = findViewById(R.id.txtOption4)

        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

    }

    private fun loadQuestions() {

        questions = when (quizLevel) {

            "BEGINNER" -> {

                txtQuizLevel.text = "🟢 Beginner Quiz"
                txtTimer.setTextColor(getColor(R.color.accent_blue))
                timeInMillis = 10 * 60 * 1000L

                QuestionRepository
                    .getBeginnerQuestions()
                    .shuffled()
                    .take(10)

            }

            "INTERMEDIATE" -> {

                txtQuizLevel.text = "🟡 Intermediate Quiz"
                txtTimer.setTextColor(getColor(R.color.accent_blue))
                timeInMillis = 12 * 60 * 1000L

                QuestionRepository
                    .getIntermediateQuestions()
                    .shuffled()
                    .take(10)

            }

            else -> {

                txtQuizLevel.text = "🔴 Advanced Quiz"
                txtTimer.setTextColor(getColor(R.color.accent_blue))
                timeInMillis = 15 * 60 * 1000L

                QuestionRepository
                    .getAdvancedQuestions()
                    .shuffled()
                    .take(10)

            }

        }

        if (questions.isEmpty()) {

            Toast.makeText(
                this,
                "No questions available.",
                Toast.LENGTH_LONG
            ).show()

            finish()

            return

        }

        currentQuestion = 0
        score = 0

        startTimer()

        showQuestion()

    }

    private fun showQuestion() {

        if (currentQuestion >= questions.size) {

            openResult()
            return

        }

        answerLocked = false
        selectedAnswer = -1

        btnNext.isEnabled = true
        btnSubmit.isEnabled = true

        setOptionsEnabled(true)

        resetOptionCards()

        val question = questions[currentQuestion]

        txtQuestionNumber.text =
            "Question ${currentQuestion + 1} of ${questions.size}"

        val progress =
            ((currentQuestion + 1) * 100) / questions.size

        txtProgress.text = "$progress% Completed"

        progressQuestions.max = questions.size
        progressQuestions.progress = currentQuestion + 1

        txtQuestion.text = question.question

        txtOption1.text = question.options[0]
        txtOption2.text = question.options[1]
        txtOption3.text = question.options[2]
        txtOption4.text = question.options[3]

        if (currentQuestion == questions.size - 1) {

            btnNext.visibility = android.view.View.GONE
            btnSubmit.visibility = android.view.View.VISIBLE

        } else {

            btnNext.visibility = android.view.View.VISIBLE
            btnSubmit.visibility = android.view.View.GONE

        }

    }

    private fun resetOptionCards() {

        val cards = listOf(
            cardOption1,
            cardOption2,
            cardOption3,
            cardOption4
        )

        cards.forEach {

            it.strokeWidth = 1

            it.strokeColor =
                getColor(R.color.accent_blue)

            it.setCardBackgroundColor(
                getColor(R.color.card_bg)
            )

        }

    }

    private fun setupOptionClickListeners() {

        cardOption1.setOnClickListener {

            selectOption(0)

        }

        cardOption2.setOnClickListener {

            selectOption(1)

        }

        cardOption3.setOnClickListener {

            selectOption(2)

        }

        cardOption4.setOnClickListener {

            selectOption(3)

        }

    }

    private fun selectOption(index: Int) {

        if (answerLocked) return

        selectedAnswer = index

        resetOptionCards()

        val selectedCard = when (index) {

            0 -> cardOption1
            1 -> cardOption2
            2 -> cardOption3
            else -> cardOption4

        }

        selectedCard.strokeWidth = 3

        selectedCard.strokeColor =
            getColor(R.color.accent_blue)

        selectedCard.setCardBackgroundColor(

            getColor(R.color.primary_blue)

        )

    }

    private fun startTimer() {

        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(
            timeInMillis,
            1000
        ) {

            override fun onTick(millisUntilFinished: Long) {

                timeInMillis = millisUntilFinished

                val minutes =
                    (millisUntilFinished / 1000) / 60

                val seconds =
                    (millisUntilFinished / 1000) % 60

                txtTimer.text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    minutes,
                    seconds
                )

                if (millisUntilFinished <= 60000) {

                    txtTimer.setTextColor(
                        getColor(android.R.color.holo_red_light)
                    )

                }

            }

            override fun onFinish() {

                txtTimer.text = "00:00"

                openResult()

            }

        }

        countDownTimer?.start()

    }

    private fun setupButtons() {

        btnNext.setOnClickListener {

            if (selectedAnswer == -1) {

                Toast.makeText(
                    this,
                    "Please select an answer",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            answerLocked = true

            checkAnswer()

            showAnswerFeedback()

            btnNext.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({

                currentQuestion++

                showQuestion()

            }, 1000)

        }

        btnSubmit.setOnClickListener {

            if (selectedAnswer == -1) {

                Toast.makeText(
                    this,
                    "Please select an answer",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener

            }

            answerLocked = true

            checkAnswer()

            showAnswerFeedback()

            btnSubmit.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed({

                openResult()

            }, 1000)

        }

    }

    private fun checkAnswer() {

        if (selectedAnswer == questions[currentQuestion].correctAnswer) {

            score++

        }

    }

    private fun showAnswerFeedback() {

        val correctAnswer =
            questions[currentQuestion].correctAnswer

        val cards = listOf(
            cardOption1,
            cardOption2,
            cardOption3,
            cardOption4
        )

        // Correct Answer
        cards[correctAnswer].strokeWidth = 3

        cards[correctAnswer].strokeColor =
            getColor(android.R.color.holo_green_light)

        cards[correctAnswer].setCardBackgroundColor(
            getColor(android.R.color.holo_green_dark)
        )

        // Wrong Answer
        if (selectedAnswer != correctAnswer) {

            cards[selectedAnswer].strokeWidth = 3

            cards[selectedAnswer].strokeColor =
                getColor(android.R.color.holo_red_light)

            cards[selectedAnswer].setCardBackgroundColor(
                getColor(android.R.color.holo_red_dark)
            )

        }

        setOptionsEnabled(false)

    }

    private fun setOptionsEnabled(enabled: Boolean) {

        cardOption1.isClickable = enabled
        cardOption2.isClickable = enabled
        cardOption3.isClickable = enabled
        cardOption4.isClickable = enabled

    }

    private fun openResult() {

        countDownTimer?.cancel()

        val intent = Intent(
            this,
            QuizResultActivity::class.java
        )

        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL", questions.size)
        intent.putExtra("LEVEL", quizLevel)

        startActivity(intent)

        finish()

    }

    private fun showExitDialog() {

        AlertDialog.Builder(this)

            .setTitle("Exit Quiz")

            .setMessage(
                "Your current quiz progress will be lost.\n\nDo you want to exit?"
            )

            .setCancelable(false)

            .setPositiveButton("Exit") { _, _ ->

                countDownTimer?.cancel()

                finish()

            }

            .setNegativeButton("Cancel", null)

            .show()

    }

    override fun onBackPressed() {

        showExitDialog()

    }

    override fun onDestroy() {

        super.onDestroy()

        countDownTimer?.cancel()

    }

}