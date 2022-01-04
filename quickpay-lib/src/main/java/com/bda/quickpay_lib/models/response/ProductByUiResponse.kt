package com.bda.quickpay_lib.models.response

import android.os.Parcelable
import com.bda.quickpay_lib.models.Product
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductByUiResponse(
    @SerializedName("statusCode") val status: Int,
    @SerializedName("data") val data: ArrayList<Product>
) : Parcelable