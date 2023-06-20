package com.example.cart.checkout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.R

class CheckoutRVAdapter(

    private var checkoutList: ArrayList<CheckoutRVModal>,
) : RecyclerView.Adapter<CheckoutRVAdapter.CheckoutViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutRVAdapter.CheckoutViewHolder {
        // inflate recycler view 的 layout file
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycleview_checkout,
            parent, false
        )
        return CheckoutViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CheckoutRVAdapter.CheckoutViewHolder, position: Int) {
        // setting data to text view
        holder.checkout_id.text = "訂單編號: ${checkoutList.get(position).checkout_id}"

        holder.checkout_product.text="商品名稱:\n${checkoutList.get(position).product_name}"

        holder.checkout_total.text="商品總金額: ${checkoutList.get(position).total} 元"
        holder.checkout_date.text = "訂單日期: ${checkoutList.get(position).date}"


    }

    override fun getItemCount(): Int {
        // return size of our list
        return checkoutList.size
    }

    class CheckoutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val checkout_id: TextView = itemView.findViewById(R.id.checkout_id)
        val checkout_product: TextView = itemView.findViewById(R.id.checkout_product)
        val checkout_total: TextView = itemView.findViewById(R.id.checkout_total)
        val checkout_date: TextView = itemView.findViewById(R.id.checkout_date)
    }

}