package com.example.trasparenciagov.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.model.network.DetailsPersonResponse
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.model.network.SendEmail
import com.example.trasparenciagov.model.persistencesRoom.PerfilPersonEntity
import com.example.trasparenciagov.useCase.InfocanUseCase
import io.reactivex.disposables.CompositeDisposable

class InfocanViewModel(
    private val context: Context,
    private val useCase: InfocanUseCase,
    private val preferences: AppPreferences,
) : ViewModel() {


    var disposables = CompositeDisposable()
    var loadLiveData = MutableLiveData<Boolean>()
    var requestPersonPerfil = MutableLiveData<List<String>>()
    var successListPoliticalLiveData = MutableLiveData<MutableList<PerfilPersonResponse>>(
        mutableListOf()
    )
    var errorListPoliticalLiveData = MutableLiveData<String>()
    var errorDetailsPoliticalLiveData = MutableLiveData<String>()
    var succesDetailsPolitical = MutableLiveData<DetailsPersonResponse>()
    var selectedPoliticalLiveData = MutableLiveData<PerfilPersonResponse>()
    var successListExpense = MutableLiveData<List<DespesasResponse>>()
    var errorsListExpense = MutableLiveData<String>()
    var messageloadmore = MutableLiveData<Boolean>()
    var ufPreferencesLiveData = MutableLiveData<String>()
    var page = 1
    var sendEmailPoliticalLiveData = SingleLiveEvent<SendEmail>()
    var messageErrorListLocal = MutableLiveData<String>()
    var messageSuccessDeletePoliticalLiveData = MutableLiveData<String>()
    var messageSuccesInsertPoliticalLiveData = MutableLiveData<String>()
    var messageErrorDeletePoliticalLiveData = MutableLiveData<String>()
    var messageErrorInsertPoliticalLiveData = MutableLiveData<String>()
    var setTextSaveorRemoveLiveData = MutableLiveData<String>()
    var setTitleSaveOrResultMembers = MutableLiveData<String>()
    var saveMembersLiveData = MutableLiveData<Unit>()
    var swipeRefreshLiveData = MutableLiveData<Boolean>()
    var verifyItemSave = MutableLiveData<PerfilPersonResponse>()
    var errorVerifyItemSave = MutableLiveData<String>()

    init {
        getPoliticalLocal()
        setTextSaveorRemoveLiveData.value = context.getString(R.string.message_save_ok)
        if (saveMembersLiveData.value == null) {
            setTextSaveorRemoveLiveData.value = context.getString(R.string.message_set_text_remove)
        }

    }


    fun showUfPreferences() {
        ufPreferencesLiveData.value = preferences.getStringKey(AppPreferences.UF)
    }

    fun getFirstListPolitical(siglaUf: List<String>) {
        page = 1
        successListPoliticalLiveData.value?.clear()
        getListPolitical(siglaUf)
    }

    fun getListPolitical(siglaUf: List<String>) {
        setTitleSaveOrResultMembers.value = context.getString(R.string.message_item_results)
        setTextSaveorRemoveLiveData.value = context.getString(R.string.message_save_ok)
        loadLiveData.value = true
        requestPersonPerfil.value = siglaUf
        requestPersonPerfil.value?.let { uf ->
            uf.map {
                preferences.saveListStringKey(AppPreferences.UF, it)
                disposables.addAll(useCase.getListPolitical(uf, page).subscribe { res, _ ->
                    if (!res.isNullOrEmpty()) {
                        page++
                        messageloadmore.value = true
                        successListPoliticalLiveData.value?.let { currentList ->
                            currentList.addAll(res)
                            successListPoliticalLiveData.value = currentList
                        }
                        loadLiveData.value = false
                        setTextSaveorRemoveLiveData.value =
                            context.getString(R.string.message_save_ok)
                    } else {
                        loadLiveData.value = false
                        messageloadmore.value = false
                        errorListPoliticalLiveData.value =
                            context.getString(R.string.error_list_isEmpty)
                        setTextSaveorRemoveLiveData.value =
                            context.getString(R.string.message_save_ok)
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
                        errorDetailsPoliticalLiveData.value = error.localizedMessage
                    }
                })
            }
        }
    }

    fun getDetailsExpense() {
        loadLiveData.value = true
        val id = selectedPoliticalLiveData.value?.id
        id?.let {
            disposables.addAll(useCase.getDetailsExpense(id).subscribe { res, _ ->
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

    fun getPoliticalLocal() {
        loadLiveData.value = true
        swipeRefreshLiveData.value = true
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
                successListPoliticalLiveData.value = res.toMutableList()
            } else {
                swipeRefreshLiveData.value = false
                loadLiveData.value = false
                messageErrorListLocal.value = context.getString(R.string.message_error_list_local)
            }
        })
    }

    fun deletePoliticalLocal() {
        loadLiveData.value = true
        selectedPoliticalLiveData.value?.let {
            disposables.addAll(useCase.deletePolicalLocal(it).subscribe { res, _ ->
                if (res != null) {
                    loadLiveData.value = false
                    messageSuccessDeletePoliticalLiveData.value =
                        context.getString(R.string.message_delete_political_ok)
                } else
                    messageErrorDeletePoliticalLiveData.value =
                    context.getString(R.string.message_error_delete_political)
                loadLiveData.value=false

            })
        }
    }

    fun savePoliticalLocal() {
        loadLiveData.value = true
        setTextSaveorRemoveLiveData.value = context.getString(R.string.message_save_ok)
        selectedPoliticalLiveData.value?.let {
            disposables.addAll(useCase.insertPoliticalLocal(it).subscribe { res, _ ->
                if (res != null) {
                    loadLiveData.value = false
                    setTextSaveorRemoveLiveData.value =
                        context.getString(R.string.message_set_text_remove)
                    messageSuccesInsertPoliticalLiveData.value =
                        context.getString(R.string.message_success_insert_political)
                } else {
                    loadLiveData.value = false
                    messageErrorInsertPoliticalLiveData.value =
                        context.getString(R.string.error_save_political)
                }
            })
        }
    }

    fun getSinglePoliticalLocal() {
        selectedPoliticalLiveData.value?.let {
            disposables.addAll(useCase.getSinglePoliticalLocal(it.id).subscribe { res, error ->
                if (res != null) {
                    deletePoliticalLocal()
                } else {
                    errorVerifyItemSave.value = error.localizedMessage
                    savePoliticalLocal()
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
        messageErrorInsertPoliticalLiveData = MutableLiveData()
        messageSuccesInsertPoliticalLiveData = MutableLiveData()
        errorListPoliticalLiveData = MutableLiveData()
        messageSuccessDeletePoliticalLiveData = MutableLiveData()
        messageErrorDeletePoliticalLiveData= MutableLiveData()
    }

    fun sendEmail() {
        succesDetailsPolitical.value?.let {
            sendEmailPoliticalLiveData.value = SendEmail(email = it.email)
        }
    }


}

