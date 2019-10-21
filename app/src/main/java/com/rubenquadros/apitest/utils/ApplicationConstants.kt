package com.rubenquadros.apitest.utils

class ApplicationConstants {

    companion object {

        const val BASE_URL = "https://www.team.hoopy.in/"
        const val TIMEOUT_REQUEST: Long = 30
        const val SUBSCRIBER_ON = "SubscribeOn"
        const val OBSERVER_ON = "ObserveOn"
        const val UPLOAD_IMG = "Upload Image"
        const val DATE_FORMAT = "yyyyMMdd_HHmmss"
        const val IMAGE_PREFIX = "IMG_"
        const val IMAGE_SUFFIX = "_"
        const val JPG_SUFFIX = ".jpg"
        const val PROVIDER = ".provider"
        const val MAIN_DIR = "/RubenQ"
        const val STATUS_OK = 200L
        const val MOBILE_LENGTH = 10
        const val EMPTY_FIELDS = "Empty Fields"
        const val MANY_FIELDS = "Many Fields"
        const val EMAIL_REGEX = "[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
        const val PHONE_REGEX = "^[0-9]*\$"
        const val NAME_REGEX = "^[a-zA-Z ]*\$"
        const val INVALID_NAME = "Invalid Name"
        const val INVALID_EMAIL = "Invalid Email"
        const val INVALID_NUMBER = "Invalid Number"
        const val INSERT_SUCCESS = "Register"
        const val INSERT_CLICKED = "Insert Clicked"
        const val FETCH_CLICKED = "Fetch Clicked"
        const val EDIT = "Edit"
        const val NAME = "Name: "
        const val EMAIL = "E-Mail: "
        const val CONTACT = "Contact: "
        const val USERNAME = "Username: "
        const val ID = "Id"
        const val ERR_IGNORE = "2 exceptions occurred"
        const val SEARCH_ERR = "Please search with different parameters"
        const val UPDATE = "Update Data"
        const val UPDATE_SUCCESS = "Update Success"
    }
}