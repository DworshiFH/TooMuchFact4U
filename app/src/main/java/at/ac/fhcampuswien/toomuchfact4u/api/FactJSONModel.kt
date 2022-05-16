package at.ac.fhcampuswien.toomuchfact4u.api

import com.google.gson.annotations.SerializedName

data class FactJSONModel(
    @SerializedName("response:code")
    var response_code: Int?,
    @SerializedName("results")
    var result: List<Fact>?
)

data class Fact(
    @SerializedName("category")
    val category: String?,
    @SerializedName("correct_answer")
    val correct_answer: String?,
    @SerializedName("difficulty")
    val difficulty: String?,
    @SerializedName("incorrect_answers")
    val incorrect_answers: List<String?>,
    @SerializedName("question")
    val question: String?,
    @SerializedName("type")
    val type: String?
)