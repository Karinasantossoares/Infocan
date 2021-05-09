package com.example.trasparenciagov.repository.dao

import androidx.room.*
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE uid=:id")
    fun getExpense(id: Int): Single<ExpenseEntity>

    @Delete
    fun deleteExpense(expenseEntity: ExpenseEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expenseEntity: ExpenseEntity): Completable
}