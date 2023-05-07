package com.example.inquirymad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.inquirymad.Model.ModelClassInquiry
import com.example.inquirymad.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddInquiriesActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inquiries)

        val UID=intent.getStringExtra("UID").toString()

        val intent= Intent(this,UpdateDelete::class.java)
        intent.putExtra("UID",UID)
        startActivity(intent)

        dbRef=FirebaseDatabase.getInstance().getReference("Inquiry")//.child(UID)


        var myEmail=findViewById<EditText>(R.id.myMail)
        var ownerEmail=findViewById<EditText>(R.id.oMail)
        var phoneNum=findViewById<EditText>(R.id.mobile)
        var inquiry=findViewById<EditText>(R.id.inquiry)
        var submit=findViewById<Button>(R.id.submit)
        var cancel=findViewById<Button>(R.id.CANCEL)

        submit.setOnClickListener{

            var MEmail=myEmail.text.toString()
            var OEmail=ownerEmail.text.toString()
            var Mobile=phoneNum.text.toString()
            var Inquiry=inquiry.text.toString()

            if (MEmail.isNotEmpty() && OEmail.isNotEmpty() && Mobile.isNotEmpty() && Inquiry.isNotEmpty()){

                val id=dbRef.push().key!!
                val data= ModelClassInquiry(id,OEmail,MEmail,Mobile,Inquiry)

                dbRef.child(id).setValue(data).addOnCompleteListener{
                    Toast.makeText(this,"Data insert succesfully", Toast.LENGTH_LONG).show()

                         val intent=Intent(this, ShowdataActivity::class.java)
                         startActivity(intent)

                }.addOnFailureListener{ err->
                    Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
                }


            }else{
                Toast.makeText(this,"Please fill Empty fields",Toast.LENGTH_LONG).show()
            }



        }

    }
}