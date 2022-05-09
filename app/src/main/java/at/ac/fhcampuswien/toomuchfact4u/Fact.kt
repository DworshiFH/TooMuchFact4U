package at.ac.fhcampuswien.toomuchfact4u

import com.google.gson.annotations.SerializedName

data class Result(
    var result: List<Fact>?
)

data class Fact(
    var question: String?,

    var correct_answer: String?,

    var incorrect_answers: List<String>?,

    var all_answers: List<String>?
)