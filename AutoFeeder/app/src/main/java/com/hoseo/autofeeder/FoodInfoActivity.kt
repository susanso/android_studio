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

    /*
        DB의 foodInfo 주소를 저장할 변수와 Storage 주소를 저장할 변수
     */

    private lateinit var foodReference: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_info)

        foodReference = FirebaseDatabase.getInstance().reference.child("foodInfo")
        storage = FirebaseStorage.getInstance().reference

        var foodInfoList: MutableList<FoodInfo> = mutableListOf()

        /*
            DB의 foodInfo를 MutableList foodInfoList에 저장
         */

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

                /*
                    저장한 값을 가지고 FoodInfoAdapter에 연결
                 */

                foodList.adapter = FoodInfoAdapter(foodInfoList)
                foodList.layoutManager = LinearLayoutManager(this@FoodInfoActivity)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }
}
