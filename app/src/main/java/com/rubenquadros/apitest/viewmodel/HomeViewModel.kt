package com.rubenquadros.apitest.viewmodel

import android.view.View
import com.rubenquadros.apitest.base.BaseViewModel
import com.rubenquadros.apitest.callbacks.IActivityCallBacks
import com.rubenquadros.apitest.data.repository.HoopyRepo
import com.rubenquadros.apitest.utils.ApplicationConstants
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNUSED_PARAMETER", "unused")
class HomeViewModel @Inject
constructor(private val hoopyRepo: HoopyRepo,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel(){

    private lateinit var mListener: IActivityCallBacks

    fun insertClicked(view: View) {
        this.mListener.onSuccess(ApplicationConstants.INSERT_CLICKED)
    }

    fun fetchClicked(view: View) {
        this.mListener.onSuccess(ApplicationConstants.FETCH_CLICKED)
    }

    fun setListener(listener: IActivityCallBacks) {
        this.mListener = listener
    }
}