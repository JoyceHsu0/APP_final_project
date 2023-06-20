package com.example.cart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cart.R
import com.example.cart.databinding.FragmentShopBinding
import com.example.cart.databinding.FragmentUserBinding
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException


class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root



        /*-- 按下查看訂單按鈕 start--*/
        val btn_check_checkout = binding.checkCheckout
        btn_check_checkout.setOnClickListener {
            /*-- 呈現checkout內容 跳至checkout fragment start --*/
            val transaction = getParentFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_container, CheckoutFragment()).commit()
            /*-- 呈現checkout內容 跳至checkout fragment end --*/
        }
        /*-- 按下查看訂單按鈕 end--*/


        getcheckoutnum()



        return root
    }

    private fun getcheckoutnum(){
        val urlBuilder = "https://s0854006.lionfree.net/app/checkout_num.php".toHttpUrlOrNull()
            ?.newBuilder()
            ?.addQueryParameter("account","appuser") //設定參數(資料庫欄位)


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
                    val total = jsonObject.getInt("cnt")
                    binding.checkoutNum.text=total.toString()


                } else {
                    println("Request failed")
                    binding.checkoutNum.text="0"

                }
            }
        })

    }

}


