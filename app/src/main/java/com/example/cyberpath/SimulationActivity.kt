package com.example.cyberpath

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cyberpath.model.Scenario
import com.example.cyberpath.repository.ScenarioRepository
import com.example.cyberpath.utils.ProgressManager

class SimulationActivity : AppCompatActivity() {

    // Header
    private lateinit var btnBack: ImageButton

    // Progress
    private lateinit var txtScenarioCount: TextView
    private lateinit var progressScenario: ProgressBar

    // Scenario
    private lateinit var txtScenarioTitle: TextView
    private lateinit var txtScenarioMessage: TextView
    private lateinit var txtWarningSigns: TextView

    // Options
    private lateinit var radioGroupOptions: RadioGroup
    private lateinit var rbOption1: RadioButton
    private lateinit var rbOption2: RadioButton
    private lateinit var rbOption3: RadioButton
    private lateinit var rbOption4: RadioButton

    // Buttons
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    // Result
    private lateinit var cardResult: View
    private lateinit var txtResult: TextView
    private lateinit var txtExplanation: TextView
    private lateinit var txtCyberTip: TextView

    // Data
    private val scenarios =
        ScenarioRepository.getScenarios()

    private var currentIndex = 0

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_simulation)

        initViews()

        loadScenario()

        btnBack.setOnClickListener {
            finish()
        }

        btnSubmit.setOnClickListener {

            submitAnswer()

        }

        btnNext.setOnClickListener {

            nextScenario()

        }

    }

    private fun initViews() {

        btnBack = findViewById(R.id.btnBack)

        txtScenarioCount =
            findViewById(R.id.txtScenarioCount)

        progressScenario =
            findViewById(R.id.progressScenario)

        txtScenarioTitle =
            findViewById(R.id.txtScenarioTitle)

        txtScenarioMessage =
            findViewById(R.id.txtScenarioMessage)

        txtWarningSigns =
            findViewById(R.id.txtWarningSigns)

        radioGroupOptions =
            findViewById(R.id.radioGroupOptions)

        rbOption1 =
            findViewById(R.id.rbOption1)

        rbOption2 =
            findViewById(R.id.rbOption2)

        rbOption3 =
            findViewById(R.id.rbOption3)

        rbOption4 =
            findViewById(R.id.rbOption4)

        btnSubmit =
            findViewById(R.id.btnSubmit)

        btnNext =
            findViewById(R.id.btnNext)

        cardResult =
            findViewById(R.id.cardResult)

        txtResult =
            findViewById(R.id.txtResult)

        txtExplanation =
            findViewById(R.id.txtExplanation)

        txtCyberTip =
            findViewById(R.id.txtCyberTip)

    }

    private fun loadScenario() {

        // Hide previous result
        cardResult.visibility = View.GONE

        // Enable Submit Button
        btnSubmit.isEnabled = true

        // Clear previous selection
        radioGroupOptions.clearCheck()

        // Get current scenario
        val scenario = scenarios[currentIndex]

        // Progress
        txtScenarioCount.text =
            "Scenario ${currentIndex + 1} of ${scenarios.size}"

        progressScenario.max = scenarios.size
        progressScenario.progress = currentIndex + 1

        // Scenario Details
        txtScenarioTitle.text = scenario.title

        txtScenarioMessage.text = scenario.message

        // Warning Signs
        txtWarningSigns.text =
            scenario.warningSigns.joinToString("\n") { "⚠ $it" }

        // Options
        rbOption1.text = scenario.options[0]

        rbOption2.text = scenario.options[1]

        rbOption3.text = scenario.options[2]

        rbOption4.text = scenario.options[3]

        // Change button text on last scenario
        if (currentIndex == scenarios.lastIndex) {

            btnNext.text = "Finish Simulation"

        } else {

            btnNext.text = "Next Scenario"

        }

    }

    private fun submitAnswer() {

        if (radioGroupOptions.checkedRadioButtonId == -1) {

            android.widget.Toast.makeText(
                this,
                "Please select an answer.",
                android.widget.Toast.LENGTH_SHORT
            ).show()

            return
        }

        val scenario = scenarios[currentIndex]

        val selectedIndex = when (radioGroupOptions.checkedRadioButtonId) {

            R.id.rbOption1 -> 0

            R.id.rbOption2 -> 1

            R.id.rbOption3 -> 2

            else -> 3

        }

        val correct = selectedIndex == scenario.correctAnswer

        if (correct) {

            score++

            txtResult.text = "✅ Correct!"

        } else {

            txtResult.text = "❌ Incorrect"

        }

        txtExplanation.text = scenario.explanation

        txtCyberTip.text = scenario.cyberTip

        cardResult.visibility = View.VISIBLE

        btnSubmit.isEnabled = false

    }

    private fun nextScenario() {

        currentIndex++

        if (currentIndex < scenarios.size) {

            loadScenario()

        } else {

            ProgressManager.markPracticalCompleted(

                "Social Engineering Simulator",

                onSuccess = {

                    android.widget.Toast.makeText(

                        this,

                        "Practical Completed!",

                        android.widget.Toast.LENGTH_SHORT

                    ).show()

                },

                onFailure = { message ->

                    android.widget.Toast.makeText(

                        this,

                        message,

                        android.widget.Toast.LENGTH_SHORT

                    ).show()

                }

            )

            val intent = Intent(
                this,
                SimulationResultActivity::class.java
            )

            intent.putExtra("score", score)
            intent.putExtra("total", scenarios.size)

            startActivity(intent)

            finish()

        }

    }
}