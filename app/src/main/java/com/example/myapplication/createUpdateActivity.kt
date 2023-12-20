package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCreateUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class createUpdateActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityCreateUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
    if (intent.getStringExtra("MODE").equals("CREATE")) {
        title="Add"


        binding.AddNotes.setOnClickListener {
            val key = database.child("myApplication").push().key
            database.child("myApplication").child(key!!)
                .setValue(Data(binding.EdTask.text.toString(), key)).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Notes Added", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                }
            }
        }
            }
        else{
            title="Update"
        binding.AddNotes.setText("Update Note")
        binding.EdTask.setText(intent.getStringExtra("DATA"))
        val dbRf= Firebase.database.getReference("myApplication").child(intent.getStringExtra("KEY")!!)
        val data=Data(binding.EdTask.text.toString(), intent.getStringExtra("KEY")!!)
//        val data=Data()

        dbRf.setValue(data).addOnCompleteListener{
            task->
            if (task.isSuccessful){
                Toast.makeText(this, "Notes Added", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
            }
        }


    }
    }
}