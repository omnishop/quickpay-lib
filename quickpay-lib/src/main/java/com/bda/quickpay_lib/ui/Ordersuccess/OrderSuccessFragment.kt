package com.bda.quickpay_lib.ui.Ordersuccess

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_order_success.*

class OrderSuccessFragment : Fragment() {

    var mListener: onQuickpayListener? = null
    private var orderId: String? = null
    private var productName: String? = null
    private var shippingTime: Int = -1
    private var quantity: Int = 0
    private var totalPrice: String? = null

    private var btnConfirm: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null

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