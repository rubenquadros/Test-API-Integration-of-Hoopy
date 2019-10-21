package com.rubenquadros.apitest.data.model.fetch

import com.google.gson.annotations.SerializedName

class FetchRequest {
    @SerializedName("email")
    var email:String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("contact")
    var contact: String? = null

    @SerializedName("username")
    var username: String? = null
}