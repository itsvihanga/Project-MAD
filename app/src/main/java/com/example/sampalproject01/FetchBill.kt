package com.example.sampalproject01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchBill : AppCompatActivity() {

    private lateinit var billRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var billList: ArrayList<BillModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_fetching)

        billRecyclerView = findViewById(R.id.rvBill)
        billRecyclerView.layoutManager = LinearLayoutManager(this)
        billRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        billList = arrayListOf<BillModel>()

        getBillData()

    }

    private fun getBillData() {

        billRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                billList.clear()
                if (snapshot.exists()){
                    for (billSnap in snapshot.children){
                        val billData = billSnap.getValue(BillModel::class.java)
                        billList.add(billData!!)
                    }
                    val mAdapter = BillAdapter(billList)
                    billRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : BillAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchBill, BillDetails::class.java)

                            //put extras
                            intent.putExtra("BillId", billList[position].billId)
                            intent.putExtra("Month", billList[position].month)
                            intent.putExtra("Current", billList[position].current)
                            intent.putExtra("Water", billList[position].water)
                            startActivity(intent)
                        }

                    })

                    billRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}