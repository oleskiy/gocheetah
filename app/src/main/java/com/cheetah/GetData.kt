package com.cheetah

import retrofit2.Call
import retrofit2.http.GET

interface dataResponce {
    @get:GET("59c791ed1100005300c39b93")
    val getOrder: Call<Data>

}