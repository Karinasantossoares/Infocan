package com.example.trasparenciagov.repository.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.DetailsPersonEntity
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity

@Dao
interface DetailsPersonDao {

    @Query("SELECT * FROM details")
    fun getDetails () :DetailsPersonEntity

    @Delete
    fun deleteDetails(detailsPersonEntity: DetailsPersonEntity)

    @Insert
    fun insertDetails(detailsPersonEntity: DetailsPersonEntity)

}