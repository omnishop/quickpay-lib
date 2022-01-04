package com.bda.quickpay_lib.ui.addToCartSuccess

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_add_to_cart_success.*

class AddToCartSuccessFragment : Fragment() {

    var mListener: onAddToCartSuccessListener? = null
    private var orderId: String? = null
    private var product: Product? = null
    private var btnConfirm: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getString("STR_ORDER_ID", "123456789")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_to_cart_success, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnConfirm = view.findViewById(R.id.bn_confirm)
        tvConfirm = view.findViewById(R.id.tvConfirm)

//        tvBrandShop.text = product!!.supplier.name
//        tvOrderId.text = "Đơn hàng: #" + "1234567"
//        tvQuantity.text = "Số lượng: " + product!!.quantity
//        tvPrice.text = Functions.formatMoney(product!!.pricing.price_with_vat * product!!.quantity)
//
//        img_product_quick_pay?.let {
//            Functions.loadImage(product!!.image_cover, it)
//        }

        btnConfirm?.setOnClickListener {
            mListener?.onCompleteAddToCartClick(product = product!!)
        }

        btnConfirm?.setOnFocusChangeListener { _, hasFocus ->
            if (btnConfirm != null)
                if (hasFocus) {
                    Functions.animateScaleUp(btnConfirm!!, 1.05f)
                    tvConfirm?.setTextColor(Color.WHITE)

                } else {
                    Functions.animateScaleDown(btnConfirm!!)
                    tvConfirm?.setTextColor(Color.parseColor("#484848"))
                }
        }
    }

    fun setListener(listenr: onAddToCartSuccessListener) {
        mListener = listenr
    }

    interface onAddToCartSuccessListener {
        fun onCompleteAddToCartClick(product: Product)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            bn_confirm?.requestFocus()
        }, 100)
    }

    fun setProduct(product: Product) {
        this.product = product
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddToCartSuccessFragment()
    }
}