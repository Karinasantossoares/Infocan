package com.example.trasparenciagov.useCase

import android.content.Context
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.repository.localRepository.ExpenseLocalRepository
import com.example.trasparenciagov.repository.localRepository.PerfilPoliticalLocalRepository

import com.example.trasparenciagov.repository.networkRepository.PerfilPoliticalNetworkRepository
import io.reactivex.Single
import java.lang.Exception

class InfocanUseCase(
    private val context: Context,
    private val repository: PerfilPoliticalNetworkRepository,
    private val perfilPoliticalLocalRepository: PerfilPoliticalLocalRepository,
    private val expenseLocalRepository: ExpenseLocalRepository
) {
    fun getListPolitical(siglaUf: List<String>, numberPage: Int) =
        if (siglaUf.isNotEmpty()) {
            repository.getListPolitical(siglaUf, numberPage)
        } else {
            Single.error(Exception(context.getString(R.string.message_error_list)))
        }

    fun getDetailsPolitical(id: Int) = repository.getDetailsPolitical(id)

    fun getDetailsExpense(id: Int) = repository.getDetailsExpense(id)

    fun getPoliticalLocal() = perfilPoliticalLocalRepository.getAllPolitical()

    fun insertPoliticalLocal(political: PerfilPersonResponse) =
        perfilPoliticalLocalRepository.insertPolitical(political)

    fun deletePolicalLocal(political: PerfilPersonResponse) =
        perfilPoliticalLocalRepository.deletePolitical(political)

    fun getSinglePoliticalLocal(id: Int) = perfilPoliticalLocalRepository.getSinglePoliticalLocal(id)

    fun getExpensesLocal(id: Int) =
        expenseLocalRepository.getExpense(id)

    fun deleteExpensesLocal(despesasResponse: DespesasResponse) =
        expenseLocalRepository.deleteExpense(despesasResponse)

    fun insertExpensesLocal(despesasResponse: DespesasResponse) =
        expenseLocalRepository.insertExpense(despesasResponse)


}







