package com.example.trasparenciagov.model.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailsPersonResponse(
    val email: String,
    val dataNascimento: String,
    val telefone: String,
    val situacao: String
): Parcelable

