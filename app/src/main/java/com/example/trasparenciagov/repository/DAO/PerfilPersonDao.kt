package com.example.trasparenciagov.repository.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity



@Dao
interface PerfilPersonDao {

    @Query("SELECT * FROM perfil")
    fun getAllPolitical () :List<PerfilPersonEntity>

    @Delete
    fun deletePolitical(perfilPersonEntity: PerfilPersonEntity)

    @Insert
    fun insertPolitical(perfilPersonEntity: PerfilPersonEntity)

}