package com.rubenquadros.apitest.viewmodel

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.apitest.base.BaseViewModel
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.model.update.UpdateRequest
import com.rubenquadros.apitest.data.model.update.UpdateResponse
import com.rubenquadros.apitest.data.repository.HoopyRepo
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import io.reactivex.Scheduler
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

@Suppress("JoinDeclarationAndAssignment", "UNUSED_PARAMETER")
class EditViewModel @Inject
constructor(private val hoopyRepo: HoopyRepo,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel() {

    private var updateRequest: UpdateRequest
    private lateinit var mListener: IActivityCallBacks
    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val updateResponse: MutableLiveData<UpdateResponse> = MutableLiveData()
    private val updateError: MutableLiveData<String?> = MutableLiveData()

    init {
        updateRequest = UpdateRequest()
    }

    fun saveClicked(view: View) {
        //empty fields check
        if(TextUtils.isEmpty(updateRequest.name) || TextUtils.isEmpty(updateRequest.email) || TextUtils.isEmpty(updateRequest.contact) ||
            TextUtils.isEmpty(updateRequest.username)) {
            mListener.onFailed(ApplicationConstants.EMPTY_FIELDS)
            return
        }

        //name check
        if(!ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.NAME_REGEX, Pattern.CASE_INSENSITIVE), updateRequest.name!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_NAME)
            return
        }

        //email check
        if(!ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.EMAIL_REGEX, Pattern.CASE_INSENSITIVE), updateRequest.email!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_EMAIL)
            return
        }

        //mobile number check
        if(updateRequest.contact!!.length < ApplicationConstants.MOBILE_LENGTH &&
            !ApplicationUtility.regexValidator(Pattern.compile(ApplicationConstants.PHONE_REGEX, Pattern.CASE_INSENSITIVE), updateRequest.contact!!)) {
            mListener.onFailed(ApplicationConstants.INVALID_NUMBER)
            return
        }

        mListener.onSuccess(ApplicationConstants.UPDATE)
    }

    fun updateData(updateRequest: UpdateRequest) {
        this.disposable.addAll(this.hoopyRepo.updateData(updateRequest)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({resources -> getUpdateResponse().postValue(resources)}, {resources -> getUpdateError().postValue(resources.message)}))
    }

    fun getUpdateResponse() = updateResponse

    fun getUpdateError() = updateError

    fun afterNameTextChanged(s: Editable?) {
        this.updateRequest.name = s.toString()
    }

    fun afterEmailTextChanged(s: Editable?) {
        this.updateRequest.email = s.toString()
    }

    fun afterNumberTextChanged(s: Editable?) {
        this.updateRequest.contact = s.toString()
    }

    fun afterUserNameTextChanged(s: Editable?) {
        this.updateRequest.username = s.toString()
    }

    fun setListener(listener: IActivityCallBacks) {
        this.mListener = listener
    }
}