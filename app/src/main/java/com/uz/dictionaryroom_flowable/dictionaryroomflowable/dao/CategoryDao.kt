package com.uz.dictionaryroom_flowable.dictionaryroomflowable.dao

import androidx.room.*
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import io.reactivex.Flowable

@Dao
interface CategoryDao {
    @Insert
    fun addCategory(category: Category)

    @Delete
    fun deleteCategory(category: Category)

    @Update
    fun editCategory(category: Category)

    @Query("select* from category")
    fun getAllCategory(): Flowable<List<Category>>

    @Query("select * from category where id=:id")
    fun getCategoryById(id: Int): Category

    @Query("select * from category")
    fun getAllCategoryList(): List<Category>
}