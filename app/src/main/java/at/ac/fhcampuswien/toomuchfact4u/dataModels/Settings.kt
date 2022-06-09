package at.ac.fhcampuswien.toomuchfact4u.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey
    val id: Long? = null,

    @ColumnInfo(name = "fact_frequency")
    var fact_frequency: Float?,

    @ColumnInfo(name = "use_sound")
    var use_sound: Boolean?,

    @ColumnInfo(name = "display_fact_as_question")
    var display_fact_as_question: Boolean?,

    @ColumnInfo(name = "category")
    var category: String?
)