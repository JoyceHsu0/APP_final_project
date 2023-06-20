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
import com.example.cart.checkout.CheckoutRVAdapter
import com.example.cart.checkout.CheckoutRVModal
import com.example.cart.checkout.RetrofitAPI
import com.example.cart.databinding.FragmentCheckoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var checkoutRV: RecyclerView
    lateinit var loadingPB: ProgressBar
    lateinit var checkoutRVAdapter: CheckoutRVAdapter
    lateinit var checkoutList: ArrayList<CheckoutRVModal>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 variable with their ids.
        checkoutRV = binding.idRVCheckout
        loadingPB = binding.checkoutPBLoading
        // on below line we are initializing our list
        checkoutList = ArrayList()

        // 呼叫 get all Checkout method 來get資料
        getAllCheckout()

        return root
    }

    private fun getAllCheckout() {
        // 建立 retrofit builder 然後 passing base url
        val retrofit = Retrofit.Builder()
            .baseUrl("https://s0854006.lionfree.net/app/")
            // calling add Converter factory as Gson converter factory.
            // at last we are building our retrofit builder.
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // below line is to create an instance for our retrofit api class.
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

        // on below line we are calling a method to get all the courses from API.
        val call: Call<ArrayList<CheckoutRVModal>?>? = retrofitAPI.getAllCheckout()

        //calling method to enqueue and calling all the data from array list.
        call!!.enqueue(object : Callback<ArrayList<CheckoutRVModal>?> {
            override fun onResponse(
                call: Call<ArrayList<CheckoutRVModal>?>,
                response: Response<ArrayList<CheckoutRVModal>?>
            ) {
                if (response.isSuccessful) {
                    loadingPB.visibility = View.GONE
                    checkoutList = response.body()!!
                }

                // on below line we are initializing our adapter.
                checkoutRVAdapter = CheckoutRVAdapter(checkoutList)


                // on below line we are setting adapter to recycler view.
                checkoutRV.adapter = checkoutRVAdapter

                /*
                checkoutRVAdapter.setOnItemClickListener(object: CheckoutRVAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        //Toast.makeText(this@MainActivity, "item$position", Toast.LENGTH_SHORT).show()

                        val intent = Intent(getActivity(), ProductDeatilActivity::class.java)
                        intent.putExtra("ID", position)
                        startActivityForResult(intent,1)
                    }

                })*/


            }

            override fun onFailure(call: Call<ArrayList<CheckoutRVModal>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(activity, "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })




    }

}