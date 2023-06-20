package com.example.cart.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.R
import com.squareup.picasso.Picasso



class ProductRVAdapter (

    //product的list
    private var productList: ArrayList<ProductRVModal>,
) : RecyclerView.Adapter<ProductRVAdapter.ProductViewHolder>() {

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
    ): ProductRVAdapter.ProductViewHolder {

        // inflating layout file
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item,
            parent, false
        )

        return ProductViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: ProductRVAdapter.ProductViewHolder, position: Int) {
        // 設定textview和imageview的內容

        holder.p_name.text = productList.get(position).p_name
        var p_c = productList.get(position).content
        val p_c_new = p_c?.replace("</br>", "，")

        holder.p_content.text = p_c_new
        holder.p_price.text = "${productList.get(position).price} 元"
        holder.p_id = productList.get(position).ID
        Picasso.get()
            .load("https://s0854006.lionfree.net/app/img/${productList.get(position).picture}")
            .into(holder.p_img)

    }

    override fun getItemCount(): Int {
        // 回傳list的大小
        return productList.size
    }

    class ProductViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        //初始化變數
        var p_id: String? = null
        val p_name: TextView = itemView.findViewById(R.id.product_name)
        val p_content: TextView = itemView.findViewById(R.id.product_content)
        val p_price: TextView = itemView.findViewById(R.id.product_price)
        val p_img: ImageView = itemView.findViewById(R.id.img)

        init{//constructor
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }






}