package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Song::class, Album::class, User::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun SongDao(): SongDao
    abstract fun AlbumDao(): AlbumDao
    abstract fun UserDao(): UserDao

    companion object{
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
             if (instance == null) {
                 synchronized(SongDatabase::class){
                     instance = Room.databaseBuilder(
                         context.applicationContext,
                         SongDatabase::class.java,
                         "user-database" // 다른 데이터베이스랑 이름 안겹치게!
                     ).allowMainThreadQueries().build()
                 }
             }
        return instance
        }
    }



}