package com.arjungupta08.arjunassignment.utils
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.LruCache
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object ImageCache {

    private  val MAX_MEMORY = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private  val CACHE_SIZE = MAX_MEMORY / 8 // Use 1/8th of the available memory for this memory cache

    private val memoryCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(CACHE_SIZE) {
        override fun sizeOf(key: String?, bitmap: Bitmap): Int {
            // Return the size of the bitmap in kilobytes
            return bitmap.byteCount / 1024
        }
    }

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    fun getBitmapFromMemCache(key: String): Bitmap? {
        return memoryCache.get(key)
    }
}