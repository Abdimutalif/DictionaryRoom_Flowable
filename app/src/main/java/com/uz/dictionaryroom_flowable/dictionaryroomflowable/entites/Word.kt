package com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var word: String,
    var translate: String,
    var photoUri: String,
    var categoryName: String,
    @ColumnInfo(name = "category_id")
    var categoryId: Int=0
) : Serializable