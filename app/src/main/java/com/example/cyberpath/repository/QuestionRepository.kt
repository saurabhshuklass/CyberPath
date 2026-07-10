package com.example.cyberpath.repository

import com.example.cyberpath.model.Question

object QuestionRepository {

    fun getBeginnerQuestions(): List<Question> {

        return BeginnerQuestions.getQuestions()

    }

    fun getIntermediateQuestions(): List<Question> {

        return IntermediateQuestions.getQuestions()

    }

    fun getAdvancedQuestions(): List<Question> {

        return AdvancedQuestions.getQuestions()

    }

}