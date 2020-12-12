package com.srb.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import coil.api.load
import com.srb.api.interactor.ApiAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    val btn : Button = findViewById(R.id.btn_dog)
    val iv : ImageView = findViewById(R.id.iv_dog)

        btn.setOnClickListener{

            MainScope().launch(Dispatchers.Main){

                //Handling Exceptions
                try{
                    val response = ApiAdapter.apiClient.getRandomDogImage()

                    if (response.isSuccessful && response.body() != null){

                        val data = response.body()!!

                        //check for null
                        data.message?.let { imageUrl ->
                            //load url into imageview using coil
                            iv.load(imageUrl)
                        }
                    }
                    else{
                        // Show API error
                        Toast.makeText(this@MainActivity,"Error Occured ${response.message()}",Toast.LENGTH_LONG).show()
                    }
                }
                catch(e : Exception){
                    // This is the error made by the client

                    Toast.makeText(this@MainActivity,"Error Occured ${e.message}",Toast.LENGTH_LONG).show()


                }

            }

        }

    }
}