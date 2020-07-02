package com.example.ownspace.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ownspace.R
import com.example.ownspace.showSnackbar
import com.github.barteksc.pdfviewer.listener.OnErrorListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import kotlinx.android.synthetic.main.fragment_pdf.*
import java.io.File

/**
 * The PdfFragment class
 */
class PdfFragment : Fragment(), OnPageErrorListener, OnErrorListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pdf, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from bundle
        val pdfPath = arguments?.getString("path").toString().replace("content://", "")
        // Display the pdf
        pdfView.fromFile(File(pdfPath)).onPageError(this).onError(this).load()
    }

    override fun onPageError(page: Int, t: Throwable?) {
        view?.let {
            showSnackbar(
                it,
                getString(R.string.pageError),
                true
            )
        }
        Log.d("Page error => ", t.toString())
    }

    override fun onError(t: Throwable?) {
        view?.let {
            showSnackbar(
                it,
                getString(R.string.pdfError),
                true
            )
        }
        Log.d("PDF error => ", t.toString())
    }
}
