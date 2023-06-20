package com.example.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.cart.CartRVAdapter
import com.example.cart.cart.CartRVModal
import com.example.cart.cart.RetrofitAPI
import com.squareup.picasso.Picasso
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class CartActivity : AppCompatActivity() {

    lateinit var cRV: RecyclerView
    lateinit var loadingPB: ProgressBar
    lateinit var cRVAdapter: CartRVAdapter
    lateinit var cList: ArrayList<CartRVModal>

    lateinit var put_in_checkout: Button

    lateinit var client : OkHttpClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        client = OkHttpClient()

        cRV = findViewById(R.id.idRVCart)
        loadingPB = findViewById(R.id.cart_PBLoading)

        cList = ArrayList()

        getAllCart()

        counttotal()

        /*-- 送出訂單功能 start --*/
        put_in_checkout = findViewById(R.id.put_in_checkout)
        put_in_checkout.setOnClickListener {
            putincheckout()
            val intent = Intent(this@CartActivity, MainActivity::class.java)
            startActivityForResult(intent,1)
            deletecart()
        }
        /*-- 送出訂單功能 end --*/




    }

    private fun getAllCart(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://s0854006.lionfree.net/app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call: Call<ArrayList<CartRVModal>?>? = retrofitAPI.getAllCart()

        call!!.enqueue(object : Callback<ArrayList<CartRVModal>?> {
            override fun onResponse(
                call: Call<ArrayList<CartRVModal>?>,
                response: Response<ArrayList<CartRVModal>?>
            ) {
                if (response.isSuccessful) {
                    loadingPB.visibility = View.GONE
                    cList = response.body()!!
                }

                // on below line we are initializing our adapter.
                cRVAdapter = CartRVAdapter(cList)


                // on below line we are setting adapter to recycler view.
                cRV.adapter = cRVAdapter

                cRVAdapter.setOnItemClickListener(object: CartRVAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        //Toast.makeText(this@MainActivity, "item$position", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@CartActivity, ProductDeatilActivity::class.java)
                        intent.putExtra("ID", position)
                        startActivityForResult(intent,1)
                    }


                })


            }

            override fun onFailure(call: Call<ArrayList<CartRVModal>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@CartActivity, "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    /*-- 送出訂單功能 start --*/
    private fun putincheckout(){
        val urlBuilder = "https://s0854006.lionfree.net/app/put_in_checkout.php".toHttpUrlOrNull()
            ?.newBuilder()

        val url = urlBuilder?.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    println("-----${responseBody}")

                } else {
                    println("Request failed")
                }
            }
        })
    }
    /*-- 送出訂單功能 end --*/

    private fun deletecart(){
        val urlBuilder = "https://s0854006.lionfree.net/app/cart_delete.php".toHttpUrlOrNull()
            ?.newBuilder()

        val url = urlBuilder?.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    println("-----${responseBody}")

                } else {
                    println("Request failed")
                }
            }
        })
    }

    private fun counttotal(){
        //https://s0854006.lionfree.net/app/cart_total.php
        val urlBuilder = "https://s0854006.lionfree.net/app/cart_total.php".toHttpUrlOrNull()
            ?.newBuilder()

        val url = urlBuilder?.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        //API裡面資料皆是JSON格式，要解析JSON格式
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    println("-----${responseBody}")


                    val jsonObject = JSONObject(responseBody)
                    val total = jsonObject.getInt("total")


                    runOnUiThread {
                        findViewById<TextView>(R.id.cart_total).text="共計: ${total} 元"


                    }
                } else {
                    println("Request failed")
                    runOnUiThread {
                        findViewById<TextView>(R.id.cart_total).text="共計: ?? 元"
                    }
                }
            }
        })
    }

}