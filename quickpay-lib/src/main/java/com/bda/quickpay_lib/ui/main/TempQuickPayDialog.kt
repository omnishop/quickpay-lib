package com.bda.quickpay_lib.ui.main

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bda.quickpay_lib.R
import com.bda.quickpay_lib.models.LiveStream
import com.bda.quickpay_lib.models.Product
import com.bda.quickpay_lib.models.response.CheckCustomerResponse
import com.bda.quickpay_lib.ui.main.adapter.ItemLiveStreamAdapterV3
import com.bda.quickpay_lib.ui.main.adapter.OthersVideoAdapter
import com.bda.quickpay_lib.ui.main.adapter.TabLiveStreamAdapter
import com.bda.quickpay_lib.utils.Functions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_quickpay.*
import kotlinx.android.synthetic.main.dialog_temp.*


class TempQuickPayDialog() : DialogFragment(), MotionLayout.TransitionListener {
    private var userInfo: CheckCustomerResponse? = null
    private lateinit var presenter: LiveStreamContract.Presenter
    private var fm: FragmentManager? = null
    private var ft: FragmentTransaction? = null

    private var titleLive: String = ""
    private var live: List<LiveStream>? = null

    private var mLiveStream: LiveStream? = null
    private var url = ""

    private val listTab = arrayListOf(/*"info", */"product", "video"/*, "voucher", "comment"*/)
    private var isPlayAnimation = false
    private var currentTab = 0
    private var currentSlectedProduct: Product? = null

    private var othersVideoAdapter: OthersVideoAdapter? = null
    private var itemLiveStreamAdapterV3: ItemLiveStreamAdapterV3? = null
    private var ssaiTimeout: Disposable? = null

