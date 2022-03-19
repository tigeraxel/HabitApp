package com.example.sestudentjuhabitapp

import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.provider.Settings.Global.getString
import java.util.ArrayList
import android.content.res.Resources


class ValidationClass {
    val maxEmailLength = 34
    val minEmailLength = 8
    val minPasswordLength = 6
    val maxPasswordLength = 24
    val maxHabitTitleLength = 24
    val minHabitTitleLength = 2

   public fun showValidationErrors(errors: ArrayList<String> ,context: Context) {

        val dialogBuilder = android.app.AlertDialog.Builder(context)
        var errorText = ""
        for (error in errors)
            errorText += error
        dialogBuilder.setMessage(
            (errorText)
        )
            // if the dialog is cancelable
            .setCancelable(false)
            // negative button text and action
            .setNegativeButton(
                context.getString(R.string.ok),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(context.getString(R.string.error))
        // show alert dialog
        alert.show()
    }
}