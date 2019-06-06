package com.hoseo.autofeeder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_editprofile.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dDayDataReference: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        storage = FirebaseStorage.getInstance().reference.child("user_profile_image.jpg")

        storage.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(picture)
        }.addOnFailureListener {
            Log.d("ImageTest", "Fail to load the image.")
        }

        dDayDataReference = FirebaseDatabase.getInstance().reference.child("date").child("dDay")

        dDayDataReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dDay = dataSnapshot.getValue(String::class.java)

                if(!TextUtils.isEmpty(dDay)) {textDay.text = "D+${dDay}"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDDay:onCancelled", databaseError.toException())
            }
        })

        picture.setOnClickListener {
            startActivity(Intent(this, Profile::class.java))
        }

        feed.setOnClickListener{
            startActivity(Intent(this, FeedActivity::class.java))
        }

        log.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java))
        }

        foodInfo.setOnClickListener {
            startActivity(Intent(this, FoodInfoActivity::class.java))
        }
    }
}
