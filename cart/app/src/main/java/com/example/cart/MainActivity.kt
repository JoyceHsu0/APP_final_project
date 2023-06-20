package com.example.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.cart.ui.AboutFragment
import com.example.cart.ui.HomeFragment
import com.example.cart.ui.ShopFragment
import com.example.cart.ui.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    //-- drawer layout --//
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //*-- 在drawer layout 中設定典籍上方toolbar，開啟側邊drawer  以及下方的bottom navigation START--*//
        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        //將上方的ActionBar改成toolbar
        setSupportActionBar(toolbar)

        //點擊toolbar以展開drawer
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        //!!!重要部分!!! 點擊側邊drawer中item切換不同畫面
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                /*R.id.shopFragment -> { 如果要跳至Activity
                    val intent = Intent(this@MainActivity, CartActivity::class.java)
                    startActivityForResult(intent,1)
                }*/
                R.id.homeFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()
                R.id.shopFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ShopFragment()).commit()
                R.id.aboutFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AboutFragment()).commit()

                R.id.chatbotFragment -> {
                    val intent = Intent(this@MainActivity, AIActivity::class.java)
                    startActivityForResult(intent,1)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        //下方的 bottom navigation
        /* 切換 Activity 的方法 (以CartActivity為例):
         when(it.itemId){
                R.id.cartFragment -> {
                    val intent = Intent(this@MainActivity, CartActivity::class.java)
                    startActivityForResult(intent,1)
                }
            }
        */
        val bottom_nav_view = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        //!!!重要部分!!! 點擊下方bottom navigation 中item切換不同畫面
        bottom_nav_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()
                R.id.cartFragment -> {
                    val intent = Intent(this@MainActivity, CartActivity::class.java)
                    startActivityForResult(intent,1)
                }
                R.id.userFragment -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, UserFragment()).commit()
            }
            true
        }

        if (savedInstanceState == null) { //一開始的畫面 -> homefragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            bottom_nav_view.selectedItemId = R.id.homeFragment
            navigationView.setCheckedItem(R.id.homeFragment)
        }


        //*-- 在drawer layout 中設定典籍上方toolbar，開啟側邊drawer  以及下方的bottom navigation END--*//




    }
}