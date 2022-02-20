package com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    var name: String
):Serializable