package com.example.trasparenciagov.repository.networkRepository


import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.service.PoliticalService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PerfilPoliticalNetworkRepository(private val service: PoliticalService) {

    fun getListPolitical(siglaUf: List<String>, numberPage: Int) =
        service.getListPolitical(siglaUf, numberPage)
            .map { perfilPersonResponseDTO ->
                perfilPersonResponseDTO.toPerfilPerson()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getDetailsPolitical(id: Int) =
        service.getDetailsPolitical(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).map { detailsPersonResponseDTO ->
                detailsPersonResponseDTO.dados.run {
                    DetailsPersonResponse(
                        email = this.ultimoStatus.email,
                        dataNascimento = dataNascimento,
                        telefone = this.ultimoStatus.gabinete.telefone,
                        situacao = this.ultimoStatus.situacao,
                    )
                }
            }

    fun getDetailsExpense(id: Int) =
        service.getDetailsExpenses(id).map {
            it.toDespesasResponse()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}









