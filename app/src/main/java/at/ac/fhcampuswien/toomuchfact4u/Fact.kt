package at.ac.fhcampuswien.toomuchfact4u

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

    @ColumnInfo(name = "incorrect_answers")
    var incorrect_answers: List<String>?,

    @ColumnInfo(name = "all_answers")
    var all_answers: List<String>?
)