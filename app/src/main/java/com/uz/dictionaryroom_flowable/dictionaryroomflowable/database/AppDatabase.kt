package com.uz.dictionaryroom_flowable.dictionaryroomflowable.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.dao.CategoryDao
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.dao.WordDao
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Category
import com.uz.dictionaryroom_flowable.dictionaryroomflowable.entites.Word

@Database(entities = [Category::class, Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var appDatabase: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabase!!
        }
    }
}