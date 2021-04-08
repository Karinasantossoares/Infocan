package com.example.trasparenciagov.useCase

import android.content.Context
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.repository.localRepository.DetailsPersonRepository
import com.example.trasparenciagov.repository.localRepository.ExpenseRepository
import com.example.trasparenciagov.repository.localRepository.PerfilPoliticalRepository

import com.example.trasparenciagov.repository.networkRepository.InfocanRepository
import io.reactivex.Single
import java.lang.Exception

class InfocanUseCase(
    private val context: Context,
    private val repository: InfocanRepository,
    private val perfilPoliticalRepository: PerfilPoliticalRepository,
    private val detailsPersonRepository: DetailsPersonRepository,
    private val expenseRepository: ExpenseRepository
) {
    fun getListPolitical(siglaUf: List<String>, numberPage: Int) =
        if (siglaUf.isNotEmpty()) {
            repository.getListPolitical(siglaUf, numberPage)
        } else {
            Single.error(Exception(context.getString(R.string.message_error_list)))
        }

    fun getDetailsPolitical(id: Int) = repository.getDetailsPolitical(id)

    fun getDetailsExpense(id: Int) = repository.getDetailsExpense(id)

    fun getPoliticalLocal() = perfilPoliticalRepository.getAllPolitical()

    fun insertPoliticalLocal(political: PerfilPersonResponse) =
        perfilPoliticalRepository.insertPolitical(political)

    fun deletePolicalLocal(political: PerfilPersonResponse) =
        perfilPoliticalRepository.deletePolitical(political)

    fun getDetailsLocal(id: Int) = detailsPersonRepository.getDetails(id)

    fun getSinglePoliticalLocal(id: Int) =perfilPoliticalRepository.getSinglePoliticalLocal(id)

    fun deleteDeatislsLocal(detailsPersonResponse: DetailsPersonResponse) =
        detailsPersonRepository.deleteDeatils(detailsPersonResponse)

    fun insertDetailsLocal(detailsPersonResponse: DetailsPersonResponse) =
        detailsPersonRepository.deleteDeatils(detailsPersonResponse)

    fun getExpensesLocal(id: Int) =
        expenseRepository.getExpense(id)

    fun deleteExpensesLocal(despesasResponse: DespesasResponse) =
        expenseRepository.deleteExpense(despesasResponse)

    fun insertExpensesLocal(despesasResponse: DespesasResponse) =
        expenseRepository.insertExpense(despesasResponse)


}







