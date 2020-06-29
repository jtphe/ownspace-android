package com.example.ownspace.ui.fragments
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.ownspace.R
import kotlinx.android.synthetic.main.fragment_image.*

/**
 * The ImageFragment class
 */
class ImageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from bundle
        val imageName = arguments?.getString("title")
        val imagePath = arguments?.getString("path")

        // Set the file name and the image
        fileNameTitle.text = imageName
        val mImageView = view.findViewById(R.id.imageView) as ImageView
        mImageView.setImageDrawable(Drawable.createFromPath(imagePath))

    }
}
