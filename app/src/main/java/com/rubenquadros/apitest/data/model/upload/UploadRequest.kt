package com.rubenquadros.apitest.data.model.upload

import com.google.gson.annotations.SerializedName

class UploadRequest {
    @SerializedName("path")
    var path: String? = null
}