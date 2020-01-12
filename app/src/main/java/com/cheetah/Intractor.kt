package com.cheetah

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import retrofit2.Response
import java.util.concurrent.TimeUnit

class Intractor(private val mOnGetDatalistener: GetDataContract.onGetDataListener) :
    GetDataContract.Interactor {
    internal var order: List<Order> = ArrayList<Order>()

    override fun initRetrofitCall(context: Context, url: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)

        httpClient.addInterceptor {
            val original: Request = it.request()
            val originalHttpUrl: HttpUrl = original.url()

            val url: HttpUrl = originalHttpUrl.newBuilder()
                .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)

            val request: Request = requestBuilder.build()
            it.proceed(request)
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val request = retrofit.create<dataResponce>(dataResponce::class.java!!)
        val call = request.getOrder
        call.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val jsonResponse = response.body()

                order = jsonResponse!!.order_items_information!!
                var total = jsonResponse!!.total!!;
                Log.d("Data", "Refreshed")
                mOnGetDatalistener.onSuccess( total, order)
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.v("Error", t.message)
                t.message?.let { mOnGetDatalistener.onFailure(it) }
            }
        })
    }
}


