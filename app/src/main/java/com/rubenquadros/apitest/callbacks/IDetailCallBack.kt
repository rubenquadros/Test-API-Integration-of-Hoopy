package com.rubenquadros.apitest.callbacks

import com.rubenquadros.apitest.data.model.fetch.Datum

interface IDetailCallBack {

    fun getDetail(datum: Datum?)
}