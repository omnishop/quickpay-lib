package com.bda.quickpay_lib.ui.detailVoucher

import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.base.BaseView


class DetailVoucherContract {
    interface View {
    }

    interface Presenter {
        fun disposeAPI()
    }
}