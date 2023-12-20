package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding
    private var list=ArrayList<Data>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbRf= Firebase.database.getReference("myApplication")
        binding.rvdesign.layoutManager=LinearLayoutManager(this)


        val rvAdapter=RvAdapter(list,this)
        binding.rvdesign.adapter=rvAdapter


        // Read from the database
                dbRf.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                      list.clear()
                        for (dataSnap in dataSnapshot.children){
                            val data=dataSnap.getValue(Data::class.java)
                            list.add(data!!)
                         rvAdapter.notifyDataSetChanged()

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value

                    }
                })




        binding.createimage.setOnClickListener{
            startActivity(Intent(this,createUpdateActivity::class.java).putExtra("MODE","CREATE"))
    }

}
}

