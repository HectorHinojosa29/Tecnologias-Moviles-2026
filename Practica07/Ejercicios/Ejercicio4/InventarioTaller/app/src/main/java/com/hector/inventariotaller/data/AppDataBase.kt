package com.hector.inventariotaller.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hector.inventariotaller.data.dao.MaterialDao
import com.hector.inventariotaller.data.model.Material

@Database(entities = [Material::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun materialDao(): MaterialDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventario_taller.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}