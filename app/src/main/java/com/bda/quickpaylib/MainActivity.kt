package com.bda.quickpaylib

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bda.quickpay_lib.QuickPayDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttn = findViewById<Button>(R.id.btnQuick)
        var isDarkMode = false
        buttn.setOnClickListener {
            val aa = QuickPayDialog.Builder()
                .setUserId("92225949")
                .setUserPhone("0982763842")
                .setProductId("0x14ba1")
                .setPlatform("box2019")
                .setXApiKey("OGYRcR4E6SpGC0PB")
                .setXApiKeyTracking("TzptM3vWlQ90XqEb")
                .setIsAppProduction(false)
                .setDarkMode(isDarkMode)
                .setOnQuickPayListener(object : QuickPayDialog.QuickPayListener {
                    override fun onQuickPayExit() {

                    }
                })
                .build()
            if (aa != null && !aa.isPopupShowing()) {
                aa.show(this, supportFragmentManager)
            }
            isDarkMode = !isDarkMode
        }
    }
}