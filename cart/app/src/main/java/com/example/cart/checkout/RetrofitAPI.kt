package com.example.cart.checkout

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("checkout.php?account=appuser")
    fun getAllCheckout(): Call<ArrayList<CheckoutRVModal>?>?
}