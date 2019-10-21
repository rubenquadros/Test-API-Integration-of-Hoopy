package com.rubenquadros.apitest.data.model.upload

import com.google.gson.annotations.SerializedName
import com.rubenquadros.apitest.data.model.metadata.Metadata

class UploadResponse {

    @SerializedName("metadata")
    var metadata: Metadata? = null
    @SerializedName("urls")
    var urls: List<String>? = null

}
