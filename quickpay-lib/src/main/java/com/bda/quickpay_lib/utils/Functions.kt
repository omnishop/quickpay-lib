package com.bda.quickpay_lib.utils

import android.animation.Animator
import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.bda.quickpay_lib.R
import kotlinx.android.synthetic.main.dialog_message.*
import java.io.InputStream
import java.net.URL
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object Functions {
    private val suffixes = TreeMap<Long, String>()

    @Synchronized
    fun animateScaleDown(v: View, duration: Long = 200) {
        val currentScaleX = v.scaleX
        val currentScaleY = v.scaleY
        val targetScale = 1f
        val scaleXHolder =
            PropertyValuesHolder.ofFloat(View.SCALE_X, currentScaleX, targetScale)
        val scaleYHolder =
            PropertyValuesHolder.ofFloat(View.SCALE_Y, currentScaleY, targetScale)
        val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXHolder, scaleYHolder)
        scaleAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (targetScale == 0f) {
                    v.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        scaleAnimator.duration = duration
        scaleAnimator.start()
    }

    @Synchronized
    fun animateScaleUp(v: View, scale: Float, duration: Long = 100) {
        v.visibility = View.VISIBLE
        val scaleXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, scale)
        val scaleYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, scale)
        val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXHolder, scaleYHolder)
        scaleAnimator.duration = duration
        scaleAnimator.start()
    }

    fun formatMoney(money: Double): String {
        val formatter = DecimalFormat("#,###đ")
        return formatter.format(money)
    }

    fun format(value: Long): String {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == java.lang.Long.MIN_VALUE) return format(java.lang.Long.MIN_VALUE + 1)
        if (value < 0) return "-" + format(-value)
        if (value < 1000) return value.toString() //deal with easy case

        val e = suffixes.floorEntry(value)
        val divideBy = e.key
        val suffix = e.value

        val truncated = value / (divideBy!! / 10) //the number part of the output times 10
        val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
        return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
    }

    fun showMessage(context: Context, message: String) {
        val mDialogView = inflate(context, R.layout.dialog_message, null) as ViewGroup
        val messageDialog = AlertDialog.Builder(context)
            .setOnCancelListener { }
            .create().apply {
                setView(mDialogView)
                setCanceledOnTouchOutside(true)
                show()
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.attributes!!.windowAnimations = R.style.SlideRightAnimation
                window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                window?.setGravity(Gravity.END)
                window?.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                val lp = window?.attributes
                lp?.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
                this?.window?.attributes = lp
                this?.tvMessage?.text = message

            }
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                messageDialog?.tvTimout?.text = "Đóng " + "(" + (millisUntilFinished / 1000) + ")"
            }

            override fun onFinish() {
                messageDialog?.dismiss()
            }
        }
        timer.start()
    }

    @Synchronized
    fun animateScaleUpLiveStream(v: View, scale: Float, duration: Long = 100) {
        v.visibility = View.VISIBLE
        val scaleXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, scale)
        val scaleYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, scale)
        val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleXHolder, scaleYHolder)
        scaleAnimator.setEvaluator(FloatEvaluator())
        scaleAnimator.interpolator = AccelerateInterpolator()
        scaleAnimator.duration = duration
        scaleAnimator.start()
    }

    fun getShippingTimeBySupplier(t: Int): String {
        val rightNow = Calendar.getInstance()
        val now = rightNow.get(Calendar.HOUR_OF_DAY)

        val data = now + t
        return if (data < 9) {
            "Hôm nay"
        } else if (data >= 20) {
            var dayDuration: Int = data / 24
            if (dayDuration < 1)
                dayDuration = 1
            rightNow.add(Calendar.DAY_OF_YEAR, dayDuration)
            val day: Date = rightNow.time
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            dateFormat.format(day)
        } else {
            "${data}:00 hôm nay"
        }
    }

    fun loadImage(imageLink: String, imageView: ImageView) {
        val imageLink = QuickPayUtils.getImageBaseUrl() + imageLink + "?mode=scale&w=342&h=342"
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            var logo: Bitmap? = null
            try {
                var inputStream: InputStream = URL(imageLink).openStream()
                logo = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            handler.post {
                imageView?.setImageBitmap(logo)
            }
        }
    }

}