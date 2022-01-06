package com.bda.quickpay_lib.ui.Ordersuccess

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.ui.base.BaseFragment
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_order_success.*

class OrderSuccessFragment : BaseFragment() {

    var mListener: onQuickpayListener? = null
    private var orderId: String? = null
    private var productName: String? = null
    private var shippingTime: Int = -1
    private var quantity: Int = 0
    private var totalPrice: String? = null

    private var btnConfirm: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null

    override fun enableDarkMode() {
        tvTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvHint?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        tvHotline?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        llOrder?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_dark)
        rlContainer?.setBackgroundColor(requireActivity().getColor(R.color.bg_dark))
        tvProduct?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvOrder?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvQuantityTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvSum?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvDeliveryTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvName?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        tvOrderId?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        tvQuantity?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        _price?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        ship_time?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        btnConfirm?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
        tvConfirm?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
    }

    override fun disableDarkMode() {
        tvTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvHint?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        tvHotline?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        llOrder?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_default)
        rlContainer?.setBackgroundColor(requireActivity().getColor(R.color.bg_default))
        tvProduct?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvOrder?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvQuantityTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvSum?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvDeliveryTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvName?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        tvOrderId?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        tvQuantity?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        _price?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        ship_time?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        btnConfirm?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
        tvConfirm?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getString("STR_ORDER_ID", "")
            productName = it.getString("STR_PRODUCT_NAME", "")
            shippingTime = it.getInt("STR_PRODUCT_SUPPLIER_TIME", -1)
            quantity = it.getInt("STR_PRODUCT_QUANTITY", 0)
            totalPrice = it.getString("STR_PRODUCT_TOTAL_PRICE", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_success, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnConfirm = view.findViewById(R.id.bn_confirm)
        tvConfirm = view.findViewById(R.id.text_bn_confirm)
        tvName.text = productName
        tvOrderId.text = orderId
        tvQuantity.text = quantity.toString() + " sản phẩm"

        _price.text = totalPrice

        ship_time.text = Functions.getShippingTimeBySupplier(shippingTime)

        btnConfirm?.setOnClickListener {
            mListener?.onOrderSuccessConfirmClick()
        }

        btnConfirm?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tvConfirm?.setTextColor(Color.WHITE)
            } else {
                tvConfirm?.setTextColor(Color.parseColor("#484848"))
            }
        }
    }

    fun setListener(listenr: onQuickpayListener) {
        mListener = listenr
    }

    interface onQuickpayListener {
        fun onOrderSuccessConfirmClick()
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            btnConfirm?.requestFocus()
        }, 100)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OrderSuccessFragment()
    }
}