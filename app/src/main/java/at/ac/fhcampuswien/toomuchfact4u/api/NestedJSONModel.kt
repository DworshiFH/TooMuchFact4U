package at.ac.fhcampuswien.toomuchfact4u.api

import com.google.gson.annotations.SerializedName

data class NestedJSONModel(
    var result: List<Data>
)

data class Data(
    @SerializedName("category")
    val category: String?

)