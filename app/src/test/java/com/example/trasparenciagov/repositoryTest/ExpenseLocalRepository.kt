package com.example.trasparenciagov.repositoryTest

import com.example.trasparenciagov.BaseViewModelTest
import com.example.trasparenciagov.model.persistencesRoom.ExpenseEntity
import com.example.trasparenciagov.repository.dao.ExpenseDao
import com.example.trasparenciagov.repository.localRepository.ExpenseLocalRepository
import com.example.trasparenciagov.repository.networkRepository.PerfilPoliticalNetworkRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ExpenseLocalRepository : BaseViewModelTest() {

    @Mock
    private lateinit var expenseDao: ExpenseDao

    private lateinit var repositoryExpenseLocal: ExpenseLocalRepository

    private val id = 1

    @Before
    fun init() {
        repositoryExpenseLocal=ExpenseLocalRepository(expenseDao)
    }

    @Test
    fun `when repository call getExpense return with success  list expense`() {
        val expenseEntity =
            ExpenseEntity(1,"tal",15f,"lskkl")
        val expectedDespesasResponse = expenseEntity.toExpenseResponse()

        whenever(expenseDao.getExpense(id)).thenReturn(
            Single.just(expenseEntity)
        )

        repositoryExpenseLocal.getExpense(any())
            .test()
            .assertResult(expectedDespesasResponse)
    }

    @Test
    fun `when repository call getExpense return with error  list expense`() {
        val expenseEntity =
            ExpenseEntity(1,"tal",15f,"lskkl")
        val expectedDespesasResponse = expenseEntity.toExpenseResponse()

        whenever(expenseDao.getExpense(id)).thenReturn(
            Single.just(expenseEntity)
        )

        repositoryExpenseLocal.getExpense(any())
            .test()
            .assertResult(expectedDespesasResponse)
    }
}