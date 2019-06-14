package com.hoseo.autofeeder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        supportFragmentManager.beginTransaction().replace(R.id.feedView, FeedingTimeFragment()).commit()

        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    /*
                        선택된 탭에 따라 다른 화면을 표시
                     */
                    0 -> supportFragmentManager.beginTransaction().replace(R.id.feedView, FeedingTimeFragment()).commit()
                    1 -> supportFragmentManager.beginTransaction().replace(R.id.feedView, FeedingAmountFragment()).commit()
                }
            }
        })
    }

}
