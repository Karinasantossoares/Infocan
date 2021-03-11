package com.example.trasparenciagov.model.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PerfilPersonResponse(
    val id: Int=0,
    val nome: String,
    val urlFoto: String,
    val siglaPartido: String
) :Parcelable