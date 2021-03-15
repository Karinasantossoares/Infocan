package com.example.trasparenciagov.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trasparenciagov.model.persistencesRoom.DetailsPersonEntity
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.repository.DAO.DetailsPersonDao
import com.example.trasparenciagov.repository.DAO.ExpenseDao
import com.example.trasparenciagov.repository.DAO.PerfilPersonDao

@Database(
    entities = [PerfilPersonEntity::class,
        DetailsPersonEntity::class,
        ExpenseEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun perfilPersonDao(): PerfilPersonDao
    abstract fun detailsPersonDao(): DetailsPersonDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        fun instance(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, "AppDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}