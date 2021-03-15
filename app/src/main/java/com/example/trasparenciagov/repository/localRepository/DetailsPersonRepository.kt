package com.example.trasparenciagov.repository.localRepository

import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.model.persistencesRoom.DetailsPersonEntity
import com.example.trasparenciagov.repository.DAO.DetailsPersonDao
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io

class DetailsPersonRepository(
    private val detailsPersonDao: DetailsPersonDao
) {

    fun getDetails(id: Int) = Single.fromCallable {
        detailsPersonDao.getDetails(id).map {
            DetailsPersonResponse(
                it.nome,
                it.email,
                it.dataNascimento,
                it.siglaPartido,
                it.siglaPartido,
                it.situacao,
                it.urlFoto
            )
        }
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun deleteDeatils(details: DetailsPersonResponse) = Single.fromCallable {
        detailsPersonDao.deleteDetails(
            DetailsPersonEntity(
                nome = details.nome,
                email = details.email,
                urlFoto = details.urlFoto,
                siglaPartido = details.siglaPartido,
                situacao = details.situacao,
                dataNascimento = details.dataNascimento,
                telefone = details.telefone
            )
        )
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertDetails(details: DetailsPersonResponse) =
        Single.fromCallable {
            detailsPersonDao.insertDetails(
                DetailsPersonEntity(
                    nome = details.nome,
                    email = details.email,
                    urlFoto = details.urlFoto,
                    siglaPartido = details.siglaPartido,
                    situacao = details.situacao,
                    dataNascimento = details.dataNascimento,
                    telefone = details.telefone
                )
            )
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}