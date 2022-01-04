package com.bda.quickpay_lib.ui.detailProduct

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.base.BaseFragment
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.dialog_message.*
import kotlinx.android.synthetic.main.fragment_add_to_cart_success.*
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.fragment_quickpay.*
import kotlinx.android.synthetic.main.fragment_quickpay.img_product_quick_pay
import kotlinx.android.synthetic.main.fragment_quickpay.price

class DetailProductFragment : BaseFragment(), DetailProductContract.View,
    MotionLayout.TransitionListener {

    var mListener: onDetailProductListener? = null
    private var presenter: DetailProductPresenter? = null
    private var product: Product? = null
    private var user: CheckCustomerResponse? = null
    private var isFavourite = false
    private var productId: String = ""
    private var fptId: String = ""
    private var phone: String = ""
    private var isAddToCart: Boolean = false
    private var isPlayAnimation = false

    private var tvProductName: SfTextView? = null
    private var icAddToCart: ImageView? = null
    private var icWishList: ImageView? = null
    private var icQuickPay: ImageView? = null
    private var icOffAd: ImageView? = null
    private var tvQuickPay: SfTextView? = null
    private var tvAddToCart: SfTextView? = null
    private var tvOffAdd: SfTextView? = null
    private var btnAddToCart: RelativeLayout? = null
    private var btnOffAd: RelativeLayout? = null
    private var btnQuickPay: RelativeLayout? = null
    private var btnWishList: RelativeLayout? = null
    private var tvTimeout: SfTextView? = null
    private var motionLayout: RelativeLayout? = null
    private var pbLoading: ProgressBar? = null
    private var isFirstOpen: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fptId = it.getString("STR_FPT_PLAY_ID", "")
            phone = it.getString("STR_PHONE", "")
            productId = it.getString("STR_PRODUCT_ID", "")
        }
        activity?.let {
            presenter = DetailProductPresenter(this@DetailProductFragment, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_product, container, false)
        motionLayout = view.findViewById(R.id.motionLayout)
        pbLoading = view.findViewById(R.id.pbLoading)
        tvProductName = view.findViewById(R.id.tvName)
        icWishList = view.findViewById(R.id.image_bn_wish)
        icOffAd = view.findViewById(R.id.img_off_ad)
        icAddToCart = view.findViewById(R.id.img_add_to_cart)
        tvAddToCart = view.findViewById(R.id.tvAddToCart)
        tvOffAdd = view.findViewById(R.id.tvOffAd)
        btnOffAd = view.findViewById(R.id.bn_off_ad)
        btnAddToCart = view.findViewById(R.id.bn_add_to_cart)
        btnQuickPay = view.findViewById(R.id.bn_quick_pay)
        btnWishList = view.findViewById(R.id.bn_wish_list)
        icQuickPay = view.findViewById(R.id.img_quick_pay)
        tvQuickPay = view.findViewById(R.id.tvQuickPay)
        tvTimeout = view.findViewById(R.id.tvTimeout)
        presenter?.getProduct(productId)
        presenter?.getProfile(fptId, phone)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var timer: CountDownTimer? = null
        if (isFirstOpen) {
            tvTimeout?.visibility = View.VISIBLE
            timer = object : CountDownTimer(15000 + 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTimeout?.text = "Đóng sau " + "(" + (millisUntilFinished / 1000) + "s)"
                }

                override fun onFinish() {
                    mListener?.onTimeoutComplete()
                }
            }
            timer.start()
            isFirstOpen = false
        } else {
            timer?.cancel()
            tvTimeout?.visibility = View.GONE
        }

        btnQuickPay?.setOnClickListener {
            timer?.cancel()
            if (product != null && user != null) {
                mListener?.onBuyProductClick(product!!, user!!)
            }
        }

        btnOffAd?.setOnClickListener {
            timer?.cancel()
            if (product != null && user != null) {
                mListener?.onTurnOffAdClick(product!!, user!!)
            }
        }

        btnAddToCart?.setOnClickListener {
            if (product != null && user != null) {
                mListener?.onAddToCartClick(product!!, user!!)
            }
        }

        btnWishList?.setOnClickListener {
            if (isFavourite) {
//                presenter?.deleteToWishList(productId, user.uid)
            } else {
//                presenter?.addToWishList(productId, user.uid)
            }

        }

        btnAddToCart?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tvAddToCart?.setTextColor(Color.WHITE)
                icAddToCart?.setColorFilter(Color.WHITE)

            } else {
                Functions.animateScaleDown(btnAddToCart!!)
                if (isAddToCart) {
                    tvAddToCart?.setTextColor(Color.parseColor("#A1B753"))
                    icAddToCart?.setColorFilter(Color.parseColor("#A1B753"))
                } else {
                    tvAddToCart?.setTextColor(Color.parseColor("#484848"))
                    icAddToCart?.setColorFilter(Color.parseColor("#484848"))
                }
            }
        }

        btnQuickPay?.setOnFocusChangeListener { v, hasFocus ->
            tvQuickPay?.isSelected = hasFocus
            icQuickPay?.isSelected = hasFocus
        }

        btnOffAd?.setOnFocusChangeListener { v, hasFocus ->
            tvOffAdd?.isSelected = hasFocus
            icOffAd?.isSelected = hasFocus
        }

        btnWishList?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                icWishList?.setColorFilter(Color.WHITE)
            } else {
                Functions.animateScaleDown(bn_wish_list!!)
                if (isFavourite) {
                    icWishList?.setColorFilter(Color.parseColor("#A1B753"))
                } else {
                    icWishList?.setColorFilter(Color.parseColor("#484848"))
                }

            }
        }
        icAddToCart?.setColorFilter(Color.parseColor("#484848"))
        icWishList?.setColorFilter(Color.parseColor("#484848"))
