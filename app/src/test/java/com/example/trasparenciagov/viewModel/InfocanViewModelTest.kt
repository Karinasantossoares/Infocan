package com.example.trasparenciagov.viewModel.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.trasparenciagov.BaseViewModelTest
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.useCase.InfocanUseCase
import com.example.trasparenciagov.viewModel.InfocanViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class InfocanViewModelTest :BaseViewModelTest() {


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

    private val viewModel by lazy {
        InfocanViewModel(
            context,
            useCase,
            preferences
        )
    }

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun prepare() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun init() {
        whenever(viewModel.loadLiveData)
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
        whenever(useCase.getListPolitical(any(), any())).thenReturn(
            Single.error(
                Exception(
                    errorMessage
                )
            )
        )
        viewModel.messageErrorLiveData.observeForever(errorListPoliticalLiveData)

        //Chamar
        viewModel.getNetworkPoliticals(any())

        //Verificar
        verify(errorListPoliticalLiveData).onChanged(errorMessage)
    }
}