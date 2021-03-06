package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        picture.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        feed.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }
    }
}
