package com.bda.quickpay_lib

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.bda.quickpay_lib.ui.main.MainQuickPayDialog
import com.bda.quickpay_lib.utils.QuickPayUtils

class QuickPayDialog(builder: Builder) {
    private var mUserId: String = builder.userId
    private var mUserPhone: String = builder.userPhone
    private var mProductID: String = builder.productId
    private var mPlatform: String = builder.platform
    private var mListener: QuickPayListener? = builder.listener
    private var mXApiKey: String = builder.xApiKey
    private var mIsAppProduction: Boolean = builder.isAppProduction
    private var mXApiKeyTracking: String = builder.xApiKeyTracking
    private var quickPayDialog: MainQuickPayDialog? = null

    class Builder() {
        var userId: String = ""
        var userPhone: String = ""
        var productId: String = ""
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
            platform = mPlatform, xApiKeyTracking = mXApiKeyTracking, xApiKey = mXApiKey, isProductionEnv = mIsAppProduction
        )
        quickPayDialog = MainQuickPayDialog(
            activity = mActivity,
            fptId = mUserId,
            phone = mUserPhone,
            productId = mProductID,
            platform = mPlatform,
            xApiKey = mXApiKey,
            xApiKeyTracking = mXApiKeyTracking,
            {
                mListener?.onQuickPayExit()
            }
        )
        quickPayDialog?.show(fragmentManager, quickPayDialog?.tag)
    }

    fun stop() {
        quickPayDialog?.dismiss()

    }

    interface QuickPayListener {
        fun onQuickPayExit()
    }
}