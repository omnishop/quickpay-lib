package com.bda.quickpay_lib.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileByUIDRequest(
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("fptplay_id") val fptplay_id: String
) : Parcelable
