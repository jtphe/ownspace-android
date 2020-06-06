package com.example.ownspace.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
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
    context: Context,
    view: View,
    supportFragmentManager: FragmentManager,
    homeFrameLayout: Int,
    name: String
) {
    val contentUri: Uri?
    val newPath = "content:/$path"
    contentUri = Uri.parse(newPath)
    Log.d("contentUri", contentUri.toString())
    Log.d("path", path)

    if (contentUri == null) {
        showSnackbar(view, "Invalid file", true)
        return;
    }

    // Create URI

    val intent = Intent(Intent.ACTION_VIEW);
    // Check what kind of file you are trying to open, by comparing the url with extensions.
    // When the if condition is matched, plugin sets the correct intent (mime) type,
    // so Android knew what application to use to open the file
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
        Log.d("name", name)
        Log.d("content", File(path).readText())
        val fragment = TextViewerFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(homeFrameLayout, fragment).addToBackStack(null).commit()

//        intent.setDataAndType(contentUri, "text/plain");
    } else if (path.contains(".3gp") || path.contains(".mpg") || path.contains(".mpeg") || path.contains(
            ".mpe"
        ) || path.contains(".mp4") || path.contains(".avi")
    ) {
        // Video files
        intent.setDataAndType(contentUri, "video/*");
    } else {
        //if you want you can also define the intent type for any other file
        //additionally use else clause below, to manage other unknown extensions
        //in this case, Android will show all applications installed on the device
        //so you can choose which application to use
        intent.setDataAndType(contentUri, "*/*");
    }

//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    context.startActivity(intent);
}
