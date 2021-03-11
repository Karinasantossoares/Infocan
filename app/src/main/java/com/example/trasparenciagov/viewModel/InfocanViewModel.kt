package com.example.trasparenciagov.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.network.SendEmail
import com.example.trasparenciagov.useCase.InfocanUseCase
import io.reactivex.disposables.CompositeDisposable

class InfocanViewModel(
    private val context: Context,
    private val useCase: InfocanUseCase,
    private val preferences: AppPreferences
) : ViewModel() {


    var disposables = CompositeDisposable()
    var loadLiveData = MutableLiveData<Boolean>()
    var loadLiveDataswipe = MutableLiveData<Boolean>()
    var requestPersonPerfil = MutableLiveData<List<String>>()
    var successListPoliticalLiveData = MutableLiveData<MutableList<PerfilPersonResponse>>(
        mutableListOf()
    )
    var errorListPoliticalLiveData = MutableLiveData<String>()
    var errorLDEtailsPoliticalLiveData = MutableLiveData<String>()
    var succesDetailsPolitical = MutableLiveData<DetailsPersonResponse>()
    var selectedPoliticalLiveData = MutableLiveData<PerfilPersonResponse>()
    var successListExpense = MutableLiveData<List<DespesasResponse>>()
    var errorsListExpense = MutableLiveData<String>()
    var messageloadmore = MutableLiveData<Boolean>()
    var ufPreferencesLiveData = MutableLiveData<String>()
    var page = 1
    var sendEmailPoliticalLiveData = SingleLiveEvent<SendEmail>()

    fun showUfPreferences() {
        ufPreferencesLiveData.value = preferences.getStringKey(AppPreferences.UF)
    }

    fun getFirstListPolitical(siglaUf: List<String>) {
        page = 1
        successListPoliticalLiveData.value?.clear()
        getListPolitical(siglaUf)
    }

    fun getListPolitical(siglaUf: List<String>) {
        loadLiveData.value = true
        requestPersonPerfil.value = siglaUf
        requestPersonPerfil.value?.let { uf ->
            uf.map {
                preferences.saveListStringKey(AppPreferences.UF, it)
                disposables.addAll(useCase.getListPolitical(uf, page).subscribe { res, error ->
                    if (!res.isNullOrEmpty()) {
                        page++
                        messageloadmore.value = true
                        successListPoliticalLiveData.value?.let { currentList ->
                            currentList.addAll(res)
                            successListPoliticalLiveData.value = currentList
                        }

                        loadLiveData.value = false

                    } else {
                        messageloadmore.value = false
                        loadLiveData.value = false
                        errorListPoliticalLiveData.value = error.localizedMessage
                    }
                })
            }
        }
    }


    fun getDetailsPolitical() {
        loadLiveData.value = true
        selectedPoliticalLiveData.value?.let {
            it.id.let { id ->
                disposables.addAll(useCase.getDetailsPolitical(id).subscribe { res, error ->
                    if (res != null) {
                        loadLiveData.value = false
                        succesDetailsPolitical.value = res
                    } else {
                        loadLiveData.value = false
                        errorLDEtailsPoliticalLiveData.value = error.localizedMessage
                    }
                })
            }
        }
    }


    fun getDetailsExpense() {
        loadLiveData.value = true
        val id = selectedPoliticalLiveData.value?.id
        id?.let {
            disposables.addAll(useCase.getDetailsExpense(id).subscribe { res, error ->
                if (res != null) {
                    loadLiveData.value = false
                    successListExpense.value = res
                } else {
                    loadLiveData.value = false
                    errorsListExpense.value = context.getString(R.string.messsage_error_expense)
                }
            })
        }

    }

    fun selectedPolitical(perfil: PerfilPersonResponse) {
        selectedPoliticalLiveData.value = perfil
    }

    fun clearSelecetedLiveData() {
        selectedPoliticalLiveData = MutableLiveData()
    }

    fun clearLiveDatas() {
        loadLiveData = MutableLiveData()
    }

    fun sendEmail() {
        succesDetailsPolitical.value?.let {
          sendEmailPoliticalLiveData.value =   SendEmail(email = it.email)
        }
    }

}

