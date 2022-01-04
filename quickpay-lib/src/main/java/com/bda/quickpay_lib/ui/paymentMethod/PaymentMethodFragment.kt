package com.bda.quickpay_lib.ui.paymentMethod

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.Voucher
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.fragment_payment_method.*

class PaymentMethodFragment : Fragment(), PaymentMethodContract.View {

    var mListener: onPaymentMethodListener? = null
    private var mProduct: Product? = null
    private var mUser: CheckCustomerResponse? = null
    var mVoucher: Voucher? = null
    private var presenter: PaymentMethodPresenter? = null
    private var btnChooseVoucher: RelativeLayout? = null
    private var tvBtnChooseVoucher: SfTextView? = null
    private var btnConfirm: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            presenter = PaymentMethodPresenter(this@PaymentMethodFragment, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_payment_method, container, false)
        btnChooseVoucher = mView.findViewById(R.id.bn_choose_voucher)
        tvBtnChooseVoucher = mView.findViewById(R.id.text_choose_voucher)
        btnConfirm = mView.findViewById(R.id.bn_confirm)
        tvConfirm = mView.findViewById(R.id.tvConfirm)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            mVoucher = Voucher(
                uid = it.getString("STR_VOUCHER_ID", ""),
                code = it.getString("STR_VOUCHER_CODE", ""),
                applied_value = it.getDouble("STR_VOUCHER_APPLY_VALUE", 0.0)
            )
        }

        btnConfirm?.setOnClickListener {
            var voucherCode = if (mVoucher == null) "" else mVoucher!!.code
            var voucherId = if (mVoucher == null) "" else mVoucher!!.uid

            presenter?.submitOrder(
                customerId = mUser!!.data.uid,
                name = mUser!!.data.name,
                phone = mUser!!.data.phone,
                voucherCode = voucherCode,
                voucherId = voucherId,
                requestType = 2,
                list = arrayListOf(mProduct!!)
            )
        }

        btnConfirm?.setOnFocusChangeListener { _, hasFocus ->
            tvConfirm?.isSelected = hasFocus
        }

        mProduct?.let {
//            price?.text = Functions.formatMoney(it.pricing.price_with_vat * it.quantity)
        }
        btnChooseVoucher?.setOnClickListener {
            mProduct?.let {
                mListener?.onSelectVoucherClick(
                    product = it,
                    user = mUser!!
                )
            }
        }

        btnChooseVoucher?.setOnFocusChangeListener { v, hasFocus ->
            tvBtnChooseVoucher?.isSelected = hasFocus
        }
    }

    private fun totalPrice(): String {
        var voucherValue = 0.0

        if (mVoucher != null) voucherValue = mVoucher!!.applied_value

        voucher?.text =
            if (voucherValue > 0) "-${Functions.formatMoney(voucherValue)}" else Functions.formatMoney(
                voucherValue
            )
        var total = 0.0
        mProduct?.let {
//            total = it.pricing.price_with_vat * it.quantity - voucherValue
        }

        ship?.text = "Xác nhận sau"

        return Functions.formatMoney(total)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            bn_confirm?.requestFocus()
        }, 100)

        total_vat?.text = totalPrice()
        if (mVoucher != null) {
            tvBtnChooseVoucher?.text = mVoucher!!.code
        } else {
            tvBtnChooseVoucher?.text = "Chọn / nhập voucher"
        }
    }

    fun setListener(listenr: onPaymentMethodListener) {
        mListener = listenr
    }

    interface onPaymentMethodListener {
        fun onSubmitOrderSuccess(orderId: String, product: Product, totalPrice: String)
        fun onSelectVoucherClick(
            product: Product,
            user: CheckCustomerResponse
        )
    }

    companion object {
        val freeShipValue = 200000

        @JvmStatic
        fun newInstance() =
            PaymentMethodFragment()
    }

    override fun sendOrderSuccess(orderId: String) {
//        mListener?.onSubmitOrderSuccess(orderId, mProduct ?: Product(), totalPrice())
    }

    override fun sendFailed(message: String) {
        activity?.let {
            Functions.showMessage(it, message)
        }
    }

    fun setUser(user: CheckCustomerResponse) {
        this.mUser = user
    }

    fun setProduct(product: Product) {
        this.mProduct = product
    }

    fun setVoucher(voucher: Voucher) {
        this.mVoucher = voucher
    }

    fun getProduct(): Product {
        return this.mProduct!!
    }
}