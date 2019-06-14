package com.hoseo.autofeeder

import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_editprofile.*
import java.util.*
import java.io.*

class EditprofileActivity : AppCompatActivity() {

    /*
        DB의 user, date, birthday 주소를 저장할 변수와 Storage 주소를 저장할 변수, 캘린더를 저장할 변수
     */

    private lateinit var database: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var dateReference: DatabaseReference
    private lateinit var birthdayReference: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var calendar:Calendar
    private lateinit var calendarChosen: Calendar
    private val GALLERY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        database = FirebaseDatabase.getInstance().reference
        userReference = database.child("user")
        dateReference = database.child("date")
        birthdayReference = database.child("birthday")
        storage = FirebaseStorage.getInstance().reference.child("user_profile_image.jpg")

        /*
            Stoage 주소의 다운로드 URL를 불러와 Picasso 클래스를 이용하여 ImageView의 이미지를 변경
         */

        storage.downloadUrl.addOnSuccessListener {
            Picasso.get().load(it).into(editProfile)
        }.addOnFailureListener {
            Log.d("ImageTest", "Fail to load the image.")
        }

        /*
            DB에서 user의 값을 불러와 각각의 값을 TextView와 EditText에 표시
         */

        val userDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)

                val name = user?.userName
                val date = user?.userDate
                val weight = user?.userWeight
                val birthday = user?.userBirthday

                if(!TextUtils.isEmpty(name)) {editName.text = Editable.Factory.getInstance().newEditable(name)}
                if(!TextUtils.isEmpty(date)) {editDate.text = "${date}"}
                if(!TextUtils.isEmpty(weight)) {editWeight.text = Editable.Factory.getInstance().newEditable(weight)}
                if(!TextUtils.isEmpty(birthday)) {editBirthday.text = "${birthday}"}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadProfile:onCancelled", databaseError.toException())
                // ...
            }
        }

        /*
            DB에서 date의 값을 불러와 캘린더를 이용하여 현재 시간과 설젇된 날짜를 비교해
            며칠이 경과되었는지 계산 후 TextView에 표시
         */

        val dateDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val date = dataSnapshot.getValue(Date::class.java)

                var year: Int
                var month: Int
                var day: Int

                year = date?.dateYear!!
                month = date?.dateMonth!!
                day = date?.dateDay!!

                calendar = Calendar.getInstance()
                val today = calendar.timeInMillis/(24*60*60*1000)

                calendarChosen = Calendar.getInstance()
                calendarChosen.set(year, month, day, 0, 0, 0)
                val chosenDay = calendarChosen.timeInMillis/(24*60*60*1000)

                val dDay = (today - chosenDay).toInt()
                textDDay.text = "$dDay"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }

        /*
            DB에서 birthday의 값을 불러와 캘린더를 이용하여 현재 시간과 설젇된 날짜를 비교해
            며칠이 경과되었는지 계산 후 TextView에 표시
         */

        val birthdayDataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val birthday = dataSnapshot.getValue(Birthday::class.java)

                var year: Int
                var month: Int
                var day: Int

                year = birthday?.birthYear!!
                month = birthday?.birthMonth!!
                day = birthday?.birthDay!!

                calendar = Calendar.getInstance()
                val today = calendar.timeInMillis/(24*60*60*1000)

                calendarChosen = Calendar.getInstance()
                calendarChosen.set(year, month, day, 0, 0, 0)
                val chosenDay = calendarChosen.timeInMillis/(24*60*60*1000)

                val bDay = (today - chosenDay).toInt()
                textBDay.text = "$bDay"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("loadDate:onCancelled", databaseError.toException())
            }
        }

        userReference.addValueEventListener(userDataListener)
        dateReference.addValueEventListener(dateDataListener)
        birthdayReference.addValueEventListener(birthdayDataListener)

        /*
            버튼을 누르면 화면 전환이나 Dialog가 생성되도록 onClickListener 설정
         */

        editDateButton.setOnClickListener {
            val calendarFragment = DatePickerFragment()
            calendarFragment.show(fragmentManager, "EditDate")
        }

        editBirthdayButton.setOnClickListener {
            val calendarFragment = BirthdayPickerFragment()
            calendarFragment.show(fragmentManager, "EditBirthday")
        }

        cancelButton.setOnClickListener {
            onBackPressed()
        }

        /*
            변경한 값을 DB에 저장
         */

        applyButton.setOnClickListener {
            val name = editName.text.toString()
            val date = editDate.text.toString()
            val weight = editWeight.text.toString()
            val birthday = editBirthday.text.toString()
            val bDay = textBDay.text.toString()

            writeUserData(name, date, weight, birthday, bDay)

            val dDay = textDDay.text.toString()

            dateReference.child("dDay").setValue(dDay)
            birthdayReference.child("bDay").setValue(bDay)

            onBackPressed()
        }

        editProfile.setOnClickListener {
            choosePhotoFromGallary()
        }

    }

    private fun writeUserData(userName: String, userDate: String, userWeight: String, userBirthday: String, userBDay: String) {
        val user = User(userName, userDate, userWeight, userBirthday, userBDay)
        userReference.setValue(user)
    }

    /*
        갤러리를 표시하고, 선택된 사진을 화면에 표시하는 함수
        사진을 고르면 Storage에도 사진을 업로드
     */

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val contentURI = data!!.data
            try
            {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                val path = saveImage(bitmap)
                Toast.makeText(this@EditprofileActivity, "이미지가 성공적으로 업로드되었습니다.", Toast.LENGTH_SHORT).show()
                editProfile!!.setImageBitmap(bitmap)
                storage.putFile(contentURI!!)
                    .addOnFailureListener {
                        Log.d("ImageTest", "Fail to upload the image on Firebase")
                    }.addOnSuccessListener {
                        val urlUpload = storage.downloadUrl
                        Log.d("ImageTest", "File location: $urlUpload")
                    }
            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@EditprofileActivity, "이미지를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }
}
