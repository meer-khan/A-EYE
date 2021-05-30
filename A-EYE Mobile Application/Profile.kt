package com.muddasarajmal.aeye

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.edit_profile_dialogue.view.*

class Profile : AppCompatActivity() {
    private lateinit var sharedPreferences :SharedPreferences
    private lateinit var db:FirebaseFirestore
    private lateinit var dialogView :View
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        sharedPreferences = getSharedPreferences("shared_PREF", Context.MODE_PRIVATE)
        db = FirebaseFirestore.getInstance()

        fetchData()

        val editBtn = findViewById<Button>(R.id.editProfileBtn)

        editBtn.setOnClickListener {
            val dialogView = View.inflate(this,R.layout.edit_profile_dialogue,null)

            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogView.confirmChangeBtn.setOnClickListener {

                updateProfile()
                dialog.dismiss()
            }
        }



    }

    fun fetchData() {
        val userEmail = sharedPreferences.getString("Email", "")
        val db = FirebaseFirestore.getInstance()
        val userName = findViewById<TextView>(R.id.userName)
        val userAge = findViewById<TextView>(R.id.userAge)
        val guideName = findViewById<TextView>(R.id.guideName)
        val guidePhone = findViewById<TextView>(R.id.guidePhone)
        db.collection("users")
            .get()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (userEmail.equals(document.id)){
                            userName.text =document.data.getValue("userName").toString()
                            userAge.text =document.data.getValue("userAge").toString()
                            guideName.text =document.data.getValue("guideName").toString()
                            guidePhone.text =document.data.getValue("phone").toString()

                        }

                    }
                }
            }
    }

    fun updateProfile(){


        val userEmail = sharedPreferences.getString("Email", "")

        val userName = dialogView.findViewById<TextView>(R.id.userNameEdit)
        val userAge = findViewById<TextView>(R.id.userAgeEdit)
        val guideName = findViewById<TextView>(R.id.guideNameEdit)
        val guidePhone = findViewById<TextView>(R.id.guidePhoneEdit)
        db.collection("users")
            .get()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (userEmail.equals(document.id)){

                            val user: MutableMap<String, Any> = HashMap()
                            user["userName"] = userName.text.toString()
                            user["userAge"] = userAge.text.toString()
                            user["guideName"] = guideName.text.toString()
                            user["guidePhone"] = guidePhone.text.toString()

                        }

                    }
                }
            }
    }
}

