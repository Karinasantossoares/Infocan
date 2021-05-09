package com.example.trasparenciagov.repositoryTest

import com.example.trasparenciagov.BaseViewModelTest
import com.example.trasparenciagov.RxImmediateSchedulerRule
import com.example.trasparenciagov.model.dto.*
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.repository.dao.ExpenseDao
import com.example.trasparenciagov.repository.dao.PerfilPersonDao
import com.example.trasparenciagov.repository.localRepository.ExpenseLocalRepository
import com.example.trasparenciagov.repository.localRepository.PerfilPoliticalLocalRepository
import com.example.trasparenciagov.repository.networkRepository.PerfilPoliticalNetworkRepository
import com.example.trasparenciagov.service.PoliticalService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import java.lang.Exception

class PerfilPoliticalNetworkRepositoryTest : BaseViewModelTest() {

    @Mock
    private lateinit var service: PoliticalService


    private lateinit var repositoryNetwork: PerfilPoliticalNetworkRepository



    private val siglaUf = listOf("df", "go", "ma")
    private val numberPage = 1
    private val id = 1

    @Before
    fun init() {
        repositoryNetwork = PerfilPoliticalNetworkRepository(service)
    }


    @Rule
    @JvmField
    val testRule = RxImmediateSchedulerRule()

    @Test
    fun `when repository call getListPoliticalNetwork return with success  list political`() {
        val expectedListPolitical =
            PerfilPersonResponseDTO(listOf(DetailsDateCongressManDTO(1, "name", "url", "sigla")))

        val perfilPersonResponseExpected = expectedListPolitical.toPerfilPerson()
        whenever(service.getListPolitical(siglaUf, numberPage)).thenReturn(
            Single.just(
                expectedListPolitical
            )
        )
        repositoryNetwork.getListPolitical(siglaUf, numberPage)
            .test()
            .assertResult(perfilPersonResponseExpected)

    }

    @Test
    fun `when repository call getListPoliticalNetwork return with error  messageError`() {
        val exceptionErrorNetwork = Exception("Erro repositório network")
        whenever(service.getListPolitical(siglaUf, numberPage))
            .thenReturn(Single.error(exceptionErrorNetwork))
        repositoryNetwork.getListPolitical(siglaUf, numberPage)
            .test()
            .assertError(exceptionErrorNetwork)
    }

    @Test
    fun `when repository call getDetailsPolitical return with success listDetailsPolitical`() {
        val expecetdDetails = getMockDetails()
        val expecetedResponse = expecetdDetails?.toDetailsResponse()
        whenever(service.getDetailsPolitical(id)).thenReturn(Single.just(expecetdDetails))
        repositoryNetwork.getDetailsPolitical(id)
            .test()
            .assertResult(expecetedResponse)
    }

    @Test
    fun `when repository call getDetailsPolitical return with error  messageError`() {
        val exceptionError = Exception("Erro repositório local ao buscar detalhe")
        whenever(service.getDetailsPolitical(any())).thenReturn(Single.error(exceptionError))
        repositoryNetwork.getDetailsPolitical(id)
            .test()
            .assertError(exceptionError)
    }

    @Test
    fun `when repository call getDetailsExpenseNetwork return with success  list expense`() {
        val expectedListExpenseLocal =
            DespesasResponseDTO(listOf(Details("despesas", 15f, "documento")))
        val expectedListExpense = expectedListExpenseLocal.toDespesasResponse()

        whenever(service.getDetailsExpenses(id)).thenReturn(
            Single.just(expectedListExpenseLocal)
        )
        repositoryNetwork.getDetailsExpense(id)
            .test()
            .assertResult(expectedListExpense)

    }

    @Test
    fun `when repository call getExpensePoliticalNetwork return with error  messageError`() {
        val exceptionError = Exception("Erro repositório local ao buscar despesas network")
        whenever(service.getDetailsExpenses(id)).thenReturn(Single.error(exceptionError))
        repositoryNetwork.getDetailsExpense(id)
            .test()
            .assertError(exceptionError)
    }


    private fun getMockDetails(): DetailsPersonResponseDTO? =
        (DetailsPersonResponseDTO(
            Dados(
                "11", Status(
                    "nome", "email", "url", "sigla",
                    Gabinete("telefone"), "situaçao"
                )
            )
        )
                )


}
