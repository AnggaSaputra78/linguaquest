package com.linguaquest.data.model

object ExerciseType {
    const val MULTIPLE_CHOICE = "MULTIPLE_CHOICE"
    const val FILL_BLANK = "FILL_BLANK"
    const val SENTENCE_BUILD = "SENTENCE_BUILD"
    const val MATCHING = "MATCHING"
}

data class ExerciseOptionUi(
    val id: String,
    val text: String,
    val isCorrect: Boolean
)

data class ExerciseUi(
    val id: String,
    val lessonId: String,
    val type: String,
    val skill: String,
    val instruction: String,
    val prompt: String,
    val answer: String,
    val explanation: String,
    val difficulty: Int,
    val options: List<ExerciseOptionUi>
)

data class WordChip(
    val id: String,
    val text: String
)

data class MatchingPairUi(
    val id: String,
    val left: String,
    val right: String
)

data class MatchingOptionUi(
    val id: String,
    val text: String
)