package com.bda.quickpay_lib.ui.paymentMethod

import com.bda.quickpay_lib.models.Product


class PaymentMethodContract {
    /**
     * Represents the View in MVP.
     */
    interface View {
        fun sendOrderSuccess(orderId: String)
        fun sendFailed(message: String)
    }

    /**
     * Represents the Presenter in MVP.
     */
    interface Presenter {
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