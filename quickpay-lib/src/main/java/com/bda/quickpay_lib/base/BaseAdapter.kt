package com.bda.quickpay_lib.base

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(val mActivity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mInflater: LayoutInflater =
        mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun getLayoutInflater() = mInflater
}