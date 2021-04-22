package com.example.trasparenciagov.repository.localRepository

import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.repository.dao.PerfilPersonDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PerfilPoliticalRepository(
    private val perfilPersonDao: PerfilPersonDao
) {

    fun getAllPolitical() = perfilPersonDao.getAllPolitical()
        .map { listPerfilEntity ->
            listPerfilEntity.map {
                PerfilPersonResponse(it.uid, it.nome, it.urlFoto, it.siglaPartido, it.detail)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


    fun getSinglePoliticalLocal(id: Int) = perfilPersonDao.getSinglePoliticalLocal(id).map {
        PerfilPersonResponse(it.uid, it.nome, it.urlFoto, it.siglaPartido, it.detail)
    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


    fun deletePolitical(political: PerfilPersonResponse) =
        perfilPersonDao.deletePolitical(
            PerfilPersonEntity(
                political.id,
                political.nome,
                political.urlFoto,
                political.siglaPartido,
                political.detail
            )
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertPolitical(political: PerfilPersonResponse) =
        perfilPersonDao.insertPolitical(
            PerfilPersonEntity(
                political.id,
                political.nome,
                political.urlFoto,
                political.siglaPartido,
                political.detail
            )
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


}