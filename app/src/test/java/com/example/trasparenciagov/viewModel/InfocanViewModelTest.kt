package com.example.trasparenciagov.viewModel

import android.content.Context
import androidx.lifecycle.Observer
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.useCase.InfocanUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class InfocanViewModelTest {

    @Mock
    private lateinit var useCase: InfocanUseCase

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var preferences: AppPreferences

    @Mock
    private lateinit var successListPoliticalLiveData: Observer<List<PerfilPersonResponse>>

    @Mock
    private lateinit var errorListPoliticalLiveData: Observer<String>

    private lateinit var viewModel: InfocanViewModel

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun init() {
        viewModel = InfocanViewModel(context, useCase,preferences)
    }

    @Test
    fun `when the viewModel call getListPolitical  with success then set successListPoliticalLiveData`() {
        //Mocar
        val expected = listOf<PerfilPersonResponse>()
        whenever(useCase.getListPolitical(any(), any())).thenReturn(Single.just(expected))
        viewModel.refreshListPoliticalLiveData.observeForever(successListPoliticalLiveData)

        //Chamar
        viewModel.getNetworkPoliticals(any())

        //Verificar
        verify(successListPoliticalLiveData).onChanged(expected)
    }

    @Test
    fun `when the viewModel call getListPolitical  with error then set errorListPoliticalLiveData`() {
        //Mocar
        val errorMessage = "teste"
        whenever(useCase.getListPolitical(any(), any())).thenReturn(Single.error(Exception(errorMessage)))
        viewModel.errorListPoliticalLiveData.observeForever(errorListPoliticalLiveData)

        //Chamar
        viewModel.getNetworkPoliticals(any())

        //Verificar
        verify(errorListPoliticalLiveData).onChanged(errorMessage)
    }
}