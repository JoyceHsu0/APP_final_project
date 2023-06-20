package com.example.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ProductDeatilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_deatil)

/*-- 控制選擇商品數量按鈕 start --*/
        val quantity = findViewById<TextView>(R.id.quantity)
        val add = findViewById<Button>(R.id.add)
        val sub = findViewById<Button>(R.id.sub)
        var cnt = quantity.text.toString().toInt()

        add.setOnClickListener {
            cnt = quantity.text.toString().toInt()+1
            quantity.text=(cnt).toString()
        }
        sub.setOnClickListener {
            if(cnt > 0) {
                cnt = quantity.text.toString().toInt()-1
                quantity.text=(cnt).toString()
            }
            else quantity.text="0"
        }
/*-- 控制選擇商品數量按鈕 end  --*/

        val client = OkHttpClient()

        var id : String? = null //從shop fragment船進來的商品ID，用來透過GET讀取product-details.php
        var pimg = ""

/*-- 接收 Shop fragment傳送之ID start--*/
        intent?.extras?.let {
            id = (it.getInt("ID")+1).toString()
        }
/*-- 接收 Shop fragment傳送之ID end--*/

/*-- 從 product-details.php GET 所需商品資料 start--*/
        if(id != null){

            val urlBuilder = "https://s0854006.lionfree.net/app/product-details.php".toHttpUrlOrNull()
                ?.newBuilder()
                ?.addQueryParameter("id","$id") //設定參數(資料庫欄位)


            val url = urlBuilder?.build().toString()
            val request = Request.Builder()
                .url(url)
                .build()

            //API裡面資料皆是JSON格式，要解析JSON格式
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        println("-----${responseBody}")

                        val jsonObject = JSONObject(responseBody)
                        val pname = jsonObject.getString("p_name")
                        val pprice = jsonObject.getString("price")
                        val pcontent = jsonObject.getString("content")
                        val pcontent_new = pcontent.replace("</br>", System.getProperty("line.separator"))
                        pimg = jsonObject.getString("image")

                        runOnUiThread {
                            findViewById<TextView>(R.id.name_detail).text=pname
                            findViewById<TextView>(R.id.price_detail).text="${pprice} 元"

                            findViewById<TextView>(R.id.content_detail).text="商品特色：\n\n${pcontent_new}"
                            Picasso.get()
                                .load(pimg)
                                .into(findViewById<ImageView>(R.id.img_detail))

                        }
                    } else {
                        println("Request failed")
                        runOnUiThread {
                            findViewById<TextView>(R.id.name_detail).text = "資料錯誤"
                        }

                    }
                }
            })
        }
/*-- 從 product-details.php GET 所需商品資料 end --*/

/*-- 加入購物車功能: 透過POST寫入cart.php start --*/
        val add_into_cart = findViewById<Button>(R.id.add_to_cart)
        add_into_cart.setOnClickListener{
            if(cnt==0) { //數量不可為0，若為0跳出提示
                Toast.makeText(this@ProductDeatilActivity, "數量不可為0", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                Toast.makeText(this@ProductDeatilActivity, "加入購物車成功", Toast.LENGTH_SHORT)
                    .show()
                val postID = id
                println("postID-------${postID}")
                val postNum= cnt
                println("postNum-------${postNum}")

                val postImg = pimg

                //----------------
                val clientPost = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("p_id", "${postID}") //設定參數，送值
                    .add("p_num", "${postNum}")
                    .add("p_img", "${postImg}")
                    .build()
                val requestPost = Request.Builder()
                    .url("https://s0854006.lionfree.net/app/cart.php") //POST API的位置
                    .post(requestBody)
                    .build()

                clientPost.newCall(requestPost).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        // Handle the response body
                        println("POST-------${responseBody}")
                    }
                })
            }

        }
/*-- 加入購物車功能: 透過POST寫入cart.php end --*/

/*-- 立即購買功能: 透過POST寫入cart.php，並跳轉到cart Activity start  --*/
        val buy_now = findViewById<Button>(R.id.buy_now)
        buy_now.setOnClickListener{
            if(cnt==0) {
                Toast.makeText(this@ProductDeatilActivity, "數量不可為0", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                Toast.makeText(this@ProductDeatilActivity, "加入購物車成功", Toast.LENGTH_SHORT)
                    .show()

                val postID = id
                println("postID-------${postID}")
                val postNum= cnt
                println("postNum-------${postNum}")
                val postImg = pimg
                println("postImg-------${postImg}")
                //----------------
                val clientPost = OkHttpClient()
                val requestBody = FormBody.Builder()
                    .add("p_id", "${postID}") //設定參數，送值
                    .add("p_num", "${postNum}")
                    .add("p_img", "${postImg}")
                    .build()
                val requestPost = Request.Builder()
                    .url("https://s0854006.lionfree.net/app/cart.php") //POST API的位置
                    .post(requestBody)
                    .build()

                clientPost.newCall(requestPost).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        // Handle the response body
                        println("POST-------${responseBody}")
                    }
                })



                val intent = Intent(this@ProductDeatilActivity, CartActivity::class.java)
                startActivityForResult(intent,1)
            }


        }
/*-- 立即購買功能: 透過POST寫入cart.php，並跳轉到cart Activity end  --*/

    }
}