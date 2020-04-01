package com.example.ownspace.ui

import android.graphics.Color
import android.view.View
import com.example.ownspace.R
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(context: View, text: String, long: Boolean) {
    if (long) {
        val mSnackbar = Snackbar.make(context, text, Snackbar.LENGTH_LONG)
        mSnackbar.apply {
            setAction(R.string.ok, View.OnClickListener {
                mSnackbar.dismiss()
            })
            setBackgroundTint(Color.parseColor("#CC000000"))
            show()
        }

    } else {
        Snackbar.make(context, text, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(Color.parseColor("#CC000000"))
            show()
        }
    }
}