package com.example.cart.cart

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.CartActivity
import com.example.cart.R
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

/*-- 將變數放進list中 --*/
class CartRVAdapter(
    private var cartList: ArrayList<CartRVModal>,
) : RecyclerView.Adapter<CartRVAdapter.CartViewHolder>() {

    private lateinit var mListener: onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartRVAdapter.CartViewHolder{
        // inflate the layout file which we have created for our recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_cart,
            parent, false
        )

        return  CartViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: CartRVAdapter.CartViewHolder, position: Int) {
        // on below line we are setting data to our text view and our image view.

        var p_name = cartList.get(position).p_name
        var p_price = cartList.get(position).p_price
        var p_id = cartList.get(position).p_id
        var p_img = cartList.get(position).p_img
        var p_num = cartList.get(position).p_num


        holder.c_name.text = p_name
        holder.c_price.text = "共 ${p_price.toString().toInt() * p_num.toString().toInt()} 元"
        holder.c_id = p_id
        Picasso.get()
            .load("${p_img}")
            .into(holder.c_img)
        holder.c_num.text = p_num




    /*-- 調整購物車中某商品數量 start --*/
        var cnt = p_num.toString().toInt() //購物車中此商品的數量
        var postNum = 0
        val postID = p_id
        val postImg = p_img

        val clientPost = OkHttpClient()

        /*-- 按下 (-) 按鈕 start --*/
        holder.btn_sub.setOnClickListener {
            cnt = holder.c_num.text.toString().toInt()-1 //數量減1

            /*-- 若這時已達到0 start --*/
            if(cnt<=0){
                /*-- post cart_delete.php 刪除cart table中這個商品的欄位 start --*/
                val requestBody = FormBody.Builder()
                    .add("p_id", "${postID}")
                    .build()
                val requestPost = Request.Builder()
                    .url("https://s0854006.lionfree.net/app/cart_delete_item.php") //POST API的位置
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
                /*-- post cart_delete.php 刪除cart table中這個商品的欄位 end --*/
            }
            /*-- 若這時已達到0 end --*/
            /*-- 尚未達到0 => 將資料POST進cart_sub_add.php來更新數量 start --*/
            else{
                holder.c_num.text = (cnt).toString()
                holder.c_price.text = "共 ${p_price.toString().toInt() *cnt} 元" //顯示總價格
                postNum = cnt
                val requestBody = FormBody.Builder()
                    .add("p_id", "${postID}") //設定參數，送值
                    .add("p_num", "${postNum}")
                    .add("p_img", "${postImg}")
                    .build()
                val requestPost = Request.Builder()
                    .url("https://s0854006.lionfree.net/app/cart_sub_add.php") //POST API的位置
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
            /*-- 尚未達到0 => 將資料POST進cart_sub_add.php來更新數量 end --*/
            /*-- 更新畫面 start  --*/
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, CartActivity::class.java)
            activity.startActivityForResult(intent,1)
            /*-- 更新畫面 end --*/
        }
        /*-- 按下 (-) 按鈕 end --*/

        /*-- 按下 (+) 按鈕 start --*/
        holder.btn_add.setOnClickListener {
            cnt = holder.c_num.text.toString().toInt()+1
            holder.c_num.text = (cnt).toString()
            holder.c_price.text = "共 ${p_price.toString().toInt() *cnt} 元" //顯示總價格

            /*-- 將資料POST進cart_sub_add.php來更新數量 start --*/
            postNum = cnt
            val requestBody = FormBody.Builder()
                .add("p_id", "${postID}") //設定參數，送值
                .add("p_num", "${postNum}")
                .add("p_img", "${postImg}")
                .build()
            val requestPost = Request.Builder()
                .url("https://s0854006.lionfree.net/app/cart_sub_add.php") //POST API的位置
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
            /*-- 將資料POST進cart_sub_add.php來更新數量 end --*/
            /*-- 更新畫面 start  --*/
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, CartActivity::class.java)
            activity.startActivityForResult(intent,1)
            /*-- 更新畫面 end --*/
        }

        /*-- 按下(X)按鈕，刪除該商品 start --*/
        holder.btn_dele.setOnClickListener {
            /*-- post cart_delete.php 刪除cart table中這個商品的欄位 start --*/
            val requestBody = FormBody.Builder()
                .add("p_id", "${postID}")
                .build()
            val requestPost = Request.Builder()
                .url("https://s0854006.lionfree.net/app/cart_delete_item.php") //POST API的位置
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
            /*-- post cart_delete.php 刪除cart table中這個商品的欄位 end --*/
            /*-- 更新畫面 start  --*/
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, CartActivity::class.java)
            activity.startActivityForResult(intent,1)
            /*-- 更新畫面 end --*/


        }
        /*-- 按下(X)按鈕，刪除該商品 end --*/
    /*-- 調整購物車中某商品數量 end --*/


    }



    override fun getItemCount(): Int {
        // 回傳 cartList大小
        return cartList.size
    }

    class CartViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        //初始化
        var c_id: String? = null
        val c_name: TextView = itemView.findViewById(R.id.cart_name)
        val c_price: TextView = itemView.findViewById(R.id.cart_price)
        val c_img: ImageView = itemView.findViewById(R.id.cart_img)

        var c_num:TextView = itemView.findViewById(R.id.cart_quantity)
        val btn_sub: Button = itemView.findViewById(R.id.cart_sub)
        val btn_add: Button = itemView.findViewById(R.id.cart_add)
        val btn_dele: Button = itemView.findViewById(R.id.cart_dele)

        init{//constructor
            itemView.setOnClickListener{ //整個 recycleview被按下去
                listener.onItemClick(c_id.toString().toInt()-1)
            }

        }







    }
}