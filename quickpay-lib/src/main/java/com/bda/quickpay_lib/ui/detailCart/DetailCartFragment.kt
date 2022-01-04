package com.bda.quickpay_lib.ui.detailCart

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_detail_cart.*

class DetailCartFragment : Fragment(), DetailCartContract.View {

    var mListener: onAddToCartListener? = null
    private var quatity: Int = 1
    private var product: Product? = null
    private var user: CheckCustomerResponse? = null
    private var presenter: DetailCartPresenter? = null
    private var btnConfirm: RelativeLayout? = null
    private var btnMinus: RelativeLayout? = null
    private var btnPlus: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null
    private var imgMinus: ImageView? = null
    private var imgPlus: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            presenter = DetailCartPresenter(this@DetailCartFragment, it)
        }
        val view = inflater.inflate(R.layout.fragment_detail_cart, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnConfirm = view.findViewById(R.id.bn_confirm)
        btnMinus = view.findViewById(R.id.bn_minus)
        btnPlus = view.findViewById(R.id.bn_plus)
        tvConfirm = view.findViewById(R.id.tvConfirm)
        imgMinus = view.findViewById(R.id.image_bn_minus)
        imgPlus = view.findViewById(R.id.image_bn_plus)

//        tvName?.text = product?.name
//        price?.text = Functions.formatMoney(product!!.pricing!!.price_with_vat)
//        img_product_quick_pay?.let {
//            Functions.loadImage(product!!.image_cover, it)
//        }

        btnMinus?.apply {
            setOnClickListener {
                if (quatity > 1)
                    setSetQuantity(--quatity)
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    Functions.animateScaleUpLiveStream(rl_quantity, 1.05f)
                    imgMinus?.setColorFilter(Color.parseColor("#A1B753"))
                    rl_quantity.isSelected = true
                } else {
                    imgMinus?.setColorFilter(Color.parseColor("#484848"))
                    if (!btnMinus!!.hasFocus() && !btnPlus!!.hasFocus()) {
                        Functions.animateScaleDown(rl_quantity)
                        rl_quantity.isSelected = false
                    }
                }
            }
        }


        btnPlus?.apply {
            setOnClickListener {
                setSetQuantity(++quatity)
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    Functions.animateScaleUpLiveStream(rl_quantity, 1.05f)
                    imgPlus?.setColorFilter(Color.parseColor("#A1B753"))
                    rl_quantity.isSelected = true
                } else {
                    imgPlus?.setColorFilter(Color.parseColor("#484848"))
                    if (!btnMinus!!.hasFocus() && !btnPlus!!.hasFocus()) {
                        rl_quantity.isSelected = false
                        Functions.animateScaleDown(rl_quantity)
                    }
                }
            }
        }

        btnConfirm?.setOnClickListener {
//            product?.quantity = quatity
//            if (product != null) {
//                product!!.quantity = quatity
//                presenter?.saveCart(userId = user!!.uid, product!!)
//            } else {
//                Functions.showMessage(
//                    activity!!,
//                    "Chức năng đang được bảo trì, Vui lòng liện hệ sau"
//                )
//                return@setOnClickListener
//            }
        }

        btnConfirm?.setOnFocusChangeListener { _, hasFocus ->
            if (btnConfirm != null)
                if (hasFocus) {
                    Functions.animateScaleUp(btnConfirm!!, 1.05f)
                    tvConfirm?.setTextColor(Color.parseColor("#ffffff"))

                } else {
                    Functions.animateScaleDown(btnConfirm!!)
                    tvConfirm?.setTextColor(Color.parseColor("#484848"))
                }
        }
    }

    fun setListener(listenr: onAddToCartListener) {
        mListener = listenr
    }

    private fun setSetQuantity(q: Int) {
        quantity.text = "$q"
//        product!!.quantity = q
    }

    interface onAddToCartListener {
        fun onSubmitAddToCartSuccess(product: Product)
    }

    override fun onResume() {
        super.onResume()
//        quatity = product!!.quantity
        setSetQuantity(quatity)
        Handler().postDelayed({ bn_confirm?.requestFocus() }, 100)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailCartFragment()
    }


    fun setUser(user: CheckCustomerResponse) {
        this.user = user
    }

    fun setProduct(product: Product) {
        this.product = product
    }

    fun getProduct(): Product {
        return this.product!!
    }

    override fun sendSaveCartSuccess() {
        mListener?.onSubmitAddToCartSuccess(product!!)
    }

    override fun sendFailed(message: String) {
        activity?.let {
            Functions.showMessage(it, message)
        }
    }
}