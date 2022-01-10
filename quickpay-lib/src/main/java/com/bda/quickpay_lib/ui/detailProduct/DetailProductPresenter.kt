package com.bda.quickpay_lib.ui.detailProduct

import android.content.Context
import com.bda.quickpay_lib.api.APIManager
import com.bda.quickpay_lib.models.request.ProductByUiRequest
import com.bda.quickpay_lib.models.request.ProfileByUIDRequest
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.utils.QuickPayUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailProductPresenter(view: DetailProductContract.View, context: Context) :
    DetailProductContract.Presenter {
    private var mView: DetailProductContract.View = view
    private var mSubscription: Disposable? = null

    override fun disposeAPI() {
        if (mSubscription != null && !mSubscription!!.isDisposed) {
            mSubscription!!.dispose()
        }
    }

    override fun getProfile(mFptId: String, phone: String) {
        mSubscription = APIManager.getInstance().getApi().getProfile(
            ProfileByUIDRequest(
                phone_number = phone,
                fptplay_id = mFptId
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                if (it.status == 200) {
                    mView.sendProfileSuccess(it)
                } else {
                    val response = CheckCustomerResponse()
                    response.data.uid = QuickPayUtils.aliasCustomerId
                    mView.sendProfileSuccess(response)
                }
            }, { err ->
                val response = CheckCustomerResponse()
                response.data.uid = QuickPayUtils.aliasCustomerId
                mView.sendProfileSuccess(response)
            }, {
            })
    }

    override fun getProduct(mProductId: String) {
        mView.showProgress()
        mSubscription = APIManager.getInstance().getApi().getProductFromId(
            ProductByUiRequest(
                arrayListOf(mProductId)
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                if (it.status == 200 && !it.data.isNullOrEmpty()) {
                    mView.sendProductSuccess(it.data[0])
                }
            }, { err ->
                mView.hideProgress()
                mView.handleError(err)
            }, {
                mView.hideProgress()
            })
    }
}