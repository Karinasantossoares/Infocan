package com.example.trasparenciagov.repository.localRepository

import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import com.example.trasparenciagov.repository.dao.ExpenseDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    fun getExpense(id: Int) = expenseDao.getExpense(id).map {
        DespesasResponse(it.tipoDespesa, it.valorDocumento, it.tipoDespesa)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


    fun deleteExpense(despesas: DespesasResponse) =
            expenseDao.deleteExpense(
                ExpenseEntity(
                    tipoDespesa = despesas.tipoDespesa,
                    valorDocumento = despesas.valorDocumento,
                    dataDocumento = despesas.dataDocumento
                )
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertExpense(despesas: DespesasResponse) =
            expenseDao.insertExpense(
                ExpenseEntity(
                    tipoDespesa = despesas.tipoDespesa,
                    valorDocumento = despesas.valorDocumento,
                    dataDocumento = despesas.dataDocumento
                )
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}