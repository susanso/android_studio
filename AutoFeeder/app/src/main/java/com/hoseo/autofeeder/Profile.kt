package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val name = intent.getStringExtra("name")
        val date = intent.getStringExtra("date")
        val birthday = intent.getStringExtra("birthday")
        val weight = intent.getStringExtra("weight")

        if(!TextUtils.isEmpty(name)) {textName.text = "${name}"}
        if(!TextUtils.isEmpty(date)) {textDate.text = "${date}"}
        if(!TextUtils.isEmpty(birthday)) {textBirthday.text = "${birthday}"}
        if(!TextUtils.isEmpty(weight)) {textWeight.text = "${weight}g"}

        profileEdit.setOnClickListener {
            startActivity(Intent(this, EditprofileActivity::class.java))
        }
    }
}
