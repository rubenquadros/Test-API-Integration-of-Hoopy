package com.rubenquadros.apitest.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class ApplicationUtility {

    companion object {

        @Throws(IOException::class)
        fun createImageFile(context: Context): File {
            val timeStamp = SimpleDateFormat(ApplicationConstants.DATE_FORMAT, Locale.getDefault()).format(
                Date()
            )
            val imageFileName = ApplicationConstants.IMAGE_PREFIX + timeStamp + ApplicationConstants.IMAGE_SUFFIX
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(imageFileName, ApplicationConstants.JPG_SUFFIX, storageDir)
        }

        fun checkDir(path: String) : Boolean {
            var result = true
            val file = File(path)
            if(!file.exists()) {
                result = file.mkdirs()
            }else if(file.isFile) {
                file.delete()
                result = file.mkdirs()
            }
            return result
        }

        fun showSnack(msg: String, view: View, action: String){
            val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(action) {
                snackBar.dismiss()
            }
            snackBar.show()
        }

        fun showDialog(context: Context, title: String, message: String, button: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(button){ _, _ ->
                // do nothing
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        fun regexValidator(pattern: Pattern, value: String): Boolean{
            if(!pattern.matcher(value).matches()){
                return false
            }
            return true
        }

        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            val focusedView = activity.currentFocus
            if (focusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
            }
        }

        fun EditText.editEnabled() {
            isFocusable = true
            isFocusableInTouchMode = true
            isEnabled = true
            isClickable = true
        }
    }
}