package com.rubenquadros.apitest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.base.BaseActivity
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.model.update.UpdateRequest
import com.rubenquadros.apitest.data.model.update.UpdateResponse
import com.rubenquadros.apitest.databinding.ActivityEditBinding
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import com.rubenquadros.apitest.viewmodel.EditViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*

class Edit : BaseActivity(), IActivityCallBacks {

    @BindView(R.id.fullName) lateinit var fullName: EditText
    @BindView(R.id.email) lateinit var mEmail: EditText
    @BindView(R.id.mobile) lateinit var mobile: EditText
    @BindView(R.id.userName) lateinit var userName: EditText
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.parent) lateinit var parentView: ConstraintLayout

    private lateinit var editViewModel: EditViewModel
    private lateinit var updateRequest: UpdateRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupBinding()
        this.setupToolbar()
        this.readData()
        this.setData()
        this.observeData()
    }

    private fun setupBinding() {
        val activityEditBinding = DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit)
        editViewModel = ViewModelProviders.of(this, viewModeFactory)[EditViewModel::class.java]
        editViewModel.setListener(this)
        activityEditBinding.editHandler = editViewModel
        ButterKnife.bind(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbarTitle.text = this.getString(R.string.update_data)
    }

    private fun readData() {
        updateRequest = UpdateRequest()
        if(intent.getStringExtra(ApplicationConstants.NAME) != null) {
            updateRequest.name = intent.getStringExtra(ApplicationConstants.NAME)
        }
        if(intent.getStringExtra(ApplicationConstants.EMAIL) != null) {
            updateRequest.email = intent.getStringExtra(ApplicationConstants.EMAIL)
        }
        if(intent.getStringExtra(ApplicationConstants.CONTACT) != null) {
            updateRequest.contact = intent.getStringExtra(ApplicationConstants.CONTACT)
        }
        if(intent.getStringExtra(ApplicationConstants.USERNAME) != null) {
            updateRequest.username = intent.getStringExtra(ApplicationConstants.USERNAME)
        }
        updateRequest.id = intent.getLongExtra(ApplicationConstants.ID, 0)
    }

    private fun setData() {
        fullName.setText(updateRequest.name)
        mEmail.setText(updateRequest.email)
        mobile.setText(updateRequest.contact)
        userName.setText(updateRequest.username)
    }

    private fun observeData() {
        this.editViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })
        this.editViewModel.getUpdateResponse().observe(this, Observer { it?.let { parseUpdateResponse(it) } })
        this.editViewModel.getUpdateError().observe(this, Observer { it?.let { parseUpdateError(it) } })
    }

    private fun parseUpdateResponse(updateResponse: UpdateResponse?) {
        if(updateResponse != null && updateResponse.metadata?.responseCode == ApplicationConstants.STATUS_OK) {
            val intent = Intent(this, Home::class.java)
            intent.putExtra(ApplicationConstants.UPDATE_SUCCESS, ApplicationConstants.UPDATE_SUCCESS)
            startActivity(intent)
        }
    }

    private fun parseUpdateError(message: String) {
        shouldShowProgress(false)
        ApplicationUtility.showSnack(message, parentView, this.getString(R.string.ok))
    }

    private fun shouldShowProgress(isLoading: Boolean) {
        if(isLoading) {
            mProgressBar.visibility = View.VISIBLE
        }else {
            mProgressBar.visibility = View.GONE
        }
    }

    override fun onSuccess(message: String) {
        when(message) {
            ApplicationConstants.UPDATE -> {
                this.editViewModel.updateData(updateRequest)
            }
        }
    }

    override fun onFailed(message: String) {
        when(message) {
            ApplicationConstants.EMPTY_FIELDS -> {
                ApplicationUtility.showSnack(getString(R.string.all_fields), parentView, getString(R.string.ok))
            }
            ApplicationConstants.INVALID_NAME -> {
                ApplicationUtility.showSnack(getString(R.string.invalid_name), parentView, getString(R.string.ok))
            }
            ApplicationConstants.INVALID_EMAIL -> {
                ApplicationUtility.showSnack(getString(R.string.invalid_email), parentView, getString(R.string.ok))
            }
            ApplicationConstants.INVALID_NUMBER -> {
                ApplicationUtility.showSnack(getString(R.string.invalid_number), parentView, getString(R.string.ok))
            }
        }
    }

}
