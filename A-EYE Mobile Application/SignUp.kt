package com.muddasarajmal.aeye

import   android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.ContextMenu
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.muddasarajmal.aeye.databinding.ActivitySignUpBinding


class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authetication: FirebaseAuth
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        authetication = Firebase.auth

        binding.signUpButtonId.setOnClickListener {
            signUpUser()
            // hide keypad
            hideKeypad()
        }
        binding.signInText.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

    }

    //   ----------------------------------------------- Creating User --------------------------------------- //
    private fun signUpUser() {
        val userName = binding.userNameId
        val userAge = binding.userAgeId
        val guideName = binding.guideNameId
        val guidePhone = binding.guidePhoneId
        val guideEmail = binding.guideEmailId
        var userPassword = binding.passwordId
        var confirmPassword = binding.confirmPasswordId

        if (userName.text.toString().isEmpty()) {
            userName.error = "Please enter user name"
            userName.requestFocus()
            return
        }
        if (userAge.text.toString().isEmpty()) {
            userAge.error = "Please enter the user age"
            userAge.requestFocus()
            return
        }
        if (guideName.text.toString().isEmpty()) {
            guideName.error = "Please enter the guide name"
            guideName.requestFocus()
            return
        }
        if (!Patterns.PHONE.matcher(guidePhone.text.toString()).matches()) {
            guidePhone.error = "Please enter the guide phone"
            guidePhone.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(guideEmail.text.toString()).matches()) {
            guideEmail.error = "Please enter valid email"
            guideEmail.requestFocus()
            return
        }

        if (userPassword.text.toString().isEmpty()) {
            userPassword.error = "Please enter password"
            userPassword.requestFocus()
            return
        }
        if (!userPassword.text.toString().equals(confirmPassword.text.toString())) {
            confirmPassword.error = "Password doesn't match"
            confirmPassword.requestFocus()
            return
        }

        authetication.createUserWithEmailAndPassword(
            guideEmail.text.toString(),
            userPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    authetication.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                saveData(
                                    userName.text.toString(),
                                    userAge.text.toString(),
                                    guideName.text.toString(),
                                    guidePhone.text.toString(),
                                    guideEmail.text.toString()
                                )
                                Toast.makeText(
                                baseContext, "Verification Email link is sent",
                                Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, SignIn::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    //   ----------------------------------------------- Saving Data to Cloud Database --------------------------------------- //
    fun saveData(
        userName: String,
        userAge: String,
        guideName: String,
        guidePhone: String,
        guideEmail: String

    ) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["userName"] = userName
        user["userAge"] = userAge
        user["guideName"] = guideName
        user["phone"] = guidePhone

        db.collection("users")
            // given line of code add the record with the id of guideEmail
            .document(guideEmail)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Data is saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Data is saved successfully", Toast.LENGTH_SHORT).show()
            }
    }

    //   ----------------------------------------------- hiding Keypad --------------------------------------- //
    fun hideKeypad() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}