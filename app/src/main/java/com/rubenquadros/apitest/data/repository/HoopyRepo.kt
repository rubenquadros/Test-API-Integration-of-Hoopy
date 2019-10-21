package com.rubenquadros.apitest.data.repository

import com.rubenquadros.apitest.data.api.ApiService
import com.rubenquadros.apitest.data.model.fetch.FetchRequest
import com.rubenquadros.apitest.data.model.insert.InsertRequest
import com.rubenquadros.apitest.data.model.update.UpdateRequest
import com.rubenquadros.apitest.data.model.upload.UploadRequest

class HoopyRepo(private val apiService: ApiService) {

    fun uploadImage(uploadRequest: UploadRequest) = this.apiService.uploadImage(uploadRequest)

    fun insertData(insertRequest: InsertRequest) = this.apiService.insertData(insertRequest)

    fun fetchData(fetchRequest: FetchRequest) = this.apiService.fetchData(fetchRequest)

    fun updateData(updateRequest: UpdateRequest) = this.apiService.updateData(updateRequest)
}