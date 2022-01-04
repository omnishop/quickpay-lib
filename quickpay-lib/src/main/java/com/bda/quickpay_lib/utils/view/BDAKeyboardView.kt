package com.bda.quickpay_lib.utils.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.utils.FontUtils
import com.bda.quickpay_lib.utils.Functions

class BDAKeyboardView : LinearLayout, View.OnFocusChangeListener {
    private val row1 = arrayOf(
        Key("q"),
        Key("w"),
        Key("e"),
        Key("r"),
        Key("t"),
        Key("y"),
        Key("u"),
        Key("i"),
        Key("o"),
        Key("p"),
        Key("/"),
        Key("-"),
        Key("_")

    )
    private val row2 = arrayOf(
        Key("a"),
        Key("s"),
        Key("d"),
        Key("f"),
        Key("g"),
        Key("h"),
        Key("j"),
        Key("k"),
        Key("l"),
        Key("@"),
        Key(
            "left",
            R.drawable.abc_left_selector,
            R.drawable.keyboard_border_selector
        ),
        Key(
            "right",
            R.drawable.abc_right_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("Xoá")
    )

    private val row3 = arrayOf(
        Key("z"),
        Key("x"),
        Key("c"),
        Key("v"),
        Key("b"),
        Key("n"),
        Key("m"),
        Key("Dấu cách"),
        Key("Hoàn tất"),
        Key(
            "down",
            R.drawable.abc_next_selector,
            R.drawable.keyboard_border_selector
        )
    )

    private val numberSpace123456789 = arrayOf(
        Key("SPACE"),
        Key("1"),
        Key("2"),
        Key("3"),
        Key("4"),
        Key("5"),
        Key("6"),
        Key("7"),
        Key("8"),
        Key("9"),
        Key("0"),
        Key("abc"),
        Key("#+-"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("XONG")
    )

    private val numberOnly = arrayOf(
        Key("SPACE"),
        Key("1"),
        Key("2"),
        Key("3"),
        Key("4"),
        Key("5"),
        Key("6"),
        Key("7"),
        Key("8"),
        Key("9"),
        Key("0"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("XONG")
    )

    private val characterLine1 = arrayOf(
        Key("SPACE"),
        Key("["),
        Key("]"),
        Key("{"),
        Key("}"),
        Key("#"),
        Key("%"),
        Key("^"),
        Key("*"),
        Key("+"),
        Key("="),
        Key("123"),
        Key("abc"),
        Key("XONG")
    )

    private val characterLine2 = arrayOf(
        Key("_"),
        Key("\\"),
        Key("|"),
        Key("~"),
        Key("<"),
        Key(">"),
        Key("$"),
        Key("."),
        Key(","),
        Key("?"),
        Key("!"),
        Key("'"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        )
    )

    private val nameLine1 = arrayOf(
        Key("SPACE"),
        Key("a"),
        Key("b"),
        Key("c"),
        Key("d"),
        Key("e"),
        Key("f"),
        Key("g"),
        Key("h"),
        Key("i"),
        Key("j"),
        Key("k"),
        Key("l"),
        Key("m"),
        Key("n"),
        Key("o"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        )
    )

    private val nameUpperLine1 = arrayOf(
        Key("SPACE"),
        Key("A"),
        Key("B"),
        Key("C"),
        Key("D"),
        Key("E"),
        Key("F"),
        Key("G"),
        Key("H"),
        Key("I"),
        Key("J"),
        Key("K"),
        Key("L"),
        Key("M"),
        Key("N"),
        Key("O"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        )
    )

    private val nameLine2 = arrayOf(
        Key(
            "upper",
            R.drawable.abc_up_white_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("p"),
        Key("q"),
        Key("r"),
        Key("f"),
        Key("t"),
        Key("u"),
        Key("v"),
        Key("w"),
        Key("x"),
        Key("y"),
        Key("z"),
        Key("123"),
        Key("#+-"),
        Key("XONG")
    )

    private val nameUpperLine2 = arrayOf(
        Key(
            "lower",
            R.drawable.abc_up_white_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("P"),
        Key("Q"),
        Key("R"),
        Key("F"),
        Key("T"),
        Key("U"),
        Key("V"),
        Key("W"),
        Key("X"),
        Key("Y"),
        Key("Z"),
        Key("123"),
        Key("#+-"),
        Key("XONG")
    )

    private val numbers1 = arrayOf(
        Key("1"),
        Key("2"),
        Key("3"),
        Key("4"),
        Key("5"),
        Key("6"),
        Key("7"),
        Key("8"),
        Key("9"),
        Key("0"),
        Key("."),
        Key(","),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        )
    )
    ////////CAPLOCK///////

    private val row4 = arrayOf(
        Key("Q"),
        Key("W"),
        Key("E"),
        Key("R"),
        Key("T"),
        Key("Y"),
        Key("U"),
        Key("I"),
        Key("O"),
        Key("P"),
        Key("-"),
        Key("_"),
        Key(
            "delete",
            R.drawable.abc_delete_white_selector,
            R.drawable.keyboard_border_selector
        )

    )
    private val row5 = arrayOf(
        Key("A"),
        Key("S"),
        Key("D"),
        Key("F"),
        Key("G"),
        Key("H"),
        Key("J"),
        Key("K"),
        Key("L"),
        Key(
            "left",
            R.drawable.abc_left_selector,
            R.drawable.keyboard_border_selector
        ),
        Key(
            "right",
            R.drawable.abc_right_selector,
            R.drawable.keyboard_border_selector
        ),
        Key("Xoá"),
        Key("@")
    )
    private val row6 = arrayOf(
        Key("Z"),
        Key("X"),
        Key("C"),
        Key("V"),
        Key("B"),
        Key("N"),
        Key("M"),
        Key("Dấu cách"),
        Key("Hoàn tất"),
        Key(
            "down",
            R.drawable.abc_next_selector,
            R.drawable.keyboard_border_selector
        )

    )


    private var mCallback: OnKeyboardCallback? = null
    private var mQuery: StringBuffer = StringBuffer()
    private var cursor: Int = 0

    constructor(context: Context?) : super(context) {

    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

    }

    fun initKeyboardNumber() {
        if (!isInEditMode) {
            this.setPadding(
                0,
                (resources.displayMetrics.density * 24).toInt(),
                0,
                (resources.displayMetrics.density * 24).toInt()
            )

            orientation = VERTICAL
            gravity = Gravity.CENTER
            createPhoneView()
            onFocusChangeListener = this
        }
    }

    fun initKeyboardName() {
        if (!isInEditMode) {
            this.setPadding(
                0,
                (resources.displayMetrics.density * 24).toInt(),
                0,
                (resources.displayMetrics.density * 24).toInt()
            )
            orientation = VERTICAL
            gravity = Gravity.CENTER
            createABCUpperView()
            onFocusChangeListener = this
        }
    }

    fun initKeyboardAddress() {
        if (!isInEditMode) {
            this.setPadding(
                0,
                (resources.displayMetrics.density * 18).toInt(),
                0,
                (resources.displayMetrics.density * 18).toInt()
            )
            orientation = VERTICAL
            gravity = Gravity.CENTER
            createABCAddressView()
            onFocusChangeListener = this
        }
    }


    @Suppress("SENSELESS_COMPARISON")
    private fun createRow(vararg cells: Key): LinearLayout {
        val row = LinearLayout(context)
        row.gravity = Gravity.CENTER
        row.orientation = HORIZONTAL

        val textSize: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (resources.displayMetrics.density * 7).toInt()
        } else {
            (resources.displayMetrics.density * 9).toInt()
        }
        val margin = (resources.displayMetrics.density * 7).toInt()
        val height = (resources.displayMetrics.density * 36).toInt()
        val width = (resources.displayMetrics.density * 30).toInt()
        if (cells != null && cells.isNotEmpty()) {
            for (cell in cells) {
                if (cell.resourceId > 0) {
                    val params =
                        LayoutParams( (resources.displayMetrics.density * 60).toInt(), height)
                    val button = createKeyWithIcon(cell, margin, params)
                    row.addView(button)
                } else {
                    val params: LayoutParams = if (cell.name.length > 1) {
                        when (cell.name) {
                            "XONG" -> {
                                LayoutParams(
                                    (resources.displayMetrics.density * 60).toInt(),
                                    height
                                )
                            }
                            "SPACE" -> {
                                LayoutParams(
                                    (resources.displayMetrics.density * 60).toInt(),
                                    height
                                )
                            }
                            else -> {
                                LayoutParams(width, height)
                            }
                        }

                    } else {
                        LayoutParams(width, height)
                    }
                    val tvText = createKeyWithText(cell, textSize, margin, params)
                    if (tvText != null) row.addView(tvText)
                }
            }
        }
        return row
    }

    private fun createKeyWithIcon(
        @NonNull key: Key, margin: Int,
        params: LayoutParams
    ): View {
        val cardView = CardView(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.setMargins(margin, margin, margin, margin)
        cardView.maxCardElevation = resources.displayMetrics.density * 2
        cardView.cardElevation = resources.displayMetrics.density * 2
        cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_translucent
            )
        )
        cardView.radius = resources.displayMetrics.density * 3
        cardView.layoutParams = lp

        val button = ImageButton(context)
        button.isFocusable = true
        button.onFocusChangeListener = this
        button.setBackgroundResource(key.backgroundId)
        button.scaleType = ImageView.ScaleType.CENTER_INSIDE
        button.isFocusableInTouchMode = true
        button.setImageResource(key.resourceId)
        button.setOnClickListener { _ ->
            detectKeyWithCustomAction(
                key.name
            )
        }
        button.layoutParams = params
        button.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                button.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.color_button_focus
                    )
                )
                Functions.animateScaleUp(cardView, 1.09f, 0)
            } else {
                Functions.animateScaleDown(cardView, 0)
                button.setColorFilter(ContextCompat.getColor(context, R.color.color_white))
            }
        }

        cardView.addView(button)
        return cardView
    }


    private fun createKeyWithText(
        @NonNull key: Key, textSize: Int,
        margin: Int,
        params: LayoutParams
    ): View {
        val cardView = CardView(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.setMargins(margin, margin, margin, margin)
        cardView.maxCardElevation = resources.displayMetrics.density * 2
        cardView.cardElevation = resources.displayMetrics.density * 2
        cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.color_translucent
            )
        )
        cardView.radius = resources.displayMetrics.density * 3
        cardView.layoutParams = lp

        val button = TextView(context)
        button.text = key.name
        button.textSize = textSize.toFloat()
        button.onFocusChangeListener = this
        button.typeface = FontUtils.getFontSFMedium(context.assets)
        val textColorId = ContextCompat.getColorStateList(
            context, R.color.color_white
        )
        button.setTextColor(textColorId)
        button.setBackgroundResource(key.backgroundId)
        button.isFocusable = true
        button.gravity = Gravity.CENTER
        button.isFocusableInTouchMode = true
        button.layoutParams = params
        button.setOnClickListener { _ ->
            detectKeyWithCustomAction(
                key.name
            )
        }
        button.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val textColorId = ContextCompat.getColorStateList(
                    context,
                    R.color.color_button_focus
                )
                button.setTextColor(textColorId)
                Functions.animateScaleUp(cardView, 1.09f, 0)
            } else {
                val textColorId = ContextCompat.getColorStateList(
                    context,
                    R.color.color_white
                )
                button.setTextColor(textColorId)
                Functions.animateScaleDown(cardView, 0)
            }
        }
        cardView.addView(button)
        return cardView
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun detectKeyWithCustomAction(text: String) {
        when (text) {
            "123" -> {
                replaceABCToNumberView()
                return
            }
            "upper" -> {
                replaceUpperView()
                return
            }
            "lower" -> {
                replaceLowerView()
                return
            }
            "abc" -> {
                replaceUpperView()
                return
            }
            "#+-" -> {
                replaceCharacterView()
                return
            }
            "Dấu cách" -> {
                mQuery.insert(cursor, " ")
                cursor++
            }
            "SPACE" -> {
                mQuery.insert(cursor, " ")
                cursor++
            }
            "Xoá" -> {
                mQuery.delete(0, mQuery.length)
                cursor = 0
            }
            "left" -> if (mQuery != null && mQuery.isNotEmpty()) {
                cursor = if ((cursor - 1) < 1) 0 else (cursor - 1)
                if (mCallback != null) {
                    mCallback!!.onChangeCursor(cursor)
                    return
                }
            }
            "right" -> if (mQuery != null && mQuery.isNotEmpty()) {
                cursor = if ((cursor + 1) > mQuery.length) mQuery.length else (cursor + 1)
                if (mCallback != null) {
                    mCallback!!.onChangeCursor(cursor)
                    return
                }
            }
            "delete" ->
                if (mQuery != null && mQuery.isNotEmpty() && cursor >= 1) {
                    mQuery.deleteCharAt(cursor - 1)
                    cursor--
                    if (mQuery.length == 0) {
                        cursor = 0
                    }
                }
            "down" -> if (mQuery != null && mQuery.isNotEmpty()) {
                if (mCallback != null) {
                    mCallback!!.onNext()
                }
            }
            "Hoàn tất" -> {
                if (mCallback != null) {
                    mCallback!!.onComplete()
                }
            }
            "XONG" -> {
                if (mCallback != null) {
                    mCallback!!.onComplete()
                }
            }
            else -> {
                mQuery.insert(cursor, text)
                cursor++
            }
        }
        if (mCallback != null) {
            mCallback!!.onSearchSubmit(mQuery.toString(), cursor)
        }
    }

    fun addText(text: String) {
        mQuery.insert(0, text)
        cursor = mQuery.length
        if (mCallback != null) mCallback!!.onSearchSubmit(text, cursor)
    }

    private fun createABCView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*row1))
        layout.addView(createRow(*row2))
        layout.addView(createRow(*row3))
        addView(layout)

    }

    private fun createABCLowerView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*nameLine1))
        layout.addView(createRow(*nameLine2))
        addView(layout)
    }

    private fun createCharacterView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*characterLine1))
        layout.addView(createRow(*characterLine2))
        addView(layout)
    }


    private fun createABCUpperView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*nameUpperLine1))
        layout.addView(createRow(*nameUpperLine2))
        addView(layout)
    }

    private fun createABCAddressView() {

        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*numbers1))
        layout.addView(createRow(*row1))
        layout.addView(createRow(*row2))
        layout.addView(createRow(*row3))
        addView(layout)
    }

    private fun createPhoneView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*numberOnly))
        addView(layout)
    }

    private fun createNumberView() {
        val layout = LinearLayout(context)
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layout.layoutParams = lp
        layout.gravity = Gravity.CENTER
        layout.orientation = VERTICAL
        layout.addView(createRow(*numberSpace123456789))
        addView(layout)
    }

    private fun replaceABCToNumberView() {
        removeAllViews()
        createNumberView()
        requestLayout()
        val focusView = getChildAt(0)
        focusView?.requestFocus()
    }

    private fun replaceUpperView() {
        removeAllViews()
        createABCUpperView()
        requestLayout()
        val focusView = getChildAt(0)
        focusView?.requestFocus()
    }

    private fun replaceLowerView() {
        removeAllViews()
        createABCLowerView()
        requestLayout()
        val focusView = getChildAt(0)
        focusView?.requestFocus()
    }

    private fun replaceCharacterView() {
        removeAllViews()
        createCharacterView()
        requestLayout()
        val focusView = getChildAt(0)
        focusView?.requestFocus()
    }

    private fun replaceNumberToABCView() {
        removeAllViews()
        createABCView()
        requestLayout()
        val focusView = getChildAt(0)
        focusView?.requestFocus()
    }

    private var view: View? = null
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        view = v
        if (mCallback != null) {
            mCallback!!.onSearchFocusable(hasFocus)
        }
    }

    private class Key @JvmOverloads constructor(
        var name: String, @field:DrawableRes @DrawableRes val resourceId: Int = 0,
        @field:DrawableRes val backgroundId: Int = R.drawable.keyboard_border_selector
    ) {
        val isColorResource: Boolean = backgroundId == R.drawable.keyboard_border_selector
    }

    fun setOnKeyboardCallback(callback: OnKeyboardCallback?) {
        mCallback = callback
    }

    interface OnKeyboardCallback {
        fun onSearchSubmit(query: String?, cursor: Int)
        fun onChangeCursor(position: Int)
        fun onComplete()
        fun onNext()
        fun onSearchFocusable(isFocus: Boolean)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event!!.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_0 -> {
                    detectKeyWithCustomAction("0")
                }
                KeyEvent.KEYCODE_1 -> {
                    detectKeyWithCustomAction("1")
                }
                KeyEvent.KEYCODE_2 -> {
                    detectKeyWithCustomAction("2")
                }
                KeyEvent.KEYCODE_3 -> {
                    detectKeyWithCustomAction("3")
                }
                KeyEvent.KEYCODE_4 -> {
                    detectKeyWithCustomAction("4")
                }
                KeyEvent.KEYCODE_5 -> {
                    detectKeyWithCustomAction("5")
                }
                KeyEvent.KEYCODE_6 -> {
                    detectKeyWithCustomAction("6")
                }
                KeyEvent.KEYCODE_7 -> {
                    detectKeyWithCustomAction("7")
                }
                KeyEvent.KEYCODE_8 -> {
                    detectKeyWithCustomAction("8")
                }
                KeyEvent.KEYCODE_9 -> {
                    detectKeyWithCustomAction("9")
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }
}
