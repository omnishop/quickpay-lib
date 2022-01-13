package com.bda.quickpay_lib.ui.banner

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.ui.base.BaseFragment
import com.bda.quickpay_lib.utils.Functions
import com.drowsyatmidnight.haint.android_banner_sdk.BannerListener
import com.drowsyatmidnight.haint.android_banner_sdk.masterhead_banner.MasterHeadBanner
import com.drowsyatmidnight.haint.android_banner_sdk.masterhead_banner.MasterheadBannerView

class BannerFragment : BaseFragment() {

    private var mastheadBannerView: MasterheadBannerView? = null
    private var listener: onOmniBannerListener? = null
    var mastheadBanner: MasterHeadBanner? = null

    // endregion
    var screenNameDefault: String? = null
    var screenIdDefault: String? = null
    var useDataDefault = false
    var userTypeDefault: String? = null
    var versionAppDefault: String? = null
    var userIdDefault: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_banner, container, false)
        mastheadBannerView = view.findViewById(R.id.mastheadBannerView)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTestData()
        initComponents()
    }

    override fun enableDarkMode() {
    }

    override fun disableDarkMode() {
    }

    private fun initTestData() {
        screenNameDefault = "trang chủ"
        screenIdDefault = ""
        useDataDefault = false
        userTypeDefault = "Free"
        versionAppDefault = ""
        userIdDefault = "a"
    }

    private fun initComponents() {
        mastheadBanner = MasterHeadBanner(mastheadBannerView, requireContext())
        mastheadBanner?.bannerListener = (object : BannerListener {
            override fun onBannerLoaded(countdown: Int) {
            }

            override fun onClickAds(url: String?, useWebViewInApp: Boolean?) {
                Log.d("AAA", "onClickAds")
                listener?.onAdsClick(Functions.loadProduct(requireContext())!!)
            }

            override fun onCloseBanner() {
            }

            override fun onHideBanner() {
            }

            override fun onReloadBanner() {
            }

            override fun onRequestBannerFailure() {
            }

            override fun onRequestBannerSuccess(htmlCode: String?) {
            }

            override fun onResizeBanner() {
            }

            override fun onShowBanner() {
            }

        })
        mastheadBanner?.showBanner(
            this,
            screenNameDefault,
            screenIdDefault,
            useDataDefault,
            userTypeDefault,
            versionAppDefault,
            userIdDefault
        )

        Handler().postDelayed({
            mastheadBannerView?.requestFocus()
        }, 100)
    }

    interface onOmniBannerListener {
        fun onAdsClick(product: Product)
    }

    fun setListener(mListener: onOmniBannerListener) {
        listener = mListener
    }
}