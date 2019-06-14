package com.hoseo.autofeeder


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*


class FeedingAmountFragment : Fragment() {

    /*
        DB의 amount 주소를 저장할 변수
     */

    private lateinit var amountReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_feeding_amount, container, false)
        val feedAmountBar: SeekBar = view.findViewById(R.id.feedAmount)
        val textFeedAmount: TextView = view.findViewById(R.id.textFeedAmount)

        amountReference = FirebaseDatabase.getInstance().reference.child("amount")

        /*
            DB에서 amount 값을 불러와 SeekBar와 TextView에 적용
         */

        amountReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val amount = dataSnapshot.getValue(Int::class.java)

                feedAmountBar.progress = amount!!

                if(amount==1) {
                    textFeedAmount.text = "소"
                } else if(amount==2) {
                    textFeedAmount.text = "중"
                } else if(amount==3) {
                    textFeedAmount.text = "대"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadAmount:onCancelled", databaseError.toException())
            }
        })

        feedAmountBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            /*
                SeekBar를 드래그할 때마다 값에 따라 TextView의 문자도 변경
             */

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if(i==1) {
                    textFeedAmount.text = "소"
                } else if(i==2) {
                    textFeedAmount.text = "중"
                } else if(i==3) {
                    textFeedAmount.text = "대"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        /*
            취소 버튼을 누르면 이전 화면으로 돌아감
         */

        val cancelBtn: Button = view.findViewById(R.id.amountCancelButton)
        cancelBtn.setOnClickListener{
            activity?.finish()
        }

        /*
            적용 버튼을 누르면 Toast 메세지로 적용을 알리고 DB에 값을 저장
         */

        val submitBtn: Button = view.findViewById(R.id.amountSubmitButton)
        submitBtn.setOnClickListener {
            val feedingAmount = feedAmountBar.progress
            amountReference.setValue(feedingAmount)

            Toast.makeText(
                activity,
                "공급량이 변경되었습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }

        return view
    }

}
