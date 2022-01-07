package com.bda.quickpay_lib.ui.quickPay

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.base.BaseFragment
import com.bda.quickpay_lib.ui.dialog.KeyboardDialog
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfEditText
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.fragment_quickpay.*

class QuickPayFragment(private val platform: String) : BaseFragment(), QuickPayContract.View {

    var mListener: onQuickpayListener? = null
    private var product: Product? = null
    private var user: CheckCustomerResponse? = null
    private var presenter: QuickPayPresenter? = null
    private var requestCode: Int = REQUEST_VOICE_NAME_CODE

    private var rlContainer: RelativeLayout? = null
    private var edt_phone: SfEditText? = null
    private var edt_name: SfEditText? = null
    private var bn_voice_name: RelativeLayout? = null
    private var bnPlus: RelativeLayout? = null
    private var bnMinus: RelativeLayout? = null
    private var bn_voice_phone: RelativeLayout? = null
    private var icPlus: ImageView? = null
    private var icMinus: ImageView? = null
    private var image_bn_voice_name: ImageView? = null
    private var image_bn_voice_phone: ImageView? = null
    private var btnConfirm: RelativeLayout? = null
    private var tvConfirm: SfTextView? = null
    private var tvTitle: SfTextView? = null
    private var tvQuanlity: SfTextView? = null
    private var tvHint: SfTextView? = null
    private var llProduct: LinearLayout? = null
    private var llQuantity: RelativeLayout? = null
    private var defaultSelectQuality = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            presenter = QuickPayPresenter(this@QuickPayFragment, it)
        }
        val view = inflater.inflate(R.layout.fragment_quickpay, container, false)
        edt_name = view.findViewById(R.id.edt_name)
        edt_phone = view.findViewById(R.id.edt_phone)
        bn_voice_name = view.findViewById(R.id.bn_voice_name)
        image_bn_voice_name = view.findViewById(R.id.image_bn_voice_name)
        bn_voice_phone = view.findViewById(R.id.bn_voice_phone)
        image_bn_voice_phone = view.findViewById(R.id.image_bn_voice_phone)
        icMinus = view.findViewById(R.id.icMinus)
        icPlus = view.findViewById(R.id.icPlus)
        btnConfirm = view.findViewById(R.id.bn_confirm)
        bnPlus = view.findViewById(R.id.btn_plus)
        bnMinus = view.findViewById(R.id.btn_minus)
        tvConfirm = view.findViewById(R.id.tvConfirm)
        tvQuanlity = view.findViewById(R.id.tvQuantity)
        rlContainer = view.findViewById(R.id.rlContainer)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvHint = view.findViewById(R.id.tvHint)
        llProduct = view.findViewById(R.id.llProduct)
        llQuantity = view.findViewById(R.id.llQuantity)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arrayListOf("box2019", "box2020", "box2021").contains(platform)) {
            bn_voice_name?.visibility = View.VISIBLE
            bn_voice_phone?.visibility = View.VISIBLE
        } else {
            bn_voice_name?.visibility = View.GONE
            bn_voice_phone?.visibility = View.GONE
        }

        edt_phone?.setText(user?.data?.phone)
        edt_name?.setText(user?.data?.name)
        tvName?.text = product?.name
        price?.text = Functions.formatMoney(product!!.price)
        img_product_quick_pay?.let {
            Functions.loadImage(product!!.imageCover, it)
        }

        Handler().postDelayed({
            bn_confirm.requestFocus()
        }, 100)

        edt_name?.apply {
            setOnClickListener {
                if (edt_name != null) {
                    val keyboardDialog = KeyboardDialog(
                        KeyboardDialog.KEYBOARD_NAME_TYPE,
                        edt_name?.text.toString(),
                        edt_name!!, edt_name
                    )
                    keyboardDialog.show(childFragmentManager, keyboardDialog.tag)
                }
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (edt_name != null)
                    if (hasFocus) {
                        edt_name?.setSelection(edt_name!!.text.toString().length)
                    }
            }

            setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && event.action == KeyEvent.ACTION_DOWN) {
                    Handler().postDelayed({
                        bn_voice_name?.requestFocus()
                    }, 0)
                    return@OnKeyListener true
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_UP && event.action == KeyEvent.ACTION_DOWN) {
                    Handler().postDelayed({
                        edt_name?.requestFocus()
                    }, 0)
                    return@OnKeyListener true
                }
                false
            })
        }

        edt_phone?.apply {
            setOnClickListener {
                val keyboardDialog = KeyboardDialog(
                    KeyboardDialog.KEYBOARD_PHONE_TYPE,
                    edt_phone?.text.toString(),
                    edt_phone!!, edt_phone
                )
                keyboardDialog.show(childFragmentManager, keyboardDialog.tag)
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    edt_phone?.setSelection(edt_phone!!.text.toString().length)
                }
            }

            setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && event.action == KeyEvent.ACTION_DOWN) {
                    Handler().postDelayed({
                        bn_voice_phone?.requestFocus()
                    }, 0)
                    return@OnKeyListener true
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_UP && event.action == KeyEvent.ACTION_DOWN) {
                    Handler().postDelayed({
                        edt_name?.requestFocus()
                    }, 0)
                    return@OnKeyListener true
                }
                false
            })
        }

        bn_voice_name?.setOnClickListener {
            this.requestCode = REQUEST_VOICE_NAME_CODE
            requestVoicePermission()
        }

        bn_voice_phone?.setOnClickListener {
            this.requestCode = REQUEST_VOICE_PHONE_CODE
            requestVoicePermission()
        }

        bn_voice_phone?.setOnFocusChangeListener { v, hasFocus ->
            image_bn_voice_phone?.isSelected = hasFocus
        }

        bn_voice_name?.setOnFocusChangeListener { v, hasFocus ->
            image_bn_voice_name?.isSelected = hasFocus
        }

        btnConfirm?.setOnClickListener {
            if (edt_name?.text.toString().isNullOrBlank()) {
                Functions.showMessage(requireContext(), "Vui lòng nhập họ tên")
                return@setOnClickListener
            }
            if (edt_phone?.text.toString().isNullOrBlank()) {
                Functions.showMessage(requireContext(), "Vui lòng nhập số điện thoại")
                return@setOnClickListener
            }
            if (edt_phone?.text!!.length > 9) {
                if (product != null) {
                    product!!.order_quantity = defaultSelectQuality
                    presenter?.submitOrder(
                        customerId = user!!.data.uid,
                        name = user!!.data.name,
                        phone = user!!.data.phone,
                        voucherCode = "",
                        voucherId = "",
                        requestType = 2,
                        products = arrayListOf(product!!)
                    )
                } else {
                    Functions.showMessage(
                        requireContext(),
                        "Chức năng đang được bảo trì, Vui lòng liện hệ sau"
                    )
                    return@setOnClickListener
                }
            } else {
                Functions.showMessage(requireContext(), "Số điện thoại không đúng")
                return@setOnClickListener
            }
        }

        btnConfirm?.setOnFocusChangeListener { _, hasFocus ->
            tvConfirm?.isSelected = hasFocus
        }

        bnMinus?.setOnFocusChangeListener { _, hasFocus ->
            icMinus?.isSelected = hasFocus
        }

        bnPlus?.setOnFocusChangeListener { _, hasFocus ->
            icPlus?.isSelected = hasFocus
        }

        bnPlus?.setOnClickListener {
            defaultSelectQuality++
            tvQuanlity?.text = defaultSelectQuality.toString()
        }

        bnMinus?.setOnClickListener {
            defaultSelectQuality--
            if (defaultSelectQuality == 0) {
                defaultSelectQuality = 1
            }
            tvQuanlity?.text = defaultSelectQuality.toString()
        }

    }

    fun setListener(listenr: onQuickpayListener) {
        mListener = listenr
    }

    interface onQuickpayListener {
        fun onSubmitOrderSuccess(orderId: String, product: Product, totalPrice: String)
    }

    override fun onResume() {
        super.onResume()
        defaultSelectQuality = product!!.order_quantity
        tvQuanlity?.text = defaultSelectQuality.toString()
        Handler().postDelayed({ bn_confirm?.requestFocus() }, 100)
    }

    private fun requestVoicePermission() {
        activity?.let {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ),
                REQUEST_VOICE_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.let {
            if (requestCode == REQUEST_VOICE_PERMISSIONS_REQUEST_CODE) {
                if (ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    voiceSpeechInput(this.requestCode)
                }
            }
        }
    }

    private fun voiceSpeechInput(requestCode: Int) {
        this.let {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("AAA", "checkSelfPermission: Done")
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM

                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN")
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                startActivityForResult(intent, requestCode)
            } else {
                requestVoicePermission()
            }
        }
    }

    override fun enableDarkMode() {
        rlContainer?.setBackgroundColor(requireActivity().getColor(R.color.bg_dark))
        tvTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        tvHint?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        llProduct?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_dark)
        tvName?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        price?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        edt_name?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        edt_name?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        edt_name?.setHintTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        edt_phone?.setHintTextColor(requireActivity().getColor(R.color.textSecondaryColor_dark))
        edt_phone?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        edt_phone?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        bn_voice_name?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        bn_voice_phone?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        tvQuantityTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        btn_plus?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        btn_minus?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_dark)
        llQuantity?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_dark)
        tvQuantity?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_dark))
        btnConfirm?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_dark)
        tvConfirm?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_dark))
        icMinus!!.setColorFilter(requireActivity().getColor(R.color.color_white))
        icPlus!!.setColorFilter(requireActivity().getColor(R.color.color_white))
        image_bn_voice_name!!.setColorFilter(requireActivity().getColor(R.color.color_white))
        image_bn_voice_phone!!.setColorFilter(requireActivity().getColor(R.color.color_white))
    }

    override fun disableDarkMode() {
        rlContainer?.setBackgroundColor(requireActivity().getColor(R.color.bg_default))
        tvTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        tvHint?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        llProduct?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_default)
        tvName?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        price?.setTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        edt_name?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)
        edt_phone?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)

        edt_name?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        edt_name?.setHintTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))

        edt_phone?.setHintTextColor(requireActivity().getColor(R.color.textSecondaryColor_default))
        edt_phone?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        bn_voice_name?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)
        bn_voice_phone?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)
        tvQuantityTitle?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        btn_plus?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)
        btn_minus?.background =
            requireActivity().getDrawable(R.drawable.background_edit_selector_default)
        llQuantity?.background = requireActivity().getDrawable(R.drawable.ic_num_gray_default)
        tvQuantity?.setTextColor(requireActivity().getColor(R.color.textPrimaryColor_default))
        btnConfirm?.background =
            requireActivity().getDrawable(R.drawable.background_button_selector_default)
        tvConfirm?.setTextColor(requireActivity().getColorStateList(R.color.selector_button_header_default))
        icMinus?.setColorFilter(requireActivity().getColor(R.color.color_black))
        icPlus?.setColorFilter(requireActivity().getColor(R.color.color_black))
        image_bn_voice_name!!.setColorFilter(requireActivity().getColor(R.color.color_black))
        image_bn_voice_phone!!.setColorFilter(requireActivity().getColor(R.color.color_black))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                when (requestCode) {
                    REQUEST_VOICE_NAME_CODE -> {
                        edt_name?.setText(result?.get(0)?.toUpperCase())
                    }
                    REQUEST_VOICE_PHONE_CODE -> {
                        edt_phone?.setText(result?.get(0)?.replace(" ", ""))
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_VOICE_NAME_CODE = 123
        const val REQUEST_VOICE_PHONE_CODE = 321
        const val REQUEST_VOICE_PERMISSIONS_REQUEST_CODE = 1

        @JvmStatic
        fun newInstance(platform: String) =
            QuickPayFragment(platform)
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

    fun getQuality(): Int {
        return defaultSelectQuality
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun sendOrderSuccess(orderId: String) {
        product?.let {
            val total = it.price * it.order_quantity
            mListener?.onSubmitOrderSuccess(
                orderId,
                it,
                Functions.formatMoney(total)
            )
        }
    }

    override fun sendFailed(message: String) {
        activity?.let {
            Functions.showMessage(it, message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.disposeAPI()
    }
}