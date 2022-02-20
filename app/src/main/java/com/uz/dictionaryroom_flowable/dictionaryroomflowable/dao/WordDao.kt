package com.uz.dictionaryroom_flowable.dictionaryroomflowable.dao

import androidx.room.*
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word
import io.reactivex.Flowable
@Dao
interface WordDao {

    @Insert
    fun addWord(word: Word)

    @Update
    fun editWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("select * from word")
    fun getAllWord(): Flowable<List<Word>>

    @Query("select * from word where id = :id")
    fun getWordById(id: Int): Word

    @Query("select * from word where category_id =:categoryId")
    fun getAllWordByCategoryId(categoryId:Int):List<Word>


}

