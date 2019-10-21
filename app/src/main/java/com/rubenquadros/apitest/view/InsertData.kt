package com.rubenquadros.apitest.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.apitest.BuildConfig
import com.rubenquadros.apitest.R
import com.rubenquadros.apitest.base.BaseActivity
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.model.insert.InsertResponse
import com.rubenquadros.apitest.data.model.upload.UploadResponse
import com.rubenquadros.apitest.databinding.ActivityInsertDataBinding
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import com.rubenquadros.apitest.viewmodel.InsertViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.File
import java.io.IOException

@Suppress("PrivatePropertyName", "DEPRECATION")
class InsertData : BaseActivity(), IActivityCallBacks {

    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.parent) lateinit var parentView: ConstraintLayout
    @BindView(R.id.insertButton) lateinit var insertButton: Button

    private lateinit var insertViewModel: InsertViewModel
    private lateinit var DIR: String
    private lateinit var photoFile: File
    private val ACCESS_CAMERA = 10210
    private val CAMERA_RC = 10201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupBinding()
        this.setupToolbar()
        this.getDir()
        this.observeData()
    }

    private fun setupBinding() {
        val activityInsertBinding = DataBindingUtil.setContentView<ActivityInsertDataBinding>(this, R.layout.activity_insert_data)
        insertViewModel = ViewModelProviders.of(this, viewModeFactory)[InsertViewModel::class.java]
        insertViewModel.setListener(this)
        activityInsertBinding.insertHandler = insertViewModel
        ButterKnife.bind(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbarTitle.text = this.getString(R.string.insert_data)
    }

    private fun getDir() {
        DIR = Environment.getExternalStorageDirectory().absolutePath + ApplicationConstants.MAIN_DIR
    }

    private fun observeData() {
        this.insertViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })

        //observe upload image
        this.insertViewModel.getUploadResponse().observe(this, Observer { it?.let { parseUploadResponse(it) } })
        this.insertViewModel.getUploadError().observe(this, Observer { it?.let { parseUploadError(it) } })

        //observe insert data
        this.insertViewModel.getInsertResponse().observe(this, Observer { it?.let { parseInsertResponse(it) } })
        this.insertViewModel.getInsertError().observe(this, Observer { it?.let { parseInsertError(it) } })
    }

    private fun parseUploadResponse(uploadResponse: UploadResponse?) {
        shouldShowProgress(false)
        /*if(uploadResponse!=null) {
            if(uploadResponse.metadata != null ) {
                if(uploadResponse.metadata?.responseCode!! == ApplicationConstants.STATUS_OK) {
                    enableRegistration()
                }
            }
        }*/
        enableRegistration()
    }

    private fun parseUploadError(message: String) {
        shouldShowProgress(false)
        ApplicationUtility.showSnack(message, parentView, this.getString(R.string.ok))
    }

    private fun parseInsertResponse(insertResponse: InsertResponse?) {
        shouldShowProgress(false)
        if(insertResponse?.metadata != null && insertResponse.metadata!!.responseCode == ApplicationConstants.STATUS_OK) {
            val intent = Intent(this, Home::class.java)
            intent.putExtra(ApplicationConstants.INSERT_SUCCESS, ApplicationConstants.INSERT_SUCCESS)
            startActivity(intent)
        }
    }

    private fun parseInsertError(message: String) {
        shouldShowProgress(false)
        ApplicationUtility.showSnack(message, parentView, this.getString(R.string.ok))
    }

    private fun enableRegistration() {
        insertButton.isClickable = true
        insertButton.isEnabled = true
        insertButton.setBackgroundColor(this.resources.getColor(R.color.colorPrimary))
        insertButton.setTextColor(this.resources.getColor(R.color.colorWhite))
    }

    private fun dispatchCameraIntent() {
        try {
            photoFile = ApplicationUtility.createImageFile(this)
        } catch (ex: IOException) {
            ApplicationUtility.showSnack(this.getString(R.string.generic_err), parentView, this.getString(
                R.string.ok
            ))
            return
        }
        val photoURI =
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ApplicationConstants.PROVIDER, photoFile)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(cameraIntent, CAMERA_RC)
    }

    private fun shouldShowProgress(isLoading: Boolean) {
        if(isLoading) {
            mProgressBar.visibility = View.VISIBLE
        }else {
            mProgressBar.visibility = View.GONE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            ACCESS_CAMERA -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    ApplicationUtility.showSnack(
                        this.getString(R.string.camera_permission),
                        parentView,
                        this.getString(R.string.ok)
                    )
                } else {
                    ApplicationUtility.checkDir(DIR)
                    dispatchCameraIntent()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                CAMERA_RC -> {
                    this.parseUploadResponse(UploadResponse())
                    //this.registerViewModel.uploadImage(photoFile.absolutePath)
                }
            }
        }
    }

    override fun onSuccess(message: String) {
        when(message) {
            ApplicationConstants.UPLOAD_IMG -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        ACCESS_CAMERA
                    )
                } else {
                    ApplicationUtility.checkDir(DIR)
                    dispatchCameraIntent()
                }
            }
            ApplicationConstants.INSERT_CLICKED -> {
                ApplicationUtility.hideSoftKeyboard(this)
                this.insertViewModel.insertData()
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

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }
}
