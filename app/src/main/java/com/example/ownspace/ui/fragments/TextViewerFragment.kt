package com.example.ownspace.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ownspace.R
import kotlinx.android.synthetic.main.fragment_text_viewer.*

/**
 * The TextViewerFragment class
 */
class TextViewerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_text_viewer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from bundle
        fileNameTitle.text = arguments?.getString("title").toString()
        fileNameContent.text = arguments?.getString("content").toString()
    }
}
