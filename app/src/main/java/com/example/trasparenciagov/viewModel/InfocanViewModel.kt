package com.example.trasparenciagov.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.network.SendEmail
import com.example.trasparenciagov.useCase.InfocanUseCase
import io.reactivex.disposables.CompositeDisposable

class InfocanViewModel(
    private val context: Context,
    private val useCase: InfocanUseCase,
    private val preferences: AppPreferences,
) : ViewModel() {


    private var isLoadedPoliticalsLocal: Boolean = true
    var disposables = CompositeDisposable()
    var loadLiveData = MutableLiveData<Boolean>()
    var refreshListPoliticalLiveData = MutableLiveData<MutableList<PerfilPersonResponse>>(
        mutableListOf()
    )

    private var selectedPoliticalLiveData = MutableLiveData<PerfilPersonResponse>()
    val selectedPolitical: LiveData<PerfilPersonResponse>
        get() = selectedPoliticalLiveData


    var successListExpense = MutableLiveData<List<DespesasResponse>>()
    var messageloadmore = MutableLiveData<Boolean>()
    var ufPreferencesLiveData = MutableLiveData<String>()
    var sendEmailPoliticalLiveData = SingleLiveEvent<SendEmail>()
    var messageSuccessDeletePoliticalLiveData = MutableLiveData<String>()
    var messageSuccesInsertPoliticalLiveData = MutableLiveData<String>()
    var messageErrorLiveData = MutableLiveData<String>()
    var setTextSaveorRemoveLiveData = MutableLiveData<String>()
    var setTitleSaveOrResultMembers = MutableLiveData<String>()
    var swipeRefreshLiveData = MutableLiveData<Boolean>()
    var errorVerifyItemSave = MutableLiveData<String>()
    var page = 1

    fun getPoliticals() {
        if (isLoadedPoliticalsLocal) getAllLocalPoliticals()
    }


    fun showUfPreferences() {
        ufPreferencesLiveData.value = preferences.getStringKey(AppPreferences.UF)
    }

    fun getFirstListPolitical(siglaUf: List<String>) {
        page = 1
        refreshListPoliticalLiveData.value?.clear()
        getNetworkPoliticals(siglaUf)
    }

    fun getNetworkPoliticals(states: List<String>) {
        this.isLoadedPoliticalsLocal = false
        setTitleSaveOrResultMembers.value = context.getString(R.string.message_item_results)
        loadLiveData.value = true
        states.map {
            preferences.saveListStringKey(AppPreferences.UF, it)
            disposables.add(useCase.getListPolitical(states, page).subscribe { res, _ ->
                if (!res.isNullOrEmpty()) {
                    page++
                    messageloadmore.value = true
                    refreshListPoliticalLiveData.value?.let { currentList ->
                        currentList.addAll(res)
                        refreshListPoliticalLiveData.value = currentList
                    }
                    loadLiveData.value = false

                } else {
                    loadLiveData.value = false
                    messageloadmore.value = false
                    messageErrorLiveData.value =
                        context.getString(R.string.error_list_isEmpty)
                }
            })
        }
    }


    fun getDetailsPolitical() {
        loadLiveData.value = true
        selectedPoliticalLiveData.value?.let {
            it.id.let { id ->
                disposables.addAll(useCase.getDetailsPolitical(id).subscribe { res, error ->
                    if (res != null) {
                        loadLiveData.value = false
                        selectedPoliticalLiveData.value?.detail = res
                        selectedPoliticalLiveData.value = selectedPoliticalLiveData.value
                    } else {
                        loadLiveData.value = false
                        messageErrorLiveData.value = error.localizedMessage
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
                    messageErrorLiveData.value = context.getString(R.string.messsage_error_expense)
                }
            })
        }

    }


    fun getAllLocalPoliticals() {
        loadLiveData.value = true
        swipeRefreshLiveData.value = true
        this.isLoadedPoliticalsLocal = true
        disposables.addAll(useCase.getPoliticalLocal().subscribe { res, _ ->
            if (res != null) {
                if (res.isNotEmpty()) {
                    setTitleSaveOrResultMembers.value =
                        context.getString(R.string.message_item_save)
                    messageloadmore.value = false
                } else {
                    setTitleSaveOrResultMembers.value =
                        context.getString(R.string.message_item_results)
                }
                loadLiveData.value = false
                swipeRefreshLiveData.value = false
                refreshListPoliticalLiveData.value = res.toMutableList()
            } else {
                swipeRefreshLiveData.value = false
                loadLiveData.value = false
                messageErrorLiveData.value = context.getString(R.string.message_error_list_local)
            }
        })
    }

    private fun deletePoliticalLocal() {
        loadLiveData.value = true
        selectedPoliticalLiveData.value?.let {
            disposables.addAll(useCase.deletePolicalLocal(it).subscribe { res, _ ->
                if (res != null) {
                    loadLiveData.value = false
                    messageSuccessDeletePoliticalLiveData.value =
                        context.getString(R.string.message_delete_political_ok)
                    verifyStatusSavedUser()

                } else {
                    messageErrorLiveData.value =
                        context.getString(R.string.message_error_delete_political)
                    loadLiveData.value = false
                }
            })
        }
    }

    private fun savePoliticalLocal() {
        loadLiveData.value = true
        selectedPoliticalLiveData.value?.let {
            disposables.add(useCase.insertPoliticalLocal(it).subscribe { _, error ->
                if (error == null) {
                    loadLiveData.value = false
                    verifyStatusSavedUser()
                    messageSuccesInsertPoliticalLiveData.value =
                        context.getString(R.string.message_success_insert_political)
                } else {
                    loadLiveData.value = false
                    messageErrorLiveData.value = context.getString(R.string.error_save_political)
                }
            })
        }
    }

    fun saveOrDeletePolitical() {
        selectedPoliticalLiveData.value?.id?.let {
            disposables.add(useCase.getSinglePoliticalLocal(it).subscribe { res, error ->
                if (res != null) {
                    deletePoliticalLocal()
                } else {
                    savePoliticalLocal()
                }
            })
        }
    }

    fun verifyStatusSavedUser() {
        selectedPoliticalLiveData.value?.let {
            disposables.add(useCase.getSinglePoliticalLocal(it.id).subscribe { res, _ ->
                if (res != null) {
                    setTextSaveorRemoveLiveData.value =
                        context.getString(R.string.message_set_text_remove)
                } else {
                    setTextSaveorRemoveLiveData.value = context.getString(R.string.message_save_ok)
                }

            })
        }
    }

//    fun insertDetailsLocal(){
//        selecetDetailsPoliticalLiveData.value?.let {
//            disposables.add(useCase.insertPoliticalLocal().subscribe { _,error-> })
//        }
//    }


    fun selectedPolitical(perfil: PerfilPersonResponse) {
        selectedPoliticalLiveData.value = perfil
    }

    fun clearSelecetedLiveData() {
        selectedPoliticalLiveData = MutableLiveData()
    }

    fun clearLiveDatas() {
        loadLiveData = MutableLiveData()
        messageSuccesInsertPoliticalLiveData = MutableLiveData()
        messageSuccessDeletePoliticalLiveData = MutableLiveData()
        messageErrorLiveData = MutableLiveData()
    }

    fun sendEmail() {
        selectedPoliticalLiveData.value?.detail?.let {
            sendEmailPoliticalLiveData.value = SendEmail(email = it.email)
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }


}

