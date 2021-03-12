package com.example.trasparenciagov.repository.localRepository

import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.repository.DAO.PerfilPersonDao
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PerfilPoliticalRepository(
    private val perfilPersonDao: PerfilPersonDao
) {

    fun getAllPolitical() = perfilPersonDao.getAllPolitical().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).map { listPerfilEntity ->
            listPerfilEntity.map {
                PerfilPersonResponse(it.uid, it.nome, it.urlFoto, it.siglaPartido)
            }
        }

    fun deletePolitical(political: PerfilPersonResponse) =
        Single.fromCallable {
            perfilPersonDao.deletePolitical(
                PerfilPersonEntity(
                    political.id,
                    political.nome,
                    political.urlFoto,
                    political.siglaPartido
                )
            )
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun insertPolitical(political: PerfilPersonResponse) =
        Single.fromCallable {
            perfilPersonDao.insertPolitical(
                PerfilPersonEntity(
                    political.id,
                    political.nome,
                    political.urlFoto,
                    political.siglaPartido
                )
            )
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}