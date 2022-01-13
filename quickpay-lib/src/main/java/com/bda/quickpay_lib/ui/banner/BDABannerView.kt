package com.bda.quickpay_lib.ui.banner

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.bda.quickpay_lib.ui.banner.BDABannerView
import android.view.LayoutInflater
import com.bda.quickpay_lib.R

internal class BDABannerView : WebView {
    var mMyWebView: BDABannerView? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    fun initView(context: Context): BDABannerView {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custome_webview, this) as BDABannerView
        view.settings.javaScriptEnabled = true
        view.settings.useWideViewPort = true
        view.settings.loadWithOverviewMode = true
        view.settings.domStorageEnabled = true
        return view
    }
}