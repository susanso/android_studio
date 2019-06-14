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
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.math.round
import kotlin.math.roundToInt

class Profile : AppCompatActivity() {

    /*
        DB의 user, date의 주소와 Storage의 주소를 저장할 변수
     */

    private lateinit var userReference: DatabaseReference
    private lateinit var dateReference: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userReference = FirebaseDatabase.getInstance().reference.child("user")
        dateReference = FirebaseDatabase.getInstance().reference.child("date")

        storage = FirebaseStorage.getInstance().reference.child("user_profile_image.jpg")

        /*
            Storage에 저장된 이미지의 URL을 불러와 Picasso 클래스를 이용하여 ImageView의 이미지를 표시
         */

        storage.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(profile)
        }.addOnFailureListener {
            Log.d("ImageTest", "Fail to load the image.")
        }

        /*
            DB의 user를 불러와 TextView에 값을 표시
         */

        val userDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)

                val name = user?.userName
                val date = user?.userDate
                val weight = user?.userWeight
                val birthday = user?.userBirthday
                val bDay = user?.userBDay

                if(!TextUtils.isEmpty(name)) {textName.text = "${name}"}
                if(!TextUtils.isEmpty(date)) {textDate.text = "${date}"}
                if(!TextUtils.isEmpty(weight)) {textWeight.text = "${weight}g"}
                if(!TextUtils.isEmpty(birthday)) {textBirthday.text = "${birthday}"}

                val bDayInt = bDay!!.toInt()
                val weightDouble = weight!!.toDouble()

                /*
                    태어난 후 경과된 일 수와 몸무게를 바탕으로 권장사료양을 계산, TextView에 표시
                 */

                val amountRecommended = when(bDayInt) {
                    in 0..60 -> (weightDouble*0.07).roundToInt()
                    in 60..90 -> (weightDouble*0.06).roundToInt()
                    in 90..150 -> (weightDouble*0.05).roundToInt()
                    in 150..365 -> (weightDouble*0.03).roundToInt()
                    in 365..1825 -> (weightDouble*0.025).roundToInt()
                    else -> (weightDouble*0.02).roundToInt()
                }

                textAmount.text = "${amountRecommended}g"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadProfile:onCancelled", databaseError.toException())
                // ...
            }
        }

        /*
            DB의 dDay를 불러와 TextView에 표시
         */

        val dateDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val date = dataSnapshot.getValue(Date::class.java)

                val dDay = date?.dDay
                if(!TextUtils.isEmpty(dDay)) {textDay.text = "+${dDay}일"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }
        userReference.addValueEventListener(userDataListener)
        dateReference.addValueEventListener(dateDataListener)

        /*
            화면 전환을 위해 버튼에 onClickListener를 설정
         */

        profileEdit.setOnClickListener {
            startActivity(Intent(this, EditprofileActivity::class.java))
        }
    }
}
