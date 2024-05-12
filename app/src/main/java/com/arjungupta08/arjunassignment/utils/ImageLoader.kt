package com.arjungupta08.arjunassignment.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL

class ImageLoader(private val context: Context, private val imageView: ImageView, private val url: String) : AsyncTask<Void, Void, Bitmap>() {

    override fun doInBackground(vararg params: Void?): Bitmap? {
        return try {
            val cachedBitmap = ImageCache.getBitmapFromMemCache(url)
            if (cachedBitmap != null) {
                return cachedBitmap // Return from memory cache if available
            } else {
                val diskCachedBitmap = readBitmapFromDiskCache(url)
                if (diskCachedBitmap != null) {
                    ImageCache.addBitmapToMemoryCache(url, diskCachedBitmap) // Cache in memory
                    return diskCachedBitmap
                } else {
                    val inputStream = URL(url).openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    saveBitmapToDiskCache(url, bitmap) // Cache in disk
                    ImageCache.addBitmapToMemoryCache(url, bitmap) // Cache in memory
                    bitmap
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        result?.let {
            imageView.setImageBitmap(it)
        }
    }

    private fun readBitmapFromDiskCache(url: String): Bitmap? {
        val diskCacheDir = getDiskCacheDir(context, "images")
        val file = File(diskCacheDir, url.hashCode().toString())
        return if (file.exists()) {
            FileInputStream(file).use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } else {
            null
        }
    }

    private fun saveBitmapToDiskCache(url: String, bitmap: Bitmap) {
        val diskCacheDir = getDiskCacheDir(context, "images")
        val file = File(diskCacheDir, url.hashCode().toString())
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }
    }

    private fun getDiskCacheDir(context: Context, uniqueName: String): File {
        return File(context.cacheDir, uniqueName).apply { mkdirs() }
    }
}
