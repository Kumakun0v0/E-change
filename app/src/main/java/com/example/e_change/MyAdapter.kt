package com.example.e_change

import android.app.Activity
import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter (private val context:Activity, private val arrayList: ArrayList<Item>):ArrayAdapter<Item>(context, R.layout.list_item, arrayList){
    override fun getView(position:Int, convertView: View?, parent:ViewGroup):View{
        val inflater:LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val imageView: ImageView = view.findViewById(R.id.itemImg)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val lastMsg: TextView = view.findViewById(R.id.lastMessage)
        val lastMsgTime: TextView = view.findViewById(R.id.msgTime)

        var timestamp = arrayList[position].lastMsgTime
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(milliseconds)
        val date = sdf.format(netDate).toString()

        val storage = Firebase.storage
        val storageRef = storage.reference
        storageRef.child(arrayList[position].imageUrl).downloadUrl.addOnSuccessListener { img ->
            Log.d(TAG, img.toString())
            // Data for "images/island.jpg" is returned, use this as needed
            Picasso.get().load(img).into(imageView);
            itemName.text = arrayList[position].name
            lastMsg.text = arrayList[position].lastMessage
            lastMsgTime.text = date
        }.addOnFailureListener { error ->
            Log.e(TAG, error.toString())
            // Handle any errors
        }

        return view

    }
}
