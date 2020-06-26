package com.example.ownspace.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.ownspace.R
import com.example.ownspace.models.Path
import com.example.ownspace.ui.activities.MainActivity
import com.example.ownspace.ui.fragments.TextViewerFragment
import com.google.android.material.snackbar.Snackbar
import com.vicpin.krealmextensions.queryFirst
import java.io.File


/**
 * Show a snackbar
 * @param context View - The view that shows the snackbar
 * @param text String - The text for the snackbar
 * @param long Boolean - The duration of the snackbar
 */
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

fun getCurrentPathString(): String {
    var path = ""
    val data = Path().queryFirst()!!.path
    for (i in data) {
        val element = i.name
        path += "$element/"
    }

    return path
}

fun openDocument(
    path: String,
    view: View,
    supportFragmentManager: FragmentManager,
    homeFrameLayout: Int,
    name: String
) {
    val newPath = "content:/$path"
    val contentUri = Uri.parse(newPath)

    if (contentUri == null) {
        showSnackbar(view, "Invalid file", true)
        return;
    }

    val intent = Intent(Intent.ACTION_VIEW);

    // Check the type of file
    if (path.contains(".doc") || path.contains(".docx")) {
        // Word document
        intent.setDataAndType(contentUri, "application/msword");
    } else if (path.contains(".pdf")) {
        // PDF file
        intent.setDataAndType(contentUri, "application/pdf");
    } else if (path.contains(".ppt") || path.contains(".pptx")) {
        // Powerpoint file
        intent.setDataAndType(contentUri, "application/vnd.ms-powerpoint");
    } else if (path.contains(".xls") || path.contains(".xlsx")) {
        // Excel file
        intent.setDataAndType(contentUri, "application/vnd.ms-excel");
    } else if (path.contains(".zip") || path.contains(".rar")) {
        // ZIP file
        intent.setDataAndType(contentUri, "application/zip");
    } else if (path.contains(".rtf")) {
        // RTF file
        intent.setDataAndType(contentUri, "application/rtf");
    } else if (path.contains(".wav") || path.contains(".mp3")) {
        // WAV audio file
        intent.setDataAndType(contentUri, "audio/x-wav");
    } else if (path.contains(".gif")) {
        // GIF file
        intent.setDataAndType(contentUri, "image/gif");
    } else if (path.contains(".jpg") || path.contains(".jpeg") || path.contains(".png")) {
        // JPG file
        intent.setDataAndType(contentUri, "image/jpeg");
    } else if (path.contains(".txt")) {
        // Text file
        val bundle = Bundle()
        bundle.putString("title", name)
        bundle.putString("content", File(path).readText())
        val fragment = TextViewerFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(homeFrameLayout, fragment)
            .addToBackStack(null).commit()
        return
    } else if (path.contains(".3gp") || path.contains(".mpg") || path.contains(".mpeg") || path.contains(
            ".mpe"
        ) || path.contains(".mp4") || path.contains(".avi")
    ) {
        // Video files
        intent.setDataAndType(contentUri, "video/*")
    } else {
        intent.setDataAndType(contentUri, "*/*")
    }

    if (!path.contains(".txt")) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        MainActivity.applicationContext().startActivity(intent)
    }
}
