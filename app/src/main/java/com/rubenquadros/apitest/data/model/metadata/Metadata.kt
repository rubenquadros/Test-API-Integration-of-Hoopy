package com.rubenquadros.apitest.data.model.metadata

import com.google.gson.annotations.SerializedName

class Metadata {

    @SerializedName("response_code")
    var responseCode: Long? = null
    @SerializedName("response_text")
    var responseText: String? = null

}
