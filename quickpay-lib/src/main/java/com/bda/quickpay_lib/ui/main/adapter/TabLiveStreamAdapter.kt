package com.bda.quickpay_lib.ui.main.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.base.BaseAdapter
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.item_tab_live_stream.view.*

class TabLiveStreamAdapter(
    val activity: Activity,
    private val listTab: List<String>
) : BaseAdapter(activity) {
    private lateinit var clickListener: OnCallBackListener
    private var currentSelectedPosition = -1
    var isFirstFocus: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = getLayoutInflater()
            .inflate(R.layout.item_tab_live_stream, parent, false)
        v.isFocusable = true
        v.isFocusableInTouchMode = true
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {

        holder as ViewHolder
        holder.image.setColorFilter(ContextCompat.getColor(activity, R.color.color_484848))

        when (listTab[position]) {
            "info" -> {
                holder.name.text = activity.getString(R.string.txt_info)
            }

            "product" -> {
                holder.name.text = activity.getString(R.string.txt_product)
                holder.image.setImageResource(R.drawable.ic_product)

            }

            "video" -> {
                holder.name.text = activity.getString(R.string.txt_video)
                holder.image.setImageResource(R.drawable.ic_tv)
            }

            "voucher" -> {
                holder.name.text = activity.getString(R.string.txt_voucher)

            }

            "comment" -> {
                holder.name.text = activity.getString(R.string.txt_comment)

            }
        }

        if (currentSelectedPosition == position) {
            holder.background.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    R.color.color_33000000
                )
            )
            holder.name.setNewTextColor(R.color.color_white)
            holder.image.setColorFilter(ContextCompat.getColor(activity, R.color.color_white))
        } else {
            holder.background.setBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    R.color.trans
                )
            )
            holder.name.setNewTextColor(R.color.color_484848)
            holder.image.setColorFilter(ContextCompat.getColor(activity, R.color.color_484848))
        }

        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Functions.animateScaleUpLiveStream(holder.revealView, 1.05f)
                holder.background.setBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.color_FCFCFC
                    )
                )
                holder.name.setNewTextColor(R.color.color_484848)
                holder.image.setColorFilter(ContextCompat.getColor(activity, R.color.color_484848))

                if (position == 0) isFirstFocus = true
            } else {

                Functions.animateScaleDownLiveStream(holder.revealView)
                isFirstFocus = false

                if (currentSelectedPosition == position) {
                    holder.background.setBackgroundColor(
                        ContextCompat.getColor(
                            activity,
                            R.color.color_33000000
                        )
                    )
                    holder.name.setNewTextColor(R.color.color_white)
                    holder.image.setColorFilter(
                        ContextCompat.getColor(
                            activity,
                            R.color.color_white
                        )
                    )
                } else {
                    holder.background.setBackgroundColor(
                        ContextCompat.getColor(
                            activity,
                            R.color.trans
                        )
                    )
                    holder.name.setNewTextColor(R.color.color_484848)
                    holder.image.setColorFilter(
                        ContextCompat.getColor(
                            activity,
                            R.color.color_484848
                        )
                    )
                }
            }
        }

        holder.itemView.setOnClickListener {
            val old = currentSelectedPosition
            currentSelectedPosition = position
            clickListener.onItemClick(position)

            if (old >= 0)
                notifyItemChanged(old)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: SfTextView = view.name
        val revealView: CardView = view.revealView
        val image: ImageView = view.image
        val background: LinearLayout = view.background_content
    }

    override fun getItemCount(): Int {
        return listTab.size
    }

    fun setOnCallbackListener(clickListener: OnCallBackListener) {
        this.clickListener = clickListener
    }

    fun setCurrentIndex(index: Int) {
        val old = currentSelectedPosition
        currentSelectedPosition = index
        notifyItemChanged(old)
        notifyItemChanged(currentSelectedPosition)
    }

    interface OnCallBackListener {
        fun onItemClick(p: Int)
    }
}