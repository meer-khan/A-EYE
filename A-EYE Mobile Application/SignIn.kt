package com.muddasarajmal.aeye

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.muddasarajmal.aeye.databinding.ActivitySignInBinding
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var authetication: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        authetication = Firebase.auth

        sharedPreferences = getSharedPreferences("shared_PREF", Context.MODE_PRIVATE)
        val mainEditor = sharedPreferences.edit()

        var emailVerificationAllert = binding.emailVerificationAllert
        emailVerificationAllert.visibility = View.GONE

        binding.signInBtnId.setOnClickListener {

            hideKeypad()
            logInUser()

        }
        binding.forgetPasswordId.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))

        }
    }

    //   ----------------------------------------------- Login User --------------------------------------- //
    private fun logInUser() {
        var userEmail = binding.userEmailAddressId
        var userPassword = binding.userPasswordId

        if (userEmail.text.toString().isEmpty()) {
            userEmail.error = "Please enter email"
            userEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()) {
            userEmail.error = "Please enter valid email"
            userEmail.requestFocus()
            return
        }

        if (userPassword.text.toString().isEmpty()) {
            userPassword.error = "Please enter password"
            userPassword.requestFocus()
            return
        }

        authetication.signInWithEmailAndPassword(
            userEmail.text.toString(),
            userPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = authetication.currentUser

                    updateUI(currentUser, userEmail.text.toString())

                } else {
                    Toast.makeText(
                        baseContext, "Sign In failed. Try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?, emailAdd: String) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("Email", emailAdd)
                startActivity(intent)
                saveUserState(emailAdd)
                finish()
                Toast.makeText(
                    baseContext, "Sign In Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                emailVerificationAllert.visibility = View.VISIBLE
                emailVerificationAllert.setText("Please Verify your Email First")
            }
        }

    }

    fun hideKeypad() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun saveUserState(userEmail: String) {
        val db = FirebaseFirestore.getInstance()
        val editor = sharedPreferences.edit()
        editor.putString("Email", userEmail)
        editor.putString("Check", "true")
        editor.apply()
        Toast.makeText(
            baseContext, "Data is saved In shared Preferences Successfully",
            Toast.LENGTH_SHORT
        ).show()



    }
}

