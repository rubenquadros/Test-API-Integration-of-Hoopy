package com.rubenquadros.apitest.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.startSplash()
    }

    private fun startSplash() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        Handler().postDelayed({
            startActivity(Intent(this, Home::class.java))
            finish()
        }, 2000)
    }
}
