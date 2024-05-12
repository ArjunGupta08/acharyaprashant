package com.arjungupta08.arjunassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arjungupta08.arjunassignment.utils.ImageCache
import com.arjungupta08.arjunassignment.utils.ImageLoader
import com.google.android.material.imageview.ShapeableImageView

class ImageAdapter(private val context : Context, private val list : ArrayList<String>) : RecyclerView.Adapter<ImageAdapter.ImagesViewHolder>() {

    // list to track loaded images
    private val loadedFlags = BooleanArray(list.size)

    private val imageLoaders = mutableMapOf<ShapeableImageView, ImageLoader>()

    class ImagesViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        val imageView : ShapeableImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val data = list[position]
        val imageView = holder.imageView

        // Cancel previous image loading task if any
        imageLoaders[imageView]?.cancel(true)

        // Load image using ImageLoader
        val imageLoader = ImageLoader(context, imageView, data)
        imageLoader.execute()
        imageLoaders[imageView] = imageLoader
    }

    override fun onViewRecycled(holder: ImagesViewHolder) {
        // Cancel image loading task when view holder is recycled
        imageLoaders[holder.imageView]?.cancel(true)
        super.onViewRecycled(holder)
    }
}