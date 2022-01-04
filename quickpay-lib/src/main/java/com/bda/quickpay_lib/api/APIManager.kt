package com.bda.quickpay_lib.api

import com.bda.quickpay_lib.BuildConfig
import com.bda.quickpay_lib.utils.QuickPayUtils


class APIManager {
    private val HOST_URL = QuickPayUtils.getBaseUrl()

    private val mApi: ItemAPI
    private val generator: APIGenerator = APIGenerator(HOST_URL)

    init {
        mApi = generator.createService(ItemAPI::class.java)
    }

    fun getApi(): ItemAPI {
        return mApi
    }

    companion object {
        private var sInstance: APIManager? = null

        @Synchronized
        fun getInstance(): APIManager {
            if (sInstance == null) {
                sInstance = APIManager()
            }
            return sInstance as APIManager
        }
    }

}