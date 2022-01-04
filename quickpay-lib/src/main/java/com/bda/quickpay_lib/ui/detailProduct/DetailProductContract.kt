package com.bda.quickpay_lib.ui.detailProduct

import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.base.BaseView


class DetailProductContract {
    interface View : BaseView{
        fun sendProductSuccess(response: Product)
        fun sendProfileSuccess(response: CheckCustomerResponse)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {
        fun disposeAPI()
        fun getProfile(mFptId: String, phone: String)
        fun getProduct(mProductId: String)
    }
}