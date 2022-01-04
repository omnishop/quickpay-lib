package com.bda.quickpay_lib.ui.quickPay

import android.content.Context
import com.bda.quickpay_lib.api.APIManager
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.request.PaymentRequest
import com.bda.quickpay_lib.utils.QuickPayUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class QuickPayPresenter(view: QuickPayContract.View, context: Context) :
    QuickPayContract.Presenter {
    private var mView: QuickPayContract.View = view

    private var mSubscription: Disposable? = null

    override fun disposeAPI() {
        if (mSubscription != null && !mSubscription!!.isDisposed) {
            mSubscription!!.dispose()
        }
    }

    override fun submitOrder(
        customerId: String,
        name: String,
        phone: String,
        voucherCode: String,
        voucherId: String,
        requestType: Int,
        products: ArrayList<Product>
    ) {
        val items = ArrayList<PaymentRequest.Item>()
        if (products.size > 0) {
            for (item in products) {
                items.add(PaymentRequest.Item(item.uid, item.order_quantity))
            }
        }

        val request = PaymentRequest(
            platform = QuickPayUtils.getPlatform(),
            payType = "quickpay",
            cid = customerId,
            voucher_code = voucherCode, voucher_uid = voucherId,
            phone = phone,
            address = "",
            province = "",
            district = "",
            items = items,
            addressType = 0,
            name = name,
            requestDeliveryTime = "all_day",
            note = "",
            create_address_type = 0,
            created_address_des = "",
            created_customer_name = name,
            created_district = "",
            created_province = "",
            created_phone_number = phone
        )
        mView.showProgress()
        mSubscription = APIManager.getInstance().getApi().submitOrder(request)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                if (it.statusCode == 200) {
                    mView.sendOrderSuccess(it.order_id)
                } else {
                    mView.sendFailed("Không thể đặt hàng")
                }
            }, { err ->
                mView.hideProgress()
                mView.handleError(err)
            }, {
                mView.hideProgress()
            })
    }
}