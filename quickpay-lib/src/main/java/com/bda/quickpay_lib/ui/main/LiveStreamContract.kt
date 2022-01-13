package com.bda.quickpay_lib.ui.main

import com.bda.quickpay_lib.models.LiveStream
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.request.FavouriteRequest
import com.bda.quickpay_lib.models.response.CheckCustomerResponse

class LiveStreamContract {
    /**
     * Represents the View in MVP.
     */
    interface View {
        fun sendSuccess(liveStream: LiveStream)
        fun sendFalsed(error: String)
        fun sendAddressSuccess(data: CheckCustomerResponse)
        fun sendAddressFailed(message: String)
        fun finishThis(order_Id: String)
        fun sendFailedOrder(message: String)
        fun seekToProductTime(time: Int)
    }

    /**
     * Represents the Presenter in MVP.
     */
    interface Presenter {
        fun loadPresenter(liveStreamID: String)
        fun disposeAPI()
        fun postAddFavourite(request: FavouriteRequest)
        fun postDeleteFavourite(request: FavouriteRequest)
        fun fetchProfile(userInfo: CheckCustomerResponse?)
        fun updateOrder(
            data: CheckCustomerResponse, product: Product,
            voucherCode: String, voucherId: String
        )
    }
}