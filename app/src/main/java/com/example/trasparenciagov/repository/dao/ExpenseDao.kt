package com.example.trasparenciagov.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import io.reactivex.Single

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE uid=:id")
    fun getExpense(id: Int): Single<ExpenseEntity>

    @Delete
    fun deleteExpense(expenseEntity: ExpenseEntity): Single<Unit>

    @Insert
    fun insertExpense(expenseEntity: ExpenseEntity): Single<Unit>
}