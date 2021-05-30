package com.muddasarajmal.aeye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.muddasarajmal.aeye.databinding.ActivityForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {
    private lateinit var authetication: FirebaseAuth
    private lateinit var binding:ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        authetication = Firebase.auth
        binding.resetPasswordButton.setOnClickListener {
            resetPassword()
//            startActivity(Intent(this, SignIn::class.java))
    }

    }
    private fun resetPassword(){
        var emailAddress = binding.emailAddress
        if (emailAddress.text.toString().isEmpty()) {
            emailAddress.error = "Please enter email"
            emailAddress.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress.text.toString()).matches()) {
            emailAddress.error = "Please enter valid email"
            emailAddress.requestFocus()
            return
        }
        authetication.sendPasswordResetEmail(emailAddress.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = authetication.currentUser
                    Toast.makeText(
                        this, "Email Sent",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this,SignIn::class.java))

                } else{
                    Toast.makeText(
                        this, "Invalide Email Address",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }
}