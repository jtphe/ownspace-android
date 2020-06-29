package com.example.ownspace.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.ownspace.R
import com.example.ownspace.models.Path
import com.example.ownspace.ui.fragments.ImageFragment
import com.example.ownspace.ui.fragments.PdfFragment
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

/**
 * Get the current path
 * @return String - The current path
 */
fun getCurrentPathString(): String {
    var path = ""
    val data = Path().queryFirst()!!.path
    for (i in data) {
        val element = i.name
        path += "$element/"
    }

    return path
}

/**
 * Open the document
 * @param path String - The document's path
 * @param view View - The current view
 * @param supportFragmentManager FragmentManager - The fragment manager
 * @param homeFrameLayout Int - The id of the homeFrameLayout
 * @param name String - The document's name
 */
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
        showSnackbar(view, "Fichier invalide", true)
        return;
    }

    // Check the type of file
    if (path.contains(".doc") || path.contains(".docx")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".pdf")) {
        // PDF file

        // Pass the data needed in a bundle
        val bundle = Bundle()
        bundle.putString("path", newPath)
        // Retrieve the pdf fragment
        val fragment = PdfFragment()
        fragment.arguments = bundle
        // Replace the home fragment with the image fragment
        supportFragmentManager.commit {
            replace(homeFrameLayout, fragment)
            addToBackStack(null)
        }
        return
    } else if (path.contains(".ppt") || path.contains(".pptx")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".xls") || path.contains(".xlsx")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".zip") || path.contains(".rar")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".rtf")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".wav") || path.contains(".mp3")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".gif")) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else if (path.contains(".jpg") || path.contains(".jpeg") || path.contains(".png")) {
        // Image file
        // Pass the data needed in a bundle
        val bundle = Bundle()
        bundle.putString("title", name)
        bundle.putString("path", path)
        // Retrieve the image fragment
        val fragment = ImageFragment()
        fragment.arguments = bundle
        // Replace the home fragment with the image fragment
        supportFragmentManager.commit {
            replace(homeFrameLayout, fragment)
            addToBackStack(null)
        }
        return
    } else if (path.contains(".txt")) {
        // Text file
        // Pass data needed in a bundle
        val bundle = Bundle()
        bundle.putString("title", name)
        bundle.putString("content", File(path).readText())
        // Retrieve text fragment
        val fragment = TextViewerFragment()
        fragment.arguments = bundle
        // Replace the home fragment with the text fragment
        supportFragmentManager.commit {
            replace(homeFrameLayout, fragment)
            addToBackStack(null)
        }
        return
    } else if (path.contains(".3gp") || path.contains(".mpg") || path.contains(".mpeg") || path.contains(
            ".mpe"
        ) || path.contains(".mp4") || path.contains(".avi")
    ) {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    } else {
        showSnackbar(view, "Impossible d'ouvrir le fichier. Format non supporté", true)
    }
}
