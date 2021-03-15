package com.example.trasparenciagov.repository.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trasparenciagov.model.persistencesRoom.DetailsPersonEntity
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import io.reactivex.Single

@Dao
interface DetailsPersonDao {

    @Query("SELECT * FROM details WHERE uid =:id")
    fun getDetails (id:Int) : Single<DetailsPersonEntity>

    @Delete
    fun deleteDetails(detailsPersonEntity: DetailsPersonEntity)

    @Insert
    fun insertDetails(detailsPersonEntity: DetailsPersonEntity)

}