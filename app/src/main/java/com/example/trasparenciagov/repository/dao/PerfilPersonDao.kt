package com.example.trasparenciagov.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import io.reactivex.Single


@Dao
interface PerfilPersonDao {

    @Query("SELECT * FROM perfil")
    fun getAllPolitical () : Single<List<PerfilPersonEntity>>

    @Query("SELECT * FROM perfil WHERE uid =:id ")
    fun getSinglePoliticalLocal(id:Int): Single<PerfilPersonEntity>

    @Delete
    fun deletePolitical(perfilPersonEntity: PerfilPersonEntity): Single<Unit>

    @Insert
    fun insertPolitical(perfilPersonEntity: PerfilPersonEntity): Single<Unit>

}