package com.rubenquadros.apitest.data.model.fetch

import com.google.gson.annotations.SerializedName
import com.rubenquadros.apitest.data.model.metadata.Metadata

class FetchResponse {

    @SerializedName("data")
    var data: List<Datum>? = null
    @SerializedName("metadata")
    var metadata: Metadata? = null

}
