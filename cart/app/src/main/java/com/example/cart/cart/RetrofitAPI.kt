package com.example.cart.cart

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("read_cart.php")
    fun getAllCart(): Call<ArrayList<CartRVModal>?>?
}