    private var bottomTab: TabLiveStreamAdapter? = null
    private var productTab: TabLiveStreamAdapter? = null
    private var videoFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        detailProductFragment = DetailProductFragment.newInstance()
//        detailVoucherFragment = DetailVoucherFragment()
//        quickPayFragment = QuickPayFragment.newInstance(platform)
//        successFragment = OrderSuccessFragment.newInstance()
//        addToCartSuccessFragment = AddToCartSuccessFragment.newInstance()
//        addToCartFragment = DetailCartFragment.newInstance()
//
//        detailProductFragment?.setListener(this)
//        detailVoucherFragment?.setListener(this)
//        quickPayFragment?.setListener(this)
//        successFragment?.setListener(this)
//        addToCartSuccessFragment?.setListener(this)
//        addToCartFragment?.setListener(this)
//
//        if (viewMode == VIEW_QUICK_PAY_MODE) {
//            val productString = Gson().toJson(product)
//            detailProductFragment?.let {
//                var bundle = bundleOf(
//                    "STR_FPT_PLAY_ID" to fptId,
//                    "STR_PHONE" to phone,
//                    "STR_PRODUCT" to productString,
//                )
//                it.arguments = bundle
//                loadFragment(it, R.id.container_body, true)
//            }
//        } else if (viewMode == VIEW_VOUCHER_MODE) {
//            detailVoucherFragment?.let {
//                var bundle = bundleOf(
//                    "STR_FPT_PLAY_ID" to fptId,
//                    "STR_PHONE" to phone,
//                )
//                it.arguments = bundle
//                loadFragment(it, R.id.container_body, true)
//            }
//        }
        return inflater.inflate(R.layout.dialog_temp, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
//                var fragment = fm?.findFragmentById(R.id.container_body)
//                if (fragment is QuickPayFragment) {
//                    detailProductFragment?.let {
//                        quickPayQuality = fragment.getQuality()
//                        it.setIsFistOpen(false)
//                        loadFragment(it, R.id.container_body, true)
//                    }
//                } else if (fragment is DetailCartFragment) {
//                    detailProductFragment?.let {
//                        addToCartQuality = fragment.getProduct().order_quantity
//                        loadFragment(it, R.id.container_body, true)
//                    }
//                } else {
//                    super.onBackPressed()
//                }

                when {
                    getFManager().backStackEntryCount > 1 -> {
                        focusDummyView()

//                        val f = getCurrentFragment()

//                        // todo change, dumb
//                        if (f is AddedProductInCartFragment)
//                            getFManager().popBackStack()
//                        else if (f is SuccessFragment) {
//                            getFManager().popBackStack()
//                            getFManager().popBackStack()
//                        }

                        getFManager().popBackStack()
                    }

                    layout3.visibility == View.VISIBLE -> {
                        hideDetailProduct()
                    }

                    layout_tab.visibility == View.VISIBLE && layout2.visibility != View.VISIBLE -> {
                        setTabVisibility(false)
                    }

                    layout2.visibility == View.VISIBLE -> {
                        if (!isPlayAnimation)
                            m_motion_layout?.let {
                                it.setTransition(R.id.transition_product_reverse)
                                it.setTransitionListener(this@TempQuickPayDialog)
                                it.transitionToEnd()
                            }
                    }

                    else -> {
                        super.onBackPressed()
                    }
                }

            }
        }
    }


    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        isPlayAnimation = true
    }

    // trick
    private var isLoadFirstFragment = false
    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        if (p1 == R.id.start_product || p1 == R.id.start_hiding_tab)
            layout_video.radius = resources.getDimension(R.dimen._8sdp) * p3
        else if (p1 == R.id.end_product)
            layout_video.radius = resources.getDimension(R.dimen._8sdp) * (1 - p3)
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        isPlayAnimation = false

        when {
            p1 == R.id.end_hiding_tab -> {

                updateTab(true)
                when (currentTab) {
                    0 -> { //product
                        Handler().postDelayed({
                            product_list_view.apply {
                                adapter = itemLiveStreamAdapterV3
                            }
                        }, 0)
                    }

                    1 -> { //video
                        Handler().postDelayed({
                            product_list_view.apply {
                                adapter = othersVideoAdapter
                            }
                        }, 0)
                    }
                }

                Handler().postDelayed({
                    product_list_view.requestFocus()
                }, 300)
            }

            (p1 == R.id.end_detail && currentSlectedProduct != null && !isLoadFirstFragment) -> {
                isLoadFirstFragment = true
//                loadFragment(
//                    DetailLiveStreamProductFragment.newInstance(currentSlectedProduct!!),
//                    R.id.layout3,
//                    true
//                )
            }

            layout2.visibility == View.VISIBLE && dummy_view.hasFocus() && layout3.visibility != View.VISIBLE ->
                Handler().postDelayed({
                    product_list_view.requestFocus()
                }, 0)

            p1 == R.id.end_tab -> {
                Handler().postDelayed({
                    tab_recycler?.requestFocus()
                }, 0)
            }
        }
    }

    private fun initial() {
        loadVideo()
        val listProduct = arrayListOf<Product>()
        listProduct.add(Functions.loadProduct(requireContext())!!)
        itemLiveStreamAdapterV3 = ItemLiveStreamAdapterV3(requireActivity(), listProduct)

        dummy_view.setOnClickListener {
            // todo nothing
        }

        name_livestram.text = mLiveStream?.display_name

        itemLiveStreamAdapterV3!!.setOnCallbackListener(object :
            ItemLiveStreamAdapterV3.OnCallBackListener {
            override fun onItemClick(product: Product) {
                if (!isPlayAnimation) {
                    currentSlectedProduct = product
                    m_motion_layout?.let {
                        it.setTransition(R.id.transition_detail)
                        it.setTransitionListener(this@TempQuickPayDialog)
                        it.transitionToEnd()
                    }

//                    if (mLiveStream!!.status == 2)
//                        seekToProductTime(product.time)
                }
            }
        })

        othersVideoAdapter = OthersVideoAdapter(requireActivity(), live!!).apply {
            setOnCallbackListener(object : OthersVideoAdapter.OnCallBackListener {
                override fun onItemClick(live: LiveStream) {
//                    gotoLiveStreamProduct(live.uid)
//                    finish()
                }
            })
        }

        // tab above list product
        productTab = TabLiveStreamAdapter(requireActivity(), listTab).apply {
            setOnCallbackListener(object : TabLiveStreamAdapter.OnCallBackListener {
                override fun onItemClick(p: Int) {
                    currentTab = p
                    updateTab(false)

                    when (listTab[p]) {
                        "product" -> {
                            product_list_view.apply {
                                adapter = itemLiveStreamAdapterV3
                            }

                            Handler().postDelayed({
                                product_list_view.requestFocus()
                            }, 200)
                        }

                        "video" -> {
                            product_list_view.apply {
                                adapter = othersVideoAdapter
                            }

                            Handler().postDelayed({
                                product_list_view.requestFocus()
                            }, 200)
                        }
                    }
                }
            })
        }

        list_tab.apply {
            adapter = productTab
        }

        // in bottom of parent
        bottomTab = TabLiveStreamAdapter(requireActivity(), listTab).apply {
            setOnCallbackListener(object : TabLiveStreamAdapter.OnCallBackListener {
                override fun onItemClick(p: Int) {
                    currentTab = p
                    //tabList.setCurrentIndex(p)
                    updateTab(true)

                    when (listTab[p]) {
                        "product" -> {
                            // trick
                            focusDummyView()
                            removeTransitionMotionLayout()

                            m_motion_layout?.let {

                                it.setTransition(R.id.transition_hiding_tab)
                                it.setTransitionListener(this@TempQuickPayDialog)
                                if (!isPlayAnimation)
                                    it.transitionToEnd()
                            }
                            //setTabVisibility(false)
                        }

                        "video" -> {
                            // trick
                            focusDummyView()
                            removeTransitionMotionLayout()

                            m_motion_layout?.let {
                                it.setTransition(R.id.transition_hiding_tab)
                                it.setTransitionListener(this@TempQuickPayDialog)
                                if (!isPlayAnimation)
                                    it.transitionToEnd()
                            }
                            //setTabVisibility(false)

                        }
                    }
                }
            })
        }

        tab_recycler.apply {
            adapter = bottomTab
        }

        //todo change

        //ImageUtils.loadImage(this, image_supplier, "")
        //detail_supplier_name.setText("")
        //supplier_number_count.setText("")
    }

    private fun updateTab(isClickBottomTabOfParent: Boolean) {
        if (isClickBottomTabOfParent)
            productTab?.setCurrentIndex(currentTab)
        else
            bottomTab?.setCurrentIndex(currentTab)
    }

    fun seekToProductTime(time: Int) {
//        videoFragment?.seekTo((time * 1000).toLong())
    }

    fun focusDummyView() {
        Handler().postDelayed({
            dummy_view?.requestFocus()
        }, 0)
    }

    private fun loadVideo() {
        if (mLiveStream!!.status == 1) {
            if (mLiveStream!!.channel.size > 0) {
                url = mLiveStream!!.channel[0].link
            }
        } else {
            if (mLiveStream!!.video_transcode.isNotEmpty()) {
                url = mLiveStream!!.video_transcode
            }
        }

        val ft = childFragmentManager.beginTransaction()
//        videoFragment = VideoConsumptionWithExoPlayerFragment.newInstance(url)
        videoFragment = Fragment()

        ft.add(R.id.layout_video, videoFragment!!, videoFragment!!.tag)
        ft.commit()
    }

    private fun removeTransitionMotionLayout() {
        m_motion_layout?.setTransition(-1, -1)
    }

//    private fun getCurrentFragment() = getFManager().findFragmentById(layoutToLoadId())

    private fun setTabVisibility(b: Boolean) {
        m_motion_layout?.let {
            it.setTransition(R.id.transition_tab)
            it.addTransitionListener(this)

            if (!isPlayAnimation) {
                if (b)
                    it.transitionToEnd()
                else
                    it.transitionToStart()
            }
        }
    }
    fun getFManager(): FragmentManager {
        if (fm == null)
            fm = this@TempQuickPayDialog.childFragmentManager
        return fm!!
    }

     fun hideDetailProduct() {
        if (!isPlayAnimation) {
            m_motion_layout?.let {
                it.setTransition(R.id.transition_detail)
                it.addTransitionListener(this)
                it.transitionToStart()
            }

            Handler().postDelayed({
                isLoadFirstFragment = false
                getFManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                product_list_view.requestFocus()
            }, 0)
        }
    }
}