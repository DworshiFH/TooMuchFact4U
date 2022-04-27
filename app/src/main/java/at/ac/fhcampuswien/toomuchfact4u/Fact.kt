package at.ac.fhcampuswien.toomuchfact4u

import com.google.gson.annotations.SerializedName

data class Result(
    var result: List<Fact>?
)

import androidx.compose.runtime.Composable

data class Fact(
    @SerializedName("question")
    val question: String?,

    @SerializedName("correct_answer")
    val correct_answer: String?,

    val incorrect_answers: Incorrect_answer?,
)

data class Incorrect_answer(
    val incorrectAnswer1: String?,
    val incorrectAnswer2: String?,
    val incorrectAnswer3: String?
)