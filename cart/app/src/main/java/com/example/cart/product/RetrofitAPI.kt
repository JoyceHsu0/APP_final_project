package com.example.cart.product

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    //當進行 get request 時，用來passing last parameter for our url
    @GET("product.php")
    fun getAllProduct(): Call<ArrayList<ProductRVModal>?>?
}