package com.bda.quickpay_lib.ui.main

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.Ordersuccess.OrderSuccessFragment
import com.bda.quickpay_lib.ui.addToCartSuccess.AddToCartSuccessFragment
import com.bda.quickpay_lib.ui.banner.BannerFragment
import com.bda.quickpay_lib.ui.chooseVoucher.ChooseVoucherFragment
import com.bda.quickpay_lib.ui.detailCart.DetailCartFragment
import com.bda.quickpay_lib.ui.detailProduct.DetailProductFragment
import com.bda.quickpay_lib.ui.detailVoucher.DetailVoucherFragment
import com.bda.quickpay_lib.ui.quickPay.QuickPayFragment
import com.bda.quickpay_lib.utils.Functions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_quickpay.*


class MainQuickPayDialog(
    private val activity: Activity,
    private val fptId: String,
    private val phone: String,
    private val product: Product,
    private val voucherId: String,
    private val platform: String,
    private val viewMode: Int,
    private val onQuickPayExit: () -> Unit
) : DialogFragment(),
    QuickPayFragment.onQuickpayListener,
    BannerFragment.onOmniBannerListener,
    DetailCartFragment.onAddToCartListener,
    DetailProductFragment.onDetailProductListener,
    AddToCartSuccessFragment.onAddToCartSuccessListener,
    DetailVoucherFragment.onDetailVoucherListener,
    OrderSuccessFragment.onQuickpayListener {

    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null
    private var detailProductFragment: DetailProductFragment? = null
    private var bannerFragment: BannerFragment? = null
    private var detailVoucherFragment: DetailVoucherFragment? = null
    private var addToCartFragment: DetailCartFragment? = null
    private var quickPayFragment: QuickPayFragment? = null
    private var successFragment: OrderSuccessFragment? = null
    private var addToCartSuccessFragment: AddToCartSuccessFragment? = null

    private var isShowing: Boolean = false
    private var quickPayQuality: Int = 1
    private var addToCartQuality: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailProductFragment = DetailProductFragment.newInstance()
        bannerFragment = BannerFragment()
        detailVoucherFragment = DetailVoucherFragment()
        quickPayFragment = QuickPayFragment.newInstance(platform)
        successFragment = OrderSuccessFragment.newInstance()
        addToCartSuccessFragment = AddToCartSuccessFragment.newInstance()
        addToCartFragment = DetailCartFragment.newInstance()

        detailProductFragment?.setListener(this)
        bannerFragment?.setListener(this)
        detailVoucherFragment?.setListener(this)
        quickPayFragment?.setListener(this)
        successFragment?.setListener(this)
        addToCartSuccessFragment?.setListener(this)
        addToCartFragment?.setListener(this)

        if (viewMode == VIEW_QUICK_PAY_MODE) {
            val productString = Gson().toJson(product)
            detailProductFragment?.let {
                var bundle = bundleOf(
                    "STR_FPT_PLAY_ID" to fptId,
                    "STR_PHONE" to phone,
                    "STR_PRODUCT" to productString,
                )
                it.arguments = bundle
                loadFragment(it, R.id.container_body, true)
            }
        } else if (viewMode == VIEW_VOUCHER_MODE) {
            detailVoucherFragment?.let {
                var bundle = bundleOf(
                    "STR_FPT_PLAY_ID" to fptId,
                    "STR_PHONE" to phone,
                )
                it.arguments = bundle
                loadFragment(it, R.id.container_body, true)
            }
        } else {
            bannerFragment?.let {
                var bundle = bundleOf(
                    "STR_FPT_PLAY_ID" to fptId,
                    "STR_PHONE" to phone,
                )
                it.arguments = bundle
                loadFragment(it, R.id.container_body, true)
            }
        }
        return inflater.inflate(R.layout.dialog_quickpay, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity, theme) {
            override fun onBackPressed() {
                var fragment = fm?.findFragmentById(R.id.container_body)
                if (fragment is QuickPayFragment) {
                    detailProductFragment?.let {
                        quickPayQuality = fragment.getQuality()
                        it.setIsFistOpen(false)
                        loadFragment(it, R.id.container_body, true)
                    }
                } else if (fragment is DetailCartFragment) {
                    detailProductFragment?.let {
                        addToCartQuality = fragment.getProduct().order_quantity
                        loadFragment(it, R.id.container_body, true)
                    }
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {

            // Set gravity of dialog
            dialog.setCanceledOnTouchOutside(true)
            val window = dialog.window
            val wlp = window!!.attributes
            wlp.gravity = Gravity.BOTTOM
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes = wlp
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val lp = window.attributes
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dialog.window!!.attributes = lp
            dialog?.setOnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        val f = fm?.findFragmentById(R.id.container_body)
                        if (f != null && container_body.visibility == View.VISIBLE
                            && (f is ChooseVoucherFragment)
                        ) {
                            f.myOnkeyDown(keyCode, event)
                        }

                        if (f != null && container_body.visibility == View.VISIBLE
                            && (f is DetailProductFragment)
                        ) {
                            f.myOnkeyDown(keyCode, event)
                        }
                    }

                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        val f = fm?.findFragmentById(R.id.container_body)
                        if (f != null && container_body.visibility == View.VISIBLE
                            && (f is ChooseVoucherFragment)
                        ) {
                            f.myOnkeyDown(keyCode, event)
                        }

                        if (f != null && container_body.visibility == View.VISIBLE
                            && (f is DetailProductFragment)
                        ) {
                            f.myOnkeyDown(keyCode, event)
                        }
                    }
                }
                false
            }
        }
    }

    fun getFManager(): FragmentManager {
        if (fm == null)
            fm = this@MainQuickPayDialog.childFragmentManager
        return fm!!
    }

    open fun loadFragment(fragment: Fragment, layout: Int, isAnimation: Boolean) {
        if (fm == null) {
            fm = getFManager();
            ft = fm!!.beginTransaction();
            ft!!.add(layout, fragment, fragment.javaClass.name)
        } else {
            val tmp =
                fm!!.findFragmentByTag(fragment.javaClass.name)
            if (tmp != null && tmp.isVisible) {
                return
            }

            ft = fm!!.beginTransaction()
            if (isAnimation) {
                ft!!.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            }
            ft!!.replace(layout, fragment, fragment.javaClass.name)
            if (isAnimation) {
                ft!!.addToBackStack(fragment.javaClass.name)
            }
        }
        ft!!.commit()
    }

    override fun onOrderSuccessConfirmClick() {
        dialog?.dismiss()
    }

    override fun onSubmitOrderSuccess(orderId: String, product: Product, totalPrice: String) {
        val bundle = bundleOf(
            "STR_ORDER_ID" to orderId,
            "STR_PRODUCT_QUANTITY" to product.order_quantity,
            "STR_PRODUCT_NAME" to product.name,
            "STR_PRODUCT_SUPPLIER_TIME" to product.supplier?.shipping_time,
            "STR_PRODUCT_TOTAL_PRICE" to totalPrice
        )
        successFragment?.arguments = bundle
        successFragment?.let {
            loadFragment(it, R.id.container_body, true)
        }
    }

    override fun onBuyProductClick(product: Product, user: CheckCustomerResponse) {
        quickPayFragment?.let {
            product.order_quantity = quickPayQuality
            it.setProduct(product)
            it.setUser(user)
            loadFragment(it, R.id.container_body, true)
        }
    }

    override fun onTurnOffAdClick(product: Product, user: CheckCustomerResponse) {
        this.dismiss()
    }

    override fun onAddToCartClick(product: Product, user: CheckCustomerResponse) {
        addToCartFragment?.let {
            product.order_quantity = addToCartQuality
            it.setProduct(product)
            it.setUser(user)
            loadFragment(it, R.id.container_body, true)
        }
    }

    override fun onTimeoutComplete() {
        this.dismiss()
    }

    override fun onCompleteAddToCartClick(product: Product) {
        detailProductFragment?.let {
            addToCartQuality = 1
            loadFragment(it, R.id.container_body, true)
        }
    }

    override fun onSubmitAddToCartSuccess(product: Product) {
        addToCartSuccessFragment?.let {
            it.setProduct(product)
            loadFragment(it, R.id.container_body, true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowing = true
    }

    override fun onDestroy() {
        super.onDestroy()
        isShowing = false
        onQuickPayExit.invoke()
    }

    fun isDialogShowing(): Boolean {
        return isShowing
    }

    override fun onReceiveVoucherClick(voucherId: String) {
        Functions.showPopupVoucherSuccess(requireActivity())
    }

    override fun onSkipVoucherClick() {
        this?.dismiss()
    }

    companion object {
        const val VIEW_QUICK_PAY_MODE = 0
        const val VIEW_VOUCHER_MODE = 1
        const val VIEW_BANNER_MODE = 2
    }

    override fun onAdsClick(product: Product) {
        val productString = Gson().toJson(product)
        detailProductFragment?.let {
            var bundle = bundleOf(
                "STR_FPT_PLAY_ID" to fptId,
                "STR_PHONE" to phone,
                "STR_PRODUCT" to productString,
            )
            it.arguments = bundle
            loadFragment(it, R.id.container_body, true)
        }
    }

}