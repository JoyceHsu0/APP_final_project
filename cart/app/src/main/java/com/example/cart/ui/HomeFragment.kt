package com.example.cart.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.cart.ProductDeatilActivity
import com.example.cart.R
import com.example.cart.databinding.FragmentHomeBinding
import com.example.cart.home.HomeGVAdapter
import com.example.cart.home.HomeGridViewModal


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    //*-- grid view --*//
    lateinit var homeGRV: GridView
    lateinit var homeGList: List<HomeGridViewModal>
    //*-- grid view --*//

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//*-- for imageSlider1 start--*//
        val imageSlider1 = binding.homeIs1
        val imageList1 = ArrayList<SlideModel>()

        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_main_su.jpg"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su1.jpg"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su2.jpg"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su3.jpg"))


        imageSlider1.setImageList(imageList1, ScaleTypes.FIT)
        imageSlider1.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider1.startSliding(3000)
//*-- for imageSlider1 end--*//

//*-- for imageSlider2 start--*//
        val imageSlider2 = binding.homeIs2
        val imageList2 = ArrayList<SlideModel>()

        imageList2.add(SlideModel("https://s0854006.lionfree.net/app/img/new1.jpg"))
        imageList2.add(SlideModel("https://s0854006.lionfree.net/app/img/new2.jpg"))


        imageSlider2.setImageList(imageList2, ScaleTypes.FIT)
        imageSlider2.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider2.startSliding(5000)

//*-- for HomeViewPagerAdapter2 end--*//

//*-- for HomeGridViewAdapter start --*//
        homeGRV = binding.homeGv
        homeGList = ArrayList<HomeGridViewModal>()

        //將資料加入 homeGList 中:
        homeGList = homeGList+ HomeGridViewModal("鮭魚", R.drawable.salmon)
        homeGList = homeGList+ HomeGridViewModal("洋蔥鮭魚", R.drawable.onion_salmon)
        homeGList = homeGList+ HomeGridViewModal("炙燒起司鮮蝦", R.drawable.cheese_shrimp)
        homeGList = homeGList+ HomeGridViewModal("甜蝦", R.drawable.sweet_shrimp)
        //初始化 HomeGVAdapter
        val homeGAdapter = getActivity()?.let { HomeGVAdapter(gridList = homeGList, it) }

        //設置Adapter到grid view
        homeGRV.numColumns=2
        homeGRV.adapter = homeGAdapter

        // grid view 的onitemclick
        homeGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(getActivity(), ProductDeatilActivity::class.java)
            intent.putExtra("ID", position+1)
            startActivityForResult(intent,1)
        }

//*-- for HomeGridViewAdapter end --*//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }







}







