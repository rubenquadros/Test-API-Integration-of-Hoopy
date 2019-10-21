package com.rubenquadros.apitest.data.model.insert

import com.google.gson.annotations.SerializedName

class InsertRequest {

    @SerializedName("contact")
    var contact: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("image_url")
    var imageUrl: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("username")
    var username: String? = null

}
