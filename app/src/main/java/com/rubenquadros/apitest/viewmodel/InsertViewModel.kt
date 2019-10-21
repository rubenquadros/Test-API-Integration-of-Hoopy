package com.rubenquadros.apitest.viewmodel

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.apitest.base.BaseViewModel
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.model.insert.InsertRequest
import com.rubenquadros.apitest.data.model.insert.InsertResponse
import com.rubenquadros.apitest.data.model.upload.UploadRequest
import com.rubenquadros.apitest.data.model.upload.UploadResponse
import com.rubenquadros.apitest.data.repository.HoopyRepo
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import io.reactivex.Scheduler
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

@Suppress("JoinDeclarationAndAssignment", "UNUSED_PARAMETER")
class InsertViewModel @Inject
constructor(private val hoopyRepo: HoopyRepo,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel(){

    var insertRequest:InsertRequest
    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val uploadResponse: MutableLiveData<UploadResponse> = MutableLiveData()
    private val uploadError: MutableLiveData<String?> = MutableLiveData()
    private val insertResponse: MutableLiveData<InsertResponse> = MutableLiveData()
    private val insertError: MutableLiveData<String?> = MutableLiveData()
    private lateinit var mListener: IActivityCallBacks

    init {
        this.insertRequest = InsertRequest()
    }

    fun uploadClicked(view: View) {
        mListener.onSuccess(ApplicationConstants.UPLOAD_IMG)
    }

    fun uploadImage(path: String) {
        val uploadRequest = UploadRequest()
        uploadRequest.path = path
        this.disposable.addAll(this.hoopyRepo.uploadImage(uploadRequest)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({resources -> uploadImageSuccess(resources)}, {resources -> getUploadError().postValue(resources.message)}))
    }

    fun getUploadResponse() = uploadResponse

    fun getUploadError() = uploadError

    private fun uploadImageSuccess(uploadResponse: UploadResponse?) {
        getUploadResponse().postValue(uploadResponse)
        if(uploadResponse != null && uploadResponse.metadata?.responseCode == ApplicationConstants.STATUS_OK) {
            insertRequest.imageUrl = uploadResponse.urls!![0]
        }
    }

    fun insertClicked(view: View) {
        //empty fields check
        insertRequest.imageUrl = "https://ticketpicuploads.sgp1.digitaloceanspaces.com/media/1566988484006-IMG-20190828-WA0002.jpg"
        if(TextUtils.isEmpty(insertRequest.name) || TextUtils.isEmpty(insertRequest.email) || TextUtils.isEmpty(insertRequest.contact) ||
            TextUtils.isEmpty(insertRequest.username) || TextUtils.isEmpty(insertRequest.imageUrl)) {
            mListener.onFailed(ApplicationConstants.EMPTY_FIELDS)
            return
        }

        //name check
        if(!ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.NAME_REGEX, Pattern.CASE_INSENSITIVE), insertRequest.name!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_NAME)
            return
        }

        //email check
        if(!ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.EMAIL_REGEX, Pattern.CASE_INSENSITIVE), insertRequest.email!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_EMAIL)
            return
        }

        //mobile number check
        if(insertRequest.contact!!.length < ApplicationConstants.MOBILE_LENGTH &&
            !ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.PHONE_REGEX, Pattern.CASE_INSENSITIVE), insertRequest.contact!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_NUMBER)
            return
        }

        mListener.onSuccess(ApplicationConstants.INSERT_CLICKED)
    }

    fun insertData() {
        this.disposable.addAll(this.hoopyRepo.insertData(insertRequest)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({resources -> getInsertResponse().postValue(resources)}, {resources -> getInsertError().postValue(resources.message)}))
    }

    fun getInsertResponse() = insertResponse

    fun getInsertError() = insertError

    fun afterNameTextChanged(s: Editable?) {
       this.insertRequest.name = s.toString()
    }

    fun afterEmailTextChanged(s: Editable?) {
        this.insertRequest.email = s.toString()
    }

    fun afterNumberTextChanged(s: Editable?) {
        this.insertRequest.contact = s.toString()
    }

    fun afterUserNameTextChanged(s: Editable?) {
        this.insertRequest.username = s.toString()
    }

    fun setListener(listener: IActivityCallBacks) {
        this.mListener = listener
    }
}