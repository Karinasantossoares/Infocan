package com.example.trasparenciagov.repositoryTest

import com.example.trasparenciagov.BaseViewModelTest
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.repository.dao.PerfilPersonDao
import com.example.trasparenciagov.repository.localRepository.PerfilPoliticalLocalRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.lang.Exception

class PerfilPoliticalLocalRepository: BaseViewModelTest()  {

    @Mock
    private lateinit var politicalDao: PerfilPersonDao

    private lateinit var perfilPoliticalLocal: PerfilPoliticalLocalRepository

    private val siglaUf = listOf("df", "go", "ma")
    private val numberPage = 1
    private val id = 1

    @Before
    fun init() {
        perfilPoliticalLocal = PerfilPoliticalLocalRepository(politicalDao)
    }

    @Test
    fun `when repository call getListPoliticalLocal return with success  list political`() {
        val expectedListPoliticalLocal =
            listOf(PerfilPersonEntity(1, "nome", "url", "sigla"))
        val perfilPersonResponse = expectedListPoliticalLocal.map {
            it.toPerfilPersonEntity()
        }

        whenever(politicalDao.getAllPolitical()).thenReturn(
            Single.just(expectedListPoliticalLocal)
        )

        perfilPoliticalLocal.getAllPolitical()
            .test()
            .assertResult(perfilPersonResponse)

    }

    @Test
    fun `when repository call getListPoliticalLocal return with error  messageError`() {
        val exceptionError = Exception("Erro repositório local")
        whenever(politicalDao.getAllPolitical()).thenReturn(Single.error(exceptionError))
        perfilPoliticalLocal.getAllPolitical()
            .test()
            .assertError(exceptionError)

    }

    @Test
    fun `when localRepository call getSinglePolitical return with success singlePolitical`() {
        val expectedListPoliticalLocal =
            PerfilPersonEntity(1, "nome", "url", "sigla")

        val personSingleResponse = expectedListPoliticalLocal.toPerfilPersonEntity()
        whenever(politicalDao.getSinglePoliticalLocal(id)).thenReturn(
            Single.just(
                expectedListPoliticalLocal
            )
        )
        perfilPoliticalLocal.getSinglePoliticalLocal(id)
            .test()
            .assertResult(personSingleResponse)
    }

    @Test
    fun `when repository call getSinglePoliticalLocal return with error  messageError`() {
        val exceptionError = Exception("Erro repositório chamando apenas um politico")
        whenever(politicalDao.getSinglePoliticalLocal(id)).thenReturn(Single.error(exceptionError))
        perfilPoliticalLocal.getSinglePoliticalLocal(id)
            .test()
            .assertError(exceptionError)

    }

    @Test
    fun `when localRepository call deletePoliticalLocal return with success `() {
        whenever(politicalDao.deletePolitical(getMockPerfilEntity())).thenReturn(Single.just(Unit))
        perfilPoliticalLocal.deletePolitical(getMockPerfilResponse())
            .test()
            .assertComplete()
    }

    @Test
    fun `when repository call deletePoliticalLocal return with error  messageError`() {
        val exceptionError = Exception("Erro repositório deletando apenas um politico")
        whenever(politicalDao.deletePolitical(getMockPerfilEntity())).thenReturn(
            Single.error(exceptionError)
        )
        perfilPoliticalLocal.deletePolitical(getMockPerfilResponse())
            .test()
            .assertError(exceptionError)

    }

    @Test
    fun `when localRepository call insertPoliticalLocal return with success `() {
        whenever(politicalDao.insertPolitical(getMockPerfilEntity())).thenReturn(Single.just(Unit))
        perfilPoliticalLocal.insertPolitical(getMockPerfilResponse())
            .test()
            .assertComplete()
    }

    @Test
    fun `when repository call insertPoliticalLocal return with error  messageError`() {
        val exceptionError = Exception("Erro repositório deletando apenas um politico")
        whenever(politicalDao.insertPolitical(getMockPerfilEntity())).thenReturn(
           Single.error(exceptionError)
        )
        perfilPoliticalLocal.insertPolitical(getMockPerfilResponse())
            .test()
            .assertError(exceptionError)

    }

    private fun getMockPerfilEntity() =
        PerfilPersonEntity(
            1, "nome", "url", "sigla"
        )

    private fun getMockPerfilResponse() =
        PerfilPersonResponse(
            1, "nome", "url", "sigla"
        )

}