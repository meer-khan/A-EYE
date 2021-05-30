package com.muddasarajmal.aeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        // create a model class
        // create a adapter class

        var listView : ListView  = findViewById(R.id.historyList)
        var list = mutableListOf<Model>()
        list.add(Model("Faces", "The persons detected by the system",R.drawable.ic_baseline_face_24))
        list.add(Model("Locations", "The Locations detected by the system",R.drawable.ic_baseline_location_on_24))
        list.add(Model("Currency", "The Currency detected by the system",R.drawable.ic_baseline_money_24))

        listView.adapter = MyAdapter(this,R.layout.list_item,list)

        listView.setOnItemClickListener { parent:AdapterView<*>, view:View, position:Int, id:Long ->
            if (position==0){
                startActivity(Intent(this,Faces::class.java))
            }
            if (position==1){
                startActivity(Intent(this,Locations::class.java))
            }

        }
    }
}