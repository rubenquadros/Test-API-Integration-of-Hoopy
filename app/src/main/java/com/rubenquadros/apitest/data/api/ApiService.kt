package com.rubenquadros.apitest.data.api

import com.rubenquadros.apitest.data.model.fetch.FetchRequest
import com.rubenquadros.apitest.data.model.fetch.FetchResponse
import com.rubenquadros.apitest.data.model.insert.InsertRequest
import com.rubenquadros.apitest.data.model.insert.InsertResponse
import com.rubenquadros.apitest.data.model.update.UpdateRequest
import com.rubenquadros.apitest.data.model.update.UpdateResponse
import com.rubenquadros.apitest.data.model.upload.UploadRequest
import com.rubenquadros.apitest.data.model.upload.UploadResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/1.0/testApis/upload_test")
    fun uploadImage(@Body uploadRequest: UploadRequest): Observable<UploadResponse>

    @POST("api/1.0/testApis/insert_test")
    fun insertData(@Body insertRequest: InsertRequest): Observable<InsertResponse>

    @POST("api/1.0/testApis/fetch_data_test")
    fun fetchData(@Body fetchRequest: FetchRequest): Observable<FetchResponse>

    @POST("api/1.0/testApis/update_data_test")
    fun updateData(@Body updateRequest: UpdateRequest): Observable<UpdateResponse>
}