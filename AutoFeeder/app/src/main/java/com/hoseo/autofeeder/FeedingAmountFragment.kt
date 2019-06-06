package com.hoseo.autofeeder


import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feeding_amount.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FeedingAmountFragment : Fragment() {

    private lateinit var amountReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_feeding_amount, container, false)
        val feedAmountBar: SeekBar = view.findViewById(R.id.feedAmount)
        val textFeedAmount: TextView = view.findViewById(R.id.textFeedAmount)

        amountReference = FirebaseDatabase.getInstance().reference.child("amount")

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

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
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

        val cancelBtn: Button = view.findViewById(R.id.amountCancelButton)
        cancelBtn.setOnClickListener{
            activity?.finish()
        }

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
