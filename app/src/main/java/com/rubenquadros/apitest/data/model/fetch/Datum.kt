package com.rubenquadros.apitest.data.model.fetch

import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("contact")
    var contact: Long? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("id")
    var id: Long? = null
    @SerializedName("image_url")
    var imageUrl: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("username")
    var username: String? = null

}
