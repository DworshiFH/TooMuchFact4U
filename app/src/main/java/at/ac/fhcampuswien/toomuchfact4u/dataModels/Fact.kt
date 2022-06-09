package at.ac.fhcampuswien.toomuchfact4u.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class Fact(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "question")
    var question: String?,

    @ColumnInfo(name = "correct_answer")
    var correct_answer: String?,

    //@ColumnInfo(name = "incorrect_answers")
    //var incorrect_answers: List<String>?,

    @ColumnInfo(name = "incorrect_answer_1")
    var incorrect_answer_1: String?,

    @ColumnInfo(name = "incorrect_answer_2")
    var incorrect_answer_2: String?,

    @ColumnInfo(name = "incorrect_answer_3")
    var incorrect_answer_3: String?,

    //@ColumnInfo(name = "all_answers")
    //var all_answers: List<String>?
)