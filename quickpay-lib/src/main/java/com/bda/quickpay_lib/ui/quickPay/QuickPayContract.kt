package com.bda.quickpay_lib.ui.quickPay

import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.ui.base.BaseView


class QuickPayContract {
    /**
     * Represents the View in MVP.
     */
    interface View: BaseView {
        fun showProgress()
        fun hideProgress()
        fun sendOrderSuccess(orderId: String)
        fun sendFailed(message: String)
    }

    interface Presenter {
        fun disposeAPI()
        fun submitOrder(
            customerId: String,
            name: String,
            phone: String,
            voucherCode: String,
            voucherId: String,
            requestType: Int,
            list: ArrayList<Product>
        )
    }
}