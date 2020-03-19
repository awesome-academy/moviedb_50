package com.sun_asterisk.moviedb_50.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sun_asterisk.moviedb_50.R

fun FragmentActivity.showSnackBar(msgId: Int) {
    val view = this.findViewById(android.R.id.content) as View
    val snackBar = Snackbar.make(
        view
        , this.getString(msgId),
        Snackbar.LENGTH_LONG
    )
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSliver))
    val textView =
        snackBarView.findViewById(R.id.snackbar_text) as TextView
    textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
    textView.textSize = 16f
    snackBar.show()
}
