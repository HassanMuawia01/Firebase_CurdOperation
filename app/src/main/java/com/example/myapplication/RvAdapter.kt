package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class RvAdapter(var datalist: ArrayList<Data>, val context: Context):
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {


    open class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textView=itemView.findViewById<TextView>(R.id.showText)
        val deletebtn=itemView.findViewById<Button>(R.id.DeletedBtn)
        val updateBtn=itemView.findViewById<Button>(R.id.updatebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view=LayoutInflater.from(context).inflate(R.layout.item, parent,false)
    return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(datalist.get(position).data)
        holder.deletebtn.setOnClickListener{
            val key=datalist.get(position).key
            val dbRf= Firebase.database.getReference("myApplication").child(key!!)
            dbRf.removeValue().addOnCompleteListener{
                if (it.isSuccessful){
                   notifyDataSetChanged()
                    Toast.makeText(context,"Remove Data",Toast.LENGTH_SHORT).show()

                }
            }

        }
        holder.updateBtn.setOnClickListener{
            val intent=Intent(context,createUpdateActivity::class.java)
            intent.putExtra("MODE","UPDATE")
            intent.putExtra("KEY",datalist.get(position).key)
            intent.putExtra("DATA",datalist.get(position).data)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }




}


