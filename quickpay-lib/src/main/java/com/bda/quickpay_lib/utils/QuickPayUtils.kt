package com.bda.quickpay_lib.utils


object QuickPayUtils {
    // Config
    private var mPlatform: String = ""
    private var mIsDarkMode: Boolean = false
    private var mXpiKey: String = ""
    private var mXpiKeyTracking: String = ""
    private var mIsProductionEnv: Boolean = true


    //    fun callLogApi(
//        mAction: String,
//        mUserID: String,
//        mUserPhone: String,
//        logData: LogData,
//        mPlatform: String
//    ) {
//        val executor: ExecutorService = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())
//        executor.execute {
//            try {
//                val shoppingTVUrl = URL(getBaseTrackingUrl())
//                val myConnection: HttpsURLConnection =
//                    shoppingTVUrl.openConnection() as HttpsURLConnection
//                if (isDevEnv()) {
//                    myConnection.setRequestProperty("is-dev", "yes")
//                }
//                myConnection.setRequestProperty("x-api-key", getXApiKeyTracking())
//                myConnection.setRequestProperty("ott", "FPTOTT")
//                myConnection.setRequestProperty("Content-Type", "application/json; utf-8")
//                myConnection.setRequestProperty("Accept", "application/json");
//                myConnection.setRequestMethod("POST")
//                myConnection.setDoOutput(true)
//                val parent = JSONObject()
//                val data = JSONObject()
//                // Banner
//                logData.url?.let {
//                    data.put("url", it)
//                }
//                logData.productId?.let {
//                    data.put("productId", it)
//                }
//
//                // ORDER_SUCCESSFUL_v2
//                logData.ORDER_ID?.let {
//                    data.put("ORDER_ID", it)
//                }
//                logData.SCREEN?.let {
//                    data.put("SCREEN", it)
//                }
//                // CLICK_QUICKPAY_BUTTON_v2
//                logData.ITEM_BRAND?.let {
//                    data.put("ITEM_BRAND", it)
//                }
//                logData.ITEM_CATEGORY_ID?.let {
//                    data.put("ITEM_CATEGORY_ID", it)
//                }
//                logData.ITEM_CATEGORY_NAME?.let {
//                    data.put("ITEM_CATEGORY_NAME", it)
//                }
//                logData.ITEM_ID?.let {
//                    data.put("ITEM_ID", it)
//                }
//                logData.ITEM_INDEX?.let {
//                    data.put("ITEM_INDEX", it)
//                }
//                logData.ITEM_LIST_PRICE_VAT?.let {
//                    data.put("ITEM_LIST_PRICE_VAT", it)
//                }
//                logData.ITEM_NAME?.let {
//                    data.put("ITEM_NAME", it)
//                }
//                logData.ITEM_NAME?.let {
//                    data.put("ITEM_NAME", it)
//                }
//                //CLICK_ADD_TO_CART_BUTTON_v2
//                logData.CART_TOTAL_ITEM?.let {
//                    data.put("CART_TOTAL_ITEM", it)
//                }
//                logData.CART_VALUE?.let {
//                    data.put("CART_VALUE", it)
//                }
//                logData.ITEM_CATEGORY_ID?.let {
//                    data.put("ITEM_CATEGORY_ID", it)
//                }
//                logData.ITEM_CATEGORY_NAME?.let {
//                    data.put("ITEM_CATEGORY_NAME", it)
//                }
//
//                parent.put("action", mAction)
//                parent.put("timestamp", Calendar.getInstance().timeInMillis)
//                parent.put("uid", mUserID)
//                parent.put("platform", mPlatform)
//                parent.put("user_phone", mUserPhone)
//                parent.put("data", data.toString())
//                val wr = OutputStreamWriter(myConnection.getOutputStream())
//                wr.write(parent.toString())
//                wr.flush()
//
//                val sb = StringBuilder()
//                val HttpResult: Int = myConnection.getResponseCode()
//                if (HttpResult == HttpURLConnection.HTTP_OK) {
//                    Log.d("AAA", "HTTP_OK")
//                } else {
//                    Log.d("AAA", "HTTP_error:" + myConnection.getResponseMessage())
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.d("AAA", "EEOE:" + e.message.toString())
//            }
//            handler.post {
//                Log.d("AAA", "DONE")
//            }
//        }
//    }
//
    fun getImageBaseUrl(): String {
        if (isProductEnv()) {
            return "https://tvcommerce-st.fptplay.net/prod/"
        } else {
            return "https://static.shoppingtv.vn/dev/"
        }
    }

    fun getBaseUrl(): String {
        if (isProductEnv()) {
            return "https://api-public.shoppingtv.vn/api/v1.1/"
        } else {
            return "https://dev-api-public.shoppingtv.vn/api/v1.1/"
        }
    }

    fun getBaseTrackingUrl(): String {
        if (isProductEnv()) {
            return "https://dev-tracking.shoppingtv.vn/log/behavior"
        } else {
            return "https://tracking.shoppingtv.vn/log/behavior"
        }
    }

    fun getXApiKeyTracking(): String {
        return this.mXpiKeyTracking
    }

    fun getXApiKey(): String {
        return this.mXpiKey
    }

    fun getPlatform(): String {
        return this.mPlatform
    }

    fun isProductEnv(): Boolean {
        return this.mIsProductionEnv
    }

    fun isDarkMode(): Boolean {
        return this.mIsDarkMode
    }

    fun initQuickPay(
        platform: String,
        xApiKey: String,
        xApiKeyTracking: String,
        isProductionEnv: Boolean,
        isDarkMode: Boolean = false
    ) {
        this.mPlatform = platform
        this.mIsProductionEnv = isProductionEnv
        this.mXpiKey = xApiKey
        this.mIsDarkMode = isDarkMode
        this.mXpiKeyTracking = xApiKeyTracking

    }
}