package com.bda.quickpay_lib.ui.detailVoucher

import android.content.Context
import com.bda.quickpay_lib.api.APIManager
import com.bda.quickpay_lib.models.request.ProductByUiRequest
import com.bda.quickpay_lib.models.request.ProfileByUIDRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailVoucherPresenter(view: DetailVoucherContract.View, context: Context) :
    DetailVoucherContract.Presenter {
    private var mView: DetailVoucherContract.View = view
    private var mSubscription: Disposable? = null

    override fun disposeAPI() {
        if (mSubscription != null && !mSubscription!!.isDisposed) {
            mSubscription!!.dispose()
        }
    }
}