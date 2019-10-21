package com.rubenquadros.apitest.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.adapter.RecViewAdapter
import com.rubenquadros.apitest.base.BaseActivity
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.callbacks.IDetailCallBack
import com.rubenquadros.apitest.data.model.fetch.Datum
import com.rubenquadros.apitest.data.model.fetch.FetchResponse
import com.rubenquadros.apitest.databinding.ActivityFetchDataBinding
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import com.rubenquadros.apitest.viewmodel.FetchViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*

class FetchData : BaseActivity(), IActivityCallBacks, IDetailCallBack {

    @BindView(R.id.parent) lateinit var parentView: ConstraintLayout
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.cardView) lateinit var mCardView: CardView
    @BindView(R.id.headingTv) lateinit var headingTV: TextView

    private lateinit var fetchDataViewModel: FetchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupBinding()
        this.setupToolbar()
        this.observeData()
        this.setupRecView()
    }

    private fun setupBinding() {
        val activityFetchDataBinding = DataBindingUtil.setContentView<ActivityFetchDataBinding>(this, R.layout.activity_fetch_data)
        fetchDataViewModel = ViewModelProviders.of(this, viewModeFactory)[FetchViewModel::class.java]
        fetchDataViewModel.setListener(this)
        activityFetchDataBinding.fetchHandler = fetchDataViewModel
        ButterKnife.bind(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbarTitle.text = this.getString(R.string.fetch_data)
    }

    private fun observeData() {
        this.fetchDataViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })

        this.fetchDataViewModel.getFetchResponse().observe(this, Observer { it?.let { parseFetchResponse(it) } })
        this.fetchDataViewModel.getFetchError().observe(this, Observer { it?.let { parseFetchError(it) } })
    }

    private fun setupRecView() {
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
    }

    private fun parseFetchResponse(fetchResponse: FetchResponse?) {
        shouldShowProgress(false)
        if(fetchResponse != null && fetchResponse.metadata?.responseCode == ApplicationConstants.STATUS_OK) {
            enableRecView()
            updateUI(fetchResponse)
        }
    }

    private fun parseFetchError(message: String) {
        shouldShowProgress(false)
        ApplicationUtility.showSnack(message, parentView, this.getString(R.string.ok))
    }

    private fun enableRecView() {
        headingTV.visibility = View.GONE
        mCardView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    private fun updateUI(fetchResponse: FetchResponse?) {
        val adapter = RecViewAdapter(fetchResponse!!, this, this)
        mRecyclerView.adapter = adapter
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
            ApplicationConstants.EDIT -> {

            }
        }
    }

    override fun onFailed(message: String) {
        when(message) {
            ApplicationConstants.EMPTY_FIELDS -> {
                ApplicationUtility.showSnack(getString(R.string.any_field), parentView, getString(R.string.ok))
            }
            ApplicationConstants.MANY_FIELDS -> {
                ApplicationUtility.showSnack(getString(R.string.any_field), parentView, getString(R.string.ok))
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

    override fun getDetail(datum: Datum?) {
        if(datum != null) {
            val intent = Intent(this, Edit::class.java)
            intent.putExtra(ApplicationConstants.NAME, datum.name)
            intent.putExtra(ApplicationConstants.EMAIL, datum.email)
            intent.putExtra(ApplicationConstants.CONTACT, datum.contact)
            intent.putExtra(ApplicationConstants.USERNAME, datum.username)
            intent.putExtra(ApplicationConstants.ID, datum.id)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }
}
