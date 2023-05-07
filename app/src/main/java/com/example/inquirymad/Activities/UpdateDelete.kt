package com.example.inquirymad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.inquirymad.R
import com.google.firebase.database.*

class UpdateDelete : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)

        val inquiry=findViewById<TextView>(R.id.UDinquiry)
        val mobile=findViewById<TextView>(R.id.UDmobile)
        val id=findViewById<TextView>(R.id.UDid)
        val mail=findViewById<TextView>(R.id.UDemail)

        val update=findViewById<Button>(R.id.update)
        val delete=findViewById<Button>(R.id.delete)

        val UID=intent.getStringExtra("UID").toString()

        database=FirebaseDatabase.getInstance().getReference("Inquiry").child(UID)

        val pid = intent.getStringExtra("id").toString()
        val pOmail = intent.getStringExtra("ownerMail").toString()
        val pNumber = intent.getStringExtra("mobile").toString()
        val pInquiry = intent.getStringExtra("inquiry").toString()

        id.setText(pid)
        mail.setText(pOmail)
        mobile.setText(pNumber)
        inquiry.setText(pInquiry)


        update.setOnClickListener {
            val newInquiry=inquiry.text.toString()
            val newMobile=mobile.text.toString()
            val newMail=mail.text.toString()

            updateDetail(newMail,newMobile,newInquiry)
        }

    delete.setOnClickListener {
        DataDelete(pid)
    }

    }

    fun DataDelete(id:String){

        database.child(id).removeValue().addOnCompleteListener {

        }.addOnFailureListener {

        }
    }


    fun updateDetail(mail: String, mobile:String,inquiry:String){
        val updates= mapOf<String,String>(
            "mail" to mail,
            "mobile" to mobile,
            "inquiry" to inquiry)

        val id = intent.getStringExtra("id").toString()

        database.child(id).updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(this,"update details Success",Toast.LENGTH_LONG).show()
                val intent=Intent(this, ShowdataActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                Toast.makeText(this,"Details Update Unsuccess",Toast.LENGTH_LONG).show()
            }
    }
}