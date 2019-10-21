package com.rubenquadros.apitest.viewmodel

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.apitest.base.BaseViewModel
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.model.fetch.FetchRequest
import com.rubenquadros.apitest.data.model.fetch.FetchResponse
import com.rubenquadros.apitest.data.repository.HoopyRepo
import com.rubenquadros.apitest.utils.ApplicationConstants
import com.rubenquadros.apitest.utils.ApplicationUtility
import io.reactivex.Scheduler
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

@Suppress("JoinDeclarationAndAssignment", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")
class FetchViewModel @Inject
constructor(private val hoopyRepo: HoopyRepo,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel() {

    private lateinit var mListener: IActivityCallBacks
    var fetchRequest: FetchRequest
    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val fetchResponse: MutableLiveData<FetchResponse> = MutableLiveData()
    private val fetchError: MutableLiveData<String?> = MutableLiveData()

    init {
        fetchRequest = FetchRequest()
    }

    fun fetchButtonClicked(view: View) {
        //empty fields check
        if(TextUtils.isEmpty(this.fetchRequest.name) && TextUtils.isEmpty(this.fetchRequest.email) &&
                TextUtils.isEmpty(this.fetchRequest.contact) && TextUtils.isEmpty(this.fetchRequest.username)) {
            this.mListener.onFailed(ApplicationConstants.EMPTY_FIELDS)
            return
        }

        //many fields check
        if(!((!TextUtils.isEmpty(fetchRequest.name) &&(TextUtils.isEmpty(fetchRequest.email) && TextUtils.isEmpty(fetchRequest.contact) && TextUtils.isEmpty(fetchRequest.username))) ||
            (!TextUtils.isEmpty(fetchRequest.email) && (TextUtils.isEmpty(fetchRequest.name) && TextUtils.isEmpty(fetchRequest.contact) && TextUtils.isEmpty(fetchRequest.username))) ||
            (!TextUtils.isEmpty(fetchRequest.contact) && (TextUtils.isEmpty(fetchRequest.name) && TextUtils.isEmpty(fetchRequest.email) && TextUtils.isEmpty(fetchRequest.username))) ||
            (!TextUtils.isEmpty(fetchRequest.username) && (TextUtils.isEmpty(fetchRequest.name) && TextUtils.isEmpty(fetchRequest.email) && TextUtils.isEmpty(fetchRequest.contact))))) {
            this.mListener.onFailed(ApplicationConstants.MANY_FIELDS)
            return
        }

        //name check
        if(!TextUtils.isEmpty(fetchRequest.name)) {
            if (!ApplicationUtility.regexValidator(
                    Pattern.compile(
                        ApplicationConstants.NAME_REGEX,
                        Pattern.CASE_INSENSITIVE
                    ), fetchRequest.name!!
                )
            ) {
                mListener.onFailed(ApplicationConstants.INVALID_NAME)
                return
            }
        }

        //email check
        if(!TextUtils.isEmpty(fetchRequest.email)) {
            if (!ApplicationUtility.regexValidator(
                    Pattern.compile(
                        ApplicationConstants.EMAIL_REGEX,
                        Pattern.CASE_INSENSITIVE
                    ), fetchRequest.email!!
                )
            ) {
                mListener.onFailed(ApplicationConstants.INVALID_EMAIL)
                return
            }
        }

        //mobile number check
        if(!TextUtils.isEmpty(fetchRequest.contact)) {
            if (fetchRequest.contact!!.length < ApplicationConstants.MOBILE_LENGTH &&
                !ApplicationUtility.regexValidator(
                    Pattern.compile(
                        ApplicationConstants.PHONE_REGEX,
                        Pattern.CASE_INSENSITIVE
                    ), fetchRequest.contact!!
                )
            ) {
                mListener.onFailed(ApplicationConstants.INVALID_NUMBER)
                return
            }
        }

        fetchData()
    }

    fun fetchData() {
        this.disposable.addAll(this.hoopyRepo.fetchData(fetchRequest)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({resources -> getFetchResponse().postValue(resources)}, {resources -> if(resources.message != ApplicationConstants.ERR_IGNORE) getFetchError().postValue(ApplicationConstants.SEARCH_ERR)}))
    }

    fun getFetchResponse() = fetchResponse

    fun getFetchError() = fetchError

    fun afterNameTextChanged(s: Editable?) {
        this.fetchRequest.name = s.toString()
    }

    fun afterEmailTextChanged(s: Editable?) {
        this.fetchRequest.email = s.toString()
    }

    fun afterNumberTextChanged(s: Editable?) {
        this.fetchRequest.contact = s.toString()
    }

    fun afterUserNameTextChanged(s: Editable?) {
        this.fetchRequest.username = s.toString()
    }

    fun setListener(listener: IActivityCallBacks) {
        this.mListener = listener
    }
}