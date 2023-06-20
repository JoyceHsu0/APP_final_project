package com.example.cart.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cart.R
import com.example.cart.product.ProductRVModal

import com.squareup.picasso.Picasso

class HomeGVAdapter (
    //建立 gridlist 和 context 的變數
    private val gridList: List<HomeGridViewModal>,
    private val context: Context
) :
    BaseAdapter() {
    // 在 base adapter 中建立 layout inflater, home TextView, home ImageView 的變數
    private var layoutInflater: LayoutInflater? = null
    private lateinit var homeGTV: TextView
    private lateinit var homeGIV: ImageView

    // return the count of grid list
    override fun getCount(): Int {
        return gridList.size
    }

    // return the item of grid view.
    override fun getItem(position: Int): Any? {
        return null
    }

    // return item id of grid view.
    override fun getItemId(position: Int): Long {
        return 0
    }

    // 取得 grid view 中個別的物件
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        // 檢查 layout inflater 是否為 null, 如果為null則將它初始化
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        // 檢查 convert view 是否為 null, 如果為null則將它初始化
        if (convertView == null) {
            //passing layout file passing
            convertView = layoutInflater!!.inflate(R.layout.home_gridview_item, null)
        }

        // 初始化 homegride image view & homegride text view
        homeGIV = convertView!!.findViewById(R.id.grid_img)
        homeGTV = convertView!!.findViewById(R.id.grid_txt)

        //setting image for our homegride image view
        homeGIV.setImageResource(gridList.get(position).gridImg)
        //setting text for our homegride text view
        homeGTV.setText(gridList.get(position).gridtxt)
        //最後回傳 convertView
        return convertView
    }
}
