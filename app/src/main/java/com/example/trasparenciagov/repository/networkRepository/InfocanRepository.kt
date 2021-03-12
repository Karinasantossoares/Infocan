package com.example.trasparenciagov.repository.networkRepository


import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.service.InfocanService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class InfocanRepository(private val service: InfocanService) {

    fun getListPolitical(siglaUf: List<String>, numberPage: Int) =
        service.getListPolitical(siglaUf, numberPage)
            .map { perfilPersonResponseDTO ->
                perfilPersonResponseDTO.dados.map {
                    PerfilPersonResponse(
                        id = it.id,
                        nome = it.nome,
                        urlFoto = it.urlFoto,
                        siglaPartido = it.siglaPartido
                    )
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun getDetailsPolitical(id: Int) =
        service.getDetailsPolitical(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).map { detailsPersonResponseDTO ->
                detailsPersonResponseDTO.dados.run {
                    DetailsPersonResponse(
                        nome = this.ultimoStatus.nome,
                        email = this.ultimoStatus.email,
                        dataNascimento = dataNascimento,
                        siglaPartido = this.ultimoStatus.siglaPartido,
                        telefone = this.ultimoStatus.gabinete.telefone,
                        situacao = this.ultimoStatus.situacao,
                        urlFoto = this.ultimoStatus.urlFoto
                    )
                }
            }

    fun getDetailsExpense(id: Int) =
        service.getDetailsExpenses(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).map {
                it.dados.map { details ->
                    DespesasResponse(
                        tipoDespesa = details.tipoDespesa,
                        valorDocumento = details.valorDocumento,
                        dataDocumento = details.dataDocumento
                    )
                }
            }


}









