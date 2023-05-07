package com.example.inquirymad.LoginAndSignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.inquirymad.Activities.ShowdataActivity
import com.example.inquirymad.Model.UsersModel
import com.example.inquirymad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserProfile : AppCompatActivity() {
    private lateinit var dbRef:DatabaseReference
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val userName=findViewById<TextView>(R.id.textView11)
        val logout=findViewById<Button>(R.id.btnLogout)
        val inquiry=findViewById<Button>(R.id.btnDriver2)

        dbRef=FirebaseDatabase.getInstance().getReference("Users")
        auth= FirebaseAuth.getInstance()

        dbRef.child(auth.currentUser?.uid.toString()).addValueEventListener(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(UsersModel::class.java)!!
                   userName.setText(user?.UserName)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UserProfile,"Error data fetch",Toast.LENGTH_LONG).show()
                }

            }
        )

        logout.setOnClickListener {
            auth.signOut()
            val intent=Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        inquiry.setOnClickListener {
            val intent=Intent(this@UserProfile,ShowdataActivity::class.java)
            startActivity(intent)
        }

    }
}