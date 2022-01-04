package com.bda.quickpay_lib.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.utils.view.BDAKeyboardView
import kotlinx.android.synthetic.main.dialog_keyboard.*

class KeyboardAlertDialog(
    context: Context,
    val keyboardType: Int,
    val content: String,
    val tvContent: EditText,
    val nextEditText: View? = null
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_keyboard)
        if (keyboard != null) {
            keyboard.removeAllViews()
            when (keyboardType) {
                KeyboardDialog.KEYBOARD_NAME_TYPE -> {
                    keyboard.initKeyboardName()
                }
                KeyboardDialog.KEYBOARD_ADDRESS_TYPE -> {
                    keyboard.initKeyboardAddress()
                }
                KeyboardDialog.KEYBOARD_PHONE_TYPE -> {
                    keyboard.initKeyboardNumber()
                }
            }
            keyboard.setOnKeyboardCallback(object : BDAKeyboardView.OnKeyboardCallback {
                override fun onSearchSubmit(query: String?, cursor: Int) {
                    tvContent.setText(query.toString())
                    tvContent.setSelection(cursor)
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
        val dialog = this
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
            lp.dimAmount = 0f
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