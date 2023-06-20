package com.example.cart.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.cart.R
import com.example.cart.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

//*-- for imageSlider1 start--*//
        val imageSlider1 = binding.aboutPrjC2HomeIs
        val imageList1 = ArrayList<SlideModel>()

        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_main_su.jpg","首頁圖片1_使用Adobe Illustrator進行修圖"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su1.jpg","首頁圖片2"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su2.jpg","首頁圖片3"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/home_su3.jpg","首頁圖片4"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/new1.jpg", "新品上市圖1_使用Canva以及Adobe Illustrator進行修圖"))
        imageList1.add(SlideModel("https://s0854006.lionfree.net/app/img/new2.jpg", "新品上市圖2_使用Canva以及Adobe Illustrator進行修圖"))


        imageSlider1.setImageList(imageList1, ScaleTypes.FIT)
        imageSlider1.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider1.startSliding(2000)

        imageSlider1.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {

            }
            override fun doubleClick(position: Int) {
                when (position) {
                    0 -> {
                        println("選擇首頁圖片1------")
                        val url = "https://rtrp.jp/articles/57817/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                    1 -> {  println("選擇首頁圖片2------")
                        val url = "https://we-xpats.com/zh-tw/guide/as/jp/detail/2782/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)}
                    2 -> {  println("選擇首頁圖片3------")
                        val url = "https://hitosara.com/0006127790/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)}
                    3 -> {  println("選擇首頁圖片4------")
                        val url = "https://parade.com/food/types-of-sushi"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)}
                }
            } })
//*-- for imageSlider1 end--*//

//*-- for imageSlider2 start--*//
        val imageSlider2 = binding.aboutPrjC2LogoIs
        val imageList2 = ArrayList<SlideModel>()

        imageList2.add(SlideModel("https://s0854006.lionfree.net/app/img/main_logo.png", "本系統的Logo_使用Adobe Illustrator繪製"))
        imageList2.add(SlideModel("https://s0854006.lionfree.net/app/img/nav_header.png", "Drawer navigation header_使用Adobe Illustrator繪製"))
        imageList2.add(SlideModel("https://s0854006.lionfree.net/app/img/appuser_card.png", "會員卡片設計_使用Canva以及Adobe Illustrator繪製"))

        imageSlider2.setImageList(imageList2, ScaleTypes.FIT)
        imageSlider2.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider2.startSliding(2000)
//*-- for imageSlider2 end--*//
        return root
    }

}