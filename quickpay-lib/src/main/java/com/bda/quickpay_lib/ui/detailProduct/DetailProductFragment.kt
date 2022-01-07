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
import com.bda.quickpay_lib.utils.view.SfStrikeTextView
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_detail_product.*
import kotlinx.android.synthetic.main.fragment_quickpay.img_product_quick_pay

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
    private var tvSalePrice: SfTextView? = null
    private var tvPrice: SfStrikeTextView? = null
    private var icAddToCart: ImageView? = null
    private var icWishList: ImageView? = null
    private var icQuickPay: ImageView? = null
    private var tvQuickPay: SfTextView? = null
    private var tvAddToCart: SfTextView? = null
    private var tvOffAdd: SfTextView? = null
    private var btnAddToCart: RelativeLayout? = null
    private var btnOffAd: RelativeLayout? = null
    private var btnQuickPay: RelativeLayout? = null
    private var btnWishList: RelativeLayout? = null
    private var motionLayout: RelativeLayout? = null
    private var ivSort1: ImageView? = null
    private var tvShortDes1: SfTextView? = null
    private var ivSort2: ImageView? = null
    private var tvShortDes2: SfTextView? = null
    private var ivSort3: ImageView? = null
    private var tvShortDes3: SfTextView? = null
    private var ivSort4: ImageView? = null
    private var tvShortDes4: SfTextView? = null
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
        tvSalePrice = view.findViewById(R.id.sale_price)
        tvPrice = view.findViewById(R.id.price)
        motionLayout = view.findViewById(R.id.motionLayout)
        pbLoading = view.findViewById(R.id.pbLoading)
        tvProductName = view.findViewById(R.id.tvName)
        icWishList = view.findViewById(R.id.image_bn_wish)
        icAddToCart = view.findViewById(R.id.img_add_to_cart)
        tvAddToCart = view.findViewById(R.id.tvAddToCart)
        tvOffAdd = view.findViewById(R.id.tvOffAd)
        btnOffAd = view.findViewById(R.id.bn_off_ad)
        btnAddToCart = view.findViewById(R.id.bn_add_to_cart)
        btnQuickPay = view.findViewById(R.id.bn_quick_pay)
        btnWishList = view.findViewById(R.id.bn_wish_list)
        icQuickPay = view.findViewById(R.id.img_quick_pay)
        tvQuickPay = view.findViewById(R.id.tvQuickPay)
        ivSort1 = view.findViewById(R.id.ivSort1)
        tvShortDes1 = view.findViewById(R.id.short_des_1)
        ivSort2 = view.findViewById(R.id.ivSort2)
        tvShortDes2 = view.findViewById(R.id.short_des_2)
        ivSort3 = view.findViewById(R.id.ivSort3)
        tvShortDes3 = view.findViewById(R.id.short_des_3)
        ivSort4 = view.findViewById(R.id.ivSort4)
        tvShortDes4 = view.findViewById(R.id.short_des_4)

        presenter?.getProduct(productId)
        presenter?.getProfile(fptId, phone)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var timer: CountDownTimer? = null
        if (isFirstOpen) {
            timer = object : CountDownTimer(15000 + 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvOffAdd?.text = "Bỏ qua " + "(" + (millisUntilFinished / 1000) + "s)"
                }

                override fun onFinish() {
                    mListener?.onTimeoutComplete()
                }
            }
            timer.start()
            isFirstOpen = false
        } else {
            timer?.cancel()
            tvOffAdd?.text = "Bỏ qua"
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

    override fun enableDarkMode() {
        motionLayout?.setBackgroundColor(requireActivity().getColor(R.color.bg_dark))
        tvProductName?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvSalePrice?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvPrice?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        tvShortDes1?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvShortDes2?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvShortDes3?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvShortDes4?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        btnQuickPay?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
        btnOffAd?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
        tvOffAdd?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
        tvQuickPay?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
        icQuickPay?.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_thunder_selector_dark))
    }

    override fun disableDarkMode() {
        motionLayout?.setBackgroundColor(requireActivity().getColor(R.color.bg_default))
        tvProductName?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvSalePrice?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvPrice?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        tvShortDes1?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvShortDes2?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvShortDes3?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvShortDes4?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        btnQuickPay?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
        btnOffAd?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
        tvOffAdd?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
        tvQuickPay?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
        icQuickPay?.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_thunder_selector_default))
    }

    private fun initProduct(mProduct: Product) {
        tvProductName?.text = mProduct.display_name_detail

        tvPrice?.text = Functions.formatMoney(mProduct.listedPrice)
        tvSalePrice?.text = Functions.formatMoney(mProduct.price)
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
            tvShortDes1?.text = mProduct.short_descriptions.short_des_1
            try {
                tvShortDes1?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_1_color.takeIf { mProduct.short_descriptions.short_des_1_color != null && mProduct.short_descriptions.short_des_1_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_1?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_2 != null && mProduct.short_descriptions.short_des_2.isNotEmpty()) {
            ll_sort_2?.visibility = View.VISIBLE
            tvShortDes2?.text = mProduct.short_descriptions.short_des_2
            try {
                tvShortDes2?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_2_color.takeIf { mProduct.short_descriptions.short_des_2_color != null && mProduct.short_descriptions.short_des_2_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_2?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_3 != null && mProduct.short_descriptions.short_des_3?.isNotEmpty()) {
            ll_sort_3?.visibility = View.VISIBLE
            tvShortDes3?.text = mProduct.short_descriptions.short_des_3
            try {
                tvShortDes3?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_3_color.takeIf { mProduct.short_descriptions.short_des_3_color != null && mProduct.short_descriptions.short_des_3_color.isNotEmpty() }
                    ?: "#000000"))
            } catch (e: IllegalArgumentException) {

            }
        } else {
            ll_sort_3?.visibility = View.GONE
        }

        if (mProduct.short_descriptions.short_des_4 != null && mProduct.short_descriptions.short_des_4?.isNotEmpty()) {
            ll_sort_4?.visibility = View.VISIBLE
            tvShortDes4?.text = mProduct.short_descriptions.short_des_4
            try {
                tvShortDes4?.setTextColor(Color.parseColor(mProduct.short_descriptions.short_des_4_color.takeIf { mProduct.short_descriptions.short_des_4_color != null && mProduct.short_descriptions.short_des_4_color.isNotEmpty() }
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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.disposeAPI()
    }
}