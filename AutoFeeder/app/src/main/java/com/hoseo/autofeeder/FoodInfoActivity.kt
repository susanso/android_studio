package com.hoseo.autofeeder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_food_info.*
import kotlinx.android.synthetic.main.activity_log.*

class FoodInfoActivity : AppCompatActivity() {
    private lateinit var foodReference: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_info)

        foodReference = FirebaseDatabase.getInstance().reference.child("foodInfo")
        storage = FirebaseStorage.getInstance().reference

        var foodInfoList: MutableList<FoodInfo> = mutableListOf()

        foodReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val foodInfo = p0.getValue(FoodInfo::class.java)

                if (foodInfo != null) {
                    foodInfoList.add(foodInfo)
                }

                foodList.adapter = FoodInfoAdapter(foodInfoList)
                foodList.layoutManager = LinearLayoutManager(this@FoodInfoActivity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }
}
