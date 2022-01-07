package com.bda.quickpay_lib

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.bda.quickpay_lib.ui.detailVoucher.DetailVoucherFragment
import com.bda.quickpay_lib.ui.main.MainQuickPayDialog
import com.bda.quickpay_lib.utils.QuickPayUtils

class QuickPayDialog(builder: Builder) {
    private var mUserId: String = builder.userId
    private var mUserPhone: String = builder.userPhone
    private var mProductID: String = builder.productId
    private var mVoucherID: String = builder.voucherId
    private var mPlatform: String = builder.platform
    private var mListener: QuickPayListener? = builder.listener
    private var mXApiKey: String = builder.xApiKey
    private var mIsDarkMode: Boolean = builder.isDardMode
    private var mIsAppProduction: Boolean = builder.isAppProduction
    private var mXApiKeyTracking: String = builder.xApiKeyTracking
    private var quickPayDialog: MainQuickPayDialog? = null
    private var voucherDialog: DetailVoucherFragment? = null

    class Builder() {
        var userId: String = ""
        var isDardMode: Boolean = false
        var userPhone: String = ""
        var productId: String = ""
        var voucherId: String = ""
        var platform: String = ""
        var xApiKey: String = ""
        var xApiKeyTracking: String = ""
        var isAppProduction: Boolean = true
        var listener: QuickPayListener? = null

        fun setUserId(mUserId: String): Builder {
            this.userId = mUserId
            return this
        }

        fun setIsAppProduction(mIsAppProduction: Boolean): Builder {
            this.isAppProduction = mIsAppProduction
            return this
        }

        fun setUserPhone(mUserPhone: String): Builder {
            this.userPhone = mUserPhone
            return this
        }

        fun setProductId(mProductId: String): Builder {
            this.productId = mProductId
            return this
        }

        fun setVoucherId(mVoucherId: String): Builder {
            this.voucherId = mVoucherId
            return this
        }

        fun setPlatform(mPlatform: String): Builder {
            this.platform = mPlatform
            return this
        }

        fun setXApiKey(mXApiKey: String): Builder {
            this.xApiKey = mXApiKey
            return this
        }

        fun setXApiKeyTracking(mXApiKeyTracking: String): Builder {
            this.xApiKeyTracking = mXApiKeyTracking
            return this
        }

        fun setOnQuickPayListener(mListener: QuickPayListener): Builder {
            this.listener = mListener
            return this
        }

        fun setDarkMode(mIsDarkMode: Boolean): Builder {
            this.isDardMode = mIsDarkMode
            return this
        }

        fun build(): QuickPayDialog {
            return QuickPayDialog(this)
        }
    }

    fun isPopupShowing(): Boolean {
        if (quickPayDialog != null) {
            return quickPayDialog!!.isDialogShowing()
        } else {
            return false
        }
    }

    fun show(mActivity: Activity, fragmentManager: FragmentManager) {
        QuickPayUtils.initQuickPay(
            platform = mPlatform,
            xApiKeyTracking = mXApiKeyTracking,
            xApiKey = mXApiKey,
            isProductionEnv = mIsAppProduction,
            isDarkMode = mIsDarkMode
        )
        quickPayDialog = MainQuickPayDialog(
            activity = mActivity,
            fptId = mUserId,
            phone = mUserPhone,
            productId = mProductID,
            voucherId = mVoucherID,
            platform = mPlatform,
            {
                mListener?.onQuickPayExit()
            }
        )
        quickPayDialog?.show(fragmentManager, quickPayDialog?.tag)
    }

    fun stop() {
        quickPayDialog?.dismiss()
        quickPayDialog?.dismiss()

    }

    interface QuickPayListener {
        fun onQuickPayExit()
    }
}