package com.example.demowebviewandroid

import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.astuetz.PagerSlidingTabStrip

class MainActivity : AppCompatActivity() {

    var tabLayout: PagerSlidingTabStrip? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        val titles = listOf("Tab 1", "Tab 2", "tab 3")




        val adapter = MyAdapter(supportFragmentManager , titles)
        viewPager!!.adapter = adapter
//        viewPager?.offscreenPageLimit = 2

        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        tabLayout?.shouldExpand = true

        tabLayout?.setViewPager(viewPager!!)

    }

    fun reLoad(){
        viewPager?.adapter?.notifyDataSetChanged()

    }

}


