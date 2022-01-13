package com.bda.quickpay_lib.ui.main.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.base.BaseAdapter
import com.bda.quickpay_lib.models.LiveStream
import com.bda.quickpay_lib.utils.Functions
import com.bda.quickpay_lib.utils.ImageUtils
import com.bda.quickpay_lib.utils.view.SfTextView
import kotlinx.android.synthetic.main.item_others_live_stream.view.*

class OthersVideoAdapter(
    val activity: Activity,
    val mList: List<LiveStream>
) : BaseAdapter(activity) {

    private lateinit var clickListener: OnCallBackListener
    var isLeftFocus = false
    var numColumn: Int = mList.size
    var isTopFocus: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = getLayoutInflater()
            .inflate(R.layout.item_others_live_stream, parent, false)
        v.isFocusable = true
        v.isFocusableInTouchMode = true
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).setData(mList[position])

        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Functions.animateScaleUpLiveStream(holder.layout_product_content, 1.05f)
                Functions.animateScaleUpLiveStream(holder.revealView, 1f, 200L)
                holder.revealView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.color_white
                    )
                )

                if (position < numColumn)
                    isTopFocus = true

                for (i in 0..itemCount / numColumn)
                    if (position == i * numColumn)
                        isLeftFocus = true

            } else {
                holder.itemView.tv_price.visibility = View.GONE
                Functions.animateScaleDownLiveStream(holder.layout_product_content)
                Functions.animateScaleDownLiveStream(holder.revealView, 100L)
                isLeftFocus = false
                isTopFocus = false
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(mList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_price: SfTextView = view.tv_price
        val layout_product_content: CardView = view.layout_product_content
        val revealView: CardView = view.revealView
        val image: ImageView = view.image
        val name: SfTextView = view.name

        fun setData(product: LiveStream) {
            name.text = product.display_name
            tv_price.visibility = View.GONE
            ImageUtils.loadImage(activity, image, product.image_thumb, ImageUtils.TYPE_COLLECTION)
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    fun setOnCallbackListener(clickListener: OnCallBackListener) {
        this.clickListener = clickListener
    }

    interface OnCallBackListener {
        fun onItemClick(live: LiveStream)
    }
}