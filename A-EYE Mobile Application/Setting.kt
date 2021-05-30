package com.muddasarajmal.aeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class Setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val listView = findViewById<ListView>(R.id.settingList)
        var list = mutableListOf<Model>()
        list.add(Model("Account Setting", "The persons detected by the system",R.drawable.ic_profile))
        list.add(Model("System Setting", "The Locations detected by the system",R.drawable.ic_update))

        listView.adapter = MyAdapter(this,R.layout.list_item,list)

        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->
            if (position==0){
                startActivity(Intent(this,Profile::class.java))
            }
            if (position==1){
                startActivity(Intent(this,SystemSetting::class.java))
//                startActivity(Intent(this,::class.java))
            }

        }
    }

}