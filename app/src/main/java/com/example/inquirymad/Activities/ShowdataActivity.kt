package com.example.inquirymad.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inquirymad.Adapter.inquiryAdpter
import com.example.inquirymad.Model.ModelClassInquiry
import com.example.inquirymad.R
import com.google.firebase.database.*

class ShowdataActivity : AppCompatActivity() {

    private lateinit var employeeItem: RecyclerView
    private lateinit var empList:ArrayList<ModelClassInquiry>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdata)

        employeeItem=findViewById(R.id.rId)
        employeeItem.layoutManager= LinearLayoutManager(this,)
        employeeItem.setHasFixedSize(true)
        empList= arrayListOf<ModelClassInquiry>()

        getEmployeeData()
    }
    private fun getEmployeeData(){
        employeeItem.visibility= View.GONE
        dbRef= FirebaseDatabase.getInstance().getReference("Inquiry")
        dbRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (inquiry in snapshot.children){
                        val inqData=inquiry.getValue(ModelClassInquiry::class.java)
                        empList.add(inqData!!)
                    }
                    val mAdapter= inquiryAdpter(empList)

                    mAdapter.setOnItemClickListener(object:inquiryAdpter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent=Intent(this@ShowdataActivity,UpdateDelete::class.java)
                            //put extras
                            intent.putExtra("id",empList[position].Yourid)
                            intent.putExtra("ownerMail",empList[position].ownerMail)
                            intent.putExtra("mobile",empList[position].Usermobile)
                            intent.putExtra("inquiry",empList[position].inquiry)
                            startActivity(intent)
                        }
                    })

                    employeeItem.adapter=mAdapter
                    employeeItem.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }

        })
    }
}