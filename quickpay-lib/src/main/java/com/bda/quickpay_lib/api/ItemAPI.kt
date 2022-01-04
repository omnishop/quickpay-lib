package com.bda.quickpay_lib.api

import com.bda.quickpay_lib.models.request.PaymentRequest
import com.bda.quickpay_lib.models.request.ProductByUiRequest
import com.bda.quickpay_lib.models.request.ProfileByUIDRequest
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.models.response.MomoPaymentResponce
import com.bda.quickpay_lib.models.response.ProductByUiResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface ItemAPI {

    @POST("list/products/uids")
    fun getProductFromId(@Body request: ProductByUiRequest): Observable<ProductByUiResponse>

    @POST("checkCustomer_v2")
    fun getProfile(@Body request: ProfileByUIDRequest): Observable<CheckCustomerResponse>

    @POST("submitOrder")
    fun submitOrder(@Body request: PaymentRequest): Observable<MomoPaymentResponce>
}