package com.example.trasparenciagov.useCase

import android.content.Context
import com.example.trasparenciagov.R

import com.example.trasparenciagov.repository.networkRepository.InfocanRepository
import io.reactivex.Single
import java.lang.Exception

class InfocanUseCase(
    private val context: Context,
    private val repository: InfocanRepository
) {
    fun getListPolitical(siglaUf: List<String>,numberPage:Int) =
        if (siglaUf.isNotEmpty()) {
            repository.getListPolitical(siglaUf,numberPage)
        } else {
            Single.error(Exception(context.getString(R.string.message_error_list)))
        }


    fun getDetailsPolitical(id: Int) = repository.getDetailsPolitical(id)

    fun getDetailsExpense(id: Int) = repository.getDetailsExpense(id)
}







