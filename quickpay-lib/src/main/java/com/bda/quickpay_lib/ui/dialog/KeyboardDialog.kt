package com.bda.quickpay_lib.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.utils.view.BDAKeyboardView
import kotlinx.android.synthetic.main.dialog_keyboard.*

class KeyboardDialog(
    val keyboardType: Int,
    val content: String,
    val tvContent: EditText,
    val nextEditText: View? = null
) : DialogFragment() {

    private var txtError: TextView? = null
    private var maxLength = 128
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_keyboard, container).apply {
            txtError = findViewById(R.id.tvError)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bn_confirm.setOnFocusChangeListener { v, hasFocus ->
            tvConfirm?.isSelected = hasFocus
        }

        bn_confirm.setOnClickListener {
            if (keyboardType == KEYBOARD_PHONE_TYPE) {
                if (tvContent.text.length < 9) {
                    txtError?.visibility = View.VISIBLE
                    txtError?.text = "Số điện thoại không đúng"
                    return@setOnClickListener
                } else {
                    txtError?.visibility = View.GONE
                }
            }
            dismiss()
            nextEditText?.requestFocus()
        }

        if (keyboard != null) {
            keyboard.removeAllViews()
            when (keyboardType) {
                KEYBOARD_NAME_TYPE -> {
                    tvTitle.text = "Nhập họ và tên"
                    tvHint.text = "Quý khách vui lòng nhập tên người mua hàng"
                    edtName.text = content
                    maxLength = 128
                    keyboard.initKeyboardName()
                }
                KEYBOARD_ADDRESS_TYPE -> {
                    tvTitle.text = "Nhập địa chỉ"
                    tvHint.text = "Quý khách vui lòng nhập địa chỉ giao hàng"
                    edtName.text = content
                    maxLength = 128
                    keyboard.initKeyboardAddress()
                }
                KEYBOARD_PHONE_TYPE -> {
                    tvTitle.text = "Nhập số điện thoại"
                    tvHint.text =
                        "Nhập số điện thoại của quý khách để bộ phận CSKH liên hệ và xác nhận đơn hàng."
                    edtName.text = content
                    maxLength = 10
                    keyboard.initKeyboardNumber()
                }
            }
            keyboard.setOnKeyboardCallback(object : BDAKeyboardView.OnKeyboardCallback {
                override fun onSearchSubmit(query: String?, cursor: Int) {
                    if(query.toString().length <= maxLength){
                        tvContent.setText(query.toString())
                        tvContent.setSelection(cursor)
                        tvError.visibility = View.INVISIBLE
                        edtName.setText(query.toString())
                    }
                }

                override fun onChangeCursor(position: Int) {
                    tvContent.setSelection(position)
                }

                override fun onComplete() {
                    dismiss()
                    nextEditText?.requestFocus()
                }

                override fun onNext() {
                    dismiss()
                }

                override fun onSearchFocusable(isFocus: Boolean) {

                }

            })
            keyboard.addText(content)
            keyboard.requestFocus()
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
            lp.dimAmount = 0.9f
            dialog.setOnKeyListener { _, _, event ->
                /*if (event.action == KeyEvent.ACTION_DOWN) {

                }*/
                false
            }
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dialog.window?.attributes = lp
        }
    }

    companion object {
        const val KEYBOARD_NAME_TYPE = 0
        const val KEYBOARD_PHONE_TYPE = 1
        const val KEYBOARD_ADDRESS_TYPE = 2
    }

}