//        loadImageFavourite(isFavourite)
//        loadAddToCart(isAddToCart)
    }

    override fun sendProfileSuccess(user: CheckCustomerResponse) {
        this.user = user
//        presenter?.checkWishList(productId, user.uid)
//        presenter?.checkCart(userId = user.uid, mProductId = productId)
    }

    override fun onResume() {
        super.onResume()
        this.product?.let {
            initProduct(it)
            loadImageFavourite(isFavourite)
            loadAddToCart(isAddToCart)
        }
        btnQuickPay?.requestFocus()
    }

    override fun sendProductSuccess(mProduct: Product) {
        product = mProduct
        product?.let {
            initProduct(product!!)
        }
    }

    private fun initProduct(mProduct: Product) {
        tvProductName?.text = mProduct.display_name_detail

        price.text = Functions.formatMoney(mProduct.listedPrice)
        sale_price?.text = Functions.formatMoney(mProduct.price)
        img_product_quick_pay?.let {
            Functions.loadImage(mProduct.imageCover, it)
        }

        val discountPercent =
            (100 - (mProduct.price * 100 / mProduct.listedPrice)).toInt()
        if (discountPercent > 0) {
            percent?.visibility = View.VISIBLE
            percent?.text = "-" + discountPercent + "%"
        } else {
            percent?.visibility = View.GONE
        }

//        if (mProduct.brandShops.size > 0) {
//            detail_supplier?.visibility = View.VISIBLE
//            image_supplier?.let {
//                Functions.loadImage(mProduct.brandShops.get(0).skin_image, it)
//            }
//            detail_supplier_name?.text = mProduct.brandShops.get(0).display_name_detail
//            supplier_number_count?.text =
//                Functions.format(product.brandShops[0].product_counts.toLong()) + " sản phẩm"
//        } else {
//            detail_supplier?.visibility = View.GONE
//        }
        if (mProduct.short_descriptions.short_des_1 != null && mProduct.short_descriptions.short_des_1.isNotEmpty()) {
            ll_sort_1?.visibility = View.VISIBLE
            short_des_1?.text = mProduct.short_descriptions.short_des_1
            try {
                short_des_1?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_1_color.takeIf { mProduct.short_descriptions.short_des_1_color != null && mProduct.short_descriptions.short_des_1_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_1?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_2 != null && mProduct.short_descriptions.short_des_2.isNotEmpty()) {
            ll_sort_2?.visibility = View.VISIBLE
            short_des_2?.text = mProduct.short_descriptions.short_des_2
            try {
                short_des_2?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_2_color.takeIf { mProduct.short_descriptions.short_des_2_color != null && mProduct.short_descriptions.short_des_2_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_2?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_3 != null && mProduct.short_descriptions.short_des_3?.isNotEmpty()) {
            ll_sort_3?.visibility = View.VISIBLE
            short_des_3?.text = mProduct.short_descriptions.short_des_3
            try {
                short_des_3?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_3_color.takeIf { mProduct.short_descriptions.short_des_3_color != null && mProduct.short_descriptions.short_des_3_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_3?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_4 != null && mProduct.short_descriptions.short_des_4?.isNotEmpty()) {
            ll_sort_4?.visibility = View.VISIBLE
            short_des_4?.text = mProduct.short_descriptions.short_des_4
            try {
                short_des_4?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_4_color.takeIf { mProduct.short_descriptions.short_des_4_color != null && mProduct.short_descriptions.short_des_4_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_4?.visibility = View.GONE
        }
    }

    fun myOnkeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP
            && !isPlayAnimation
            && ((btnQuickPay != null && btnQuickPay!!.hasFocus())
                    || ((btnAddToCart != null && btnAddToCart!!.hasFocus()
                    && btnQuickPay != null
                    && btnQuickPay?.visibility != View.VISIBLE)
                    || (btnWishList != null
                    && btnWishList!!.hasFocus()
                    && btnQuickPay != null
                    && btnQuickPay?.visibility != View.VISIBLE)
                    ))
        ) {
            showInfo()
            return true

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN
            && btnAddToCart != null
            && btnWishList != null
            && (btnAddToCart!!.hasFocus() || btnWishList!!.hasFocus())
            && !isPlayAnimation
        ) {
            hideInfo()
        }

        return false
    }

    override fun showProgress() {
        pbLoading?.visibility = View.VISIBLE
        motionLayout?.visibility = View.GONE
    }

    override fun hideProgress() {
        motionLayout?.visibility = View.VISIBLE
        pbLoading?.visibility = View.GONE
    }

    fun setListener(listenr: onDetailProductListener) {
        mListener = listenr
    }

    interface onDetailProductListener {
        fun onBuyProductClick(product: Product, user: CheckCustomerResponse)
        fun onTurnOffAdClick(product: Product, user: CheckCustomerResponse)
        fun onAddToCartClick(product: Product, user: CheckCustomerResponse)
        fun onTimeoutComplete()
    }

    private fun loadImageFavourite(isFav: Boolean) {
        if (isFav) {
            btnWishList?.let {
                if (it.hasFocus()) {
                    icWishList?.setColorFilter(Color.WHITE)
                } else {
                    icWishList?.setColorFilter(Color.parseColor("#A1B753"))
                }
            }

            activity?.let {
                icWishList?.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_heart_liked
                    )
                )
            }
        } else {
            btnWishList?.let {
                if (it.hasFocus()) {
                    icWishList?.setColorFilter(Color.WHITE)
                } else {
                    icWishList?.setColorFilter(Color.parseColor("#484848"))
                }
            }

            activity?.let {
                icWishList?.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_heart_selector
                    )
                )
            }
        }
    }

    private fun loadAddToCart(mIsAddToCart: Boolean) {
        if (mIsAddToCart) {
            tvAddToCart?.setTextColor(Color.parseColor("#A1B753"))
            icAddToCart?.setColorFilter(Color.parseColor("#A1B753"))
            activity?.let {
                icAddToCart?.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_tick
                    )
                )
            }
        } else {
            tvAddToCart?.setTextColor(Color.parseColor("#484848"))
            activity?.let {
                icAddToCart?.setImageDrawable(
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_plus
                    )
                )
            }
        }
    }

    private fun hideInfo() {
        if (!isPlayAnimation)
            motionLayout?.let {
//                it.setTransition(R.id.transition_hiding_info)
//                it.setTransitionListener(this)
//                it.transitionToEnd()
            }
    }

    private fun showInfo() {
        if (!isPlayAnimation)
            motionLayout?.let {
//                it.setTransition(R.id.transition_showing_info)
//                it.addTransitionListener(this)
//                it.transitionToEnd()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailProductFragment()
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        isPlayAnimation = true
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        isPlayAnimation = false
    }

    fun setIsFistOpen(mIsFistOpen: Boolean) {
        this.isFirstOpen = mIsFistOpen
    }
}