package com.rubenquadros.apitest.view

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.base.BaseActivity
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.databinding.ActivityHomeBinding
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import com.rubenquadros.apitest.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*

class Home : BaseActivity(), IActivityCallBacks {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupBinding()
        this.setupToolbar()
        this.readData()
    }

    private fun setupBinding() {
        val homeBinding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        homeViewModel = ViewModelProviders.of(this, viewModeFactory)[HomeViewModel::class.java]
        homeViewModel.setListener(this)
        homeBinding.homeHandler = homeViewModel
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        toolbarTitle.text = this.getString(R.string.home)
    }

    private fun readData() {
        if(intent.getStringExtra(ApplicationConstants.INSERT_SUCCESS) != null) {
            ApplicationUtility.showDialog(this, getString(R.string.insert_succ), getString(R.string.insert_succ_msg), getString(R.string.ok))
        }
        if(intent.getStringExtra(ApplicationConstants.UPDATE_SUCCESS) != null) {
            ApplicationUtility.showDialog(this, getString(R.string.update_succ), getString(R.string.update_succ_msg), getString(R.string.ok))
        }
    }

    override fun onSuccess(message: String) {
        when(message) {
            ApplicationConstants.INSERT_CLICKED -> {
                startActivity(Intent(this, InsertData::class.java))
            }
            ApplicationConstants.FETCH_CLICKED -> {
                startActivity(Intent(this, FetchData::class.java))
            }
        }
    }

    override fun onFailed(message: String) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
