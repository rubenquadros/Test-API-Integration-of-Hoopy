package com.rubenquadros.apitest.data.model.insert

import com.google.gson.annotations.SerializedName
import com.rubenquadros.apitest.data.model.metadata.Metadata

class InsertResponse {

    @SerializedName("data")
    var data: Data? = null
    @SerializedName("metadata")
    var metadata: Metadata? = null

}
