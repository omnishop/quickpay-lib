package com.bda.quickpay_lib.ui.detailVoucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_detail_voucher.*

class DetailVoucherFragment : BaseFragment(), DetailVoucherContract.View {
    var mListener: onDetailVoucherListener? = null
    private var productId: String = ""
    private var fptId: String = ""
    private var phone: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fptId = it.getString("STR_FPT_PLAY_ID", "")
            phone = it.getString("STR_PHONE", "")
            productId = it.getString("STR_PRODUCT_ID", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_voucher, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bn_off_ad?.setOnClickListener {
            mListener?.onSkipVoucherClick()
        }
        bn_off_ad?.setOnClickListener {
            mListener?.onReceiveVoucherClick("")
        }
        bn_off_ad?.setOnFocusChangeListener { v, hasFocus ->
            tvOffAd?.isSelected = hasFocus
        }
        bn_receive?.setOnFocusChangeListener { v, hasFocus ->
            tvReceive?.isSelected = hasFocus
        }
    }

    override fun enableDarkMode() {
        motionLayout?.setBackgroundColor(requireActivity().getColor(R.color.bg_dark))
        tvName?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvVoucherHint?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvOffAd?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
        tvReceive?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
        bn_off_ad?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
        bn_receive?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
    }

    override fun disableDarkMode() {
        motionLayout?.setBackgroundColor(requireActivity().getColor(R.color.bg_default))
        tvName.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvVoucherHint?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvOffAd?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
        tvReceive?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
        bn_off_ad?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
        bn_receive?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
    }

    fun setListener(listener: onDetailVoucherListener) {
        mListener = listener
    }

    interface onDetailVoucherListener {
        fun onReceiveVoucherClick(voucherId: String)
        fun onSkipVoucherClick()
    }
}