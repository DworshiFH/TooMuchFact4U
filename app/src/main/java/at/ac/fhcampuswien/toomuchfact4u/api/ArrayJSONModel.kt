package at.ac.fhcampuswien.toomuchfact4u.api

import com.google.gson.annotations.SerializedName

data class ArrayJSONModel(
    @SerializedName("category")
    var category: String?,

    @SerializedName("question")
    var question: String?,

    @SerializedName("correct_answer")
    var correct_answer: String?


)
