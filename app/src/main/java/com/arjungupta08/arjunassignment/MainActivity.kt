package com.arjungupta08.arjunassignment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjungupta08.arjunassignment.databinding.ActivityMainBinding
import com.arjungupta08.arjunassignment.model.AllImagesItem
import com.arjungupta08.arjunassignment.retrofit.RetrofitBuilder
import com.arjungupta08.arjunassignment.utils.ImageCache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val imageUrlList = ArrayList<String>()
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)

        getImages()

        // Load images
        getImages()
    }

    private fun getImages() {
        val get = RetrofitBuilder.imagesApi.getImages(100)
        get.enqueue(object : Callback<List<AllImagesItem>?> {
            override fun onResponse(
                call: Call<List<AllImagesItem>?>,
                response: Response<List<AllImagesItem>?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        val imageUrl = "${it.thumbnail.domain}/${it.thumbnail.basePath}/0/${it.thumbnail.key}"
                        Log.d("imageUrl", imageUrl)
                        imageUrlList.add(imageUrl)
                    }
                        adapter = ImageAdapter(this@MainActivity, imageUrlList)
                        binding.recyclerView.adapter = adapter
                } else {
                    Log.e("ApiFailed", response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<AllImagesItem>?>, t: Throwable) {
                Log.e("error", t.localizedMessage!!.toString())
            }
        })
    }

}