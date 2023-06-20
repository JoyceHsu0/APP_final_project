package com.example.cart.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.ProductDeatilActivity
import com.example.cart.R
import com.example.cart.databinding.FragmentShopBinding
import com.example.cart.product.ProductRVAdapter
import com.example.cart.product.ProductRVModal
import com.example.cart.product.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null

    private val binding get() = _binding!!

    // creating variables.
    lateinit var pRV: RecyclerView
    lateinit var loadingPB: ProgressBar
    lateinit var pRVAdapter: ProductRVAdapter
    lateinit var pList: ArrayList<ProductRVModal>

    //reference(using setOnItemClickListener): https://www.youtube.com/@_foxandroid/videos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 variable with their ids.
        pRV = binding.idRVProduct
        loadingPB = binding.idPBLoading
        // initializing our list
        pList = ArrayList()

        // 呼叫 get all Product method 來get資料
        getAllProduct()

        return root
    }

    private fun getAllProduct() {
        // creating a retrofit builder and passing our base url
        val retrofit = Retrofit.Builder()
            .baseUrl("https://s0854006.lionfree.net/app/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // below line is to create an instance for our retrofit api class.
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        // on below line we are calling a method to get all the product from API.
        val call: Call<ArrayList<ProductRVModal>?>? = retrofitAPI.getAllProduct()

        // on below line we are calling method to enqueue and calling
        // all the data from array list.
        call!!.enqueue(object : Callback<ArrayList<ProductRVModal>?> {
            override fun onResponse(
                call: Call<ArrayList<ProductRVModal>?>,
                response: Response<ArrayList<ProductRVModal>?>
            ) {
                if (response.isSuccessful) {
                    loadingPB.visibility = View.GONE
                    pList = response.body()!!
                }

                // on below line we are initializing our adapter.
                pRVAdapter = ProductRVAdapter(pList)


                // on below line we are setting adapter to recycler view.
                pRV.adapter = pRVAdapter

                pRVAdapter.setOnItemClickListener(object: ProductRVAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val intent = Intent(getActivity(), ProductDeatilActivity::class.java)
                        intent.putExtra("ID", position)
                        startActivityForResult(intent,1)
                    }
                })


            }

            override fun onFailure(call: Call<ArrayList<ProductRVModal>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(activity, "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })




    }


}