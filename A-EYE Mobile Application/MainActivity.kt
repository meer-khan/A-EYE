package com.muddasarajmal.aeye

import android.content.ClipData
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.muddasarajmal.aeye.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var authetication: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navigationView = binding.navView
        // Shared Preferences for current user state
        sharedPreferences = getSharedPreferences("shared_PREF", Context.MODE_PRIVATE)

        db = FirebaseFirestore.getInstance()

        fetchData()
        // intent get from signIn to display email on drawer header
//        val intent = intent
//        val logedInUserEmail = intent.getStringExtra("Email")


        setDrawer()
        setBottomNavigation()

    }
//    public override fun onStart() {
//        super.onStart()
//        val currentUser = authetication.currentUser
//
//    }

    //   ----------------------------------------------- Setting Drawer --------------------------------------- //
    fun setDrawer() {
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView, navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)


        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHistory -> startActivity(Intent(this, History::class.java))
                R.id.navTakeADemo -> startActivity(Intent(this, Demo::class.java))
                R.id.navAbout -> startActivity(Intent(this, AboutUs::class.java))
                R.id.navSetting -> startActivity(Intent(this, Setting::class.java))
                R.id.navSignIn ->
                    startActivity(Intent(this, SignIn::class.java))
                R.id.navLogout -> {
                    logOut()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //   ----------------------------------------------- Setting Bottom Bar --------------------------------------- //

    fun setBottomNavigation() {
        bottomNav = binding.bottomBarNavigation
        setupBottomNavigation()
    }




    //   ----------------------------------------------- Setting Back Button to toggle --------------------------------------- //
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }




    //   ----------------------------------------------- Setting Bottom NAvigations --------------------------------------- //
    private fun setupBottomNavigation() {
        val navController = this.findNavController(R.id.myNavHostFragment)
        bottomNav.setupWithNavController(navController)
    }


    //   ----------------------------------------------- Fetching Data from shared preferences--------------------------------------- //

    fun fetchData() {
        val userEmail = sharedPreferences.getString("Email", "")
        val headerView = navigationView.getHeaderView(0)
        val userName = headerView.findViewById<TextView>(R.id.userName)
        val guideName = headerView.findViewById<TextView>(R.id.guideName)
        val currentUserEmail = headerView.findViewById<TextView>(R.id.guardianEmail)
        currentUserEmail.setText(userEmail)
        db.collection("users")
            .get()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if (userEmail.equals(document.id)){
                            userName.text =document.data.getValue("userName").toString()
                            guideName.text =document.data.getValue("guideName").toString()


                        }

                    }
                }
            }

    }

    fun logOut() {
        val editor = sharedPreferences.edit()
        Firebase.auth.signOut()
        startActivity(Intent(this, SignIn::class.java))
        editor.clear()
        editor.apply()
        Toast.makeText(this, "User loged out successfully", Toast.LENGTH_SHORT).show()

//        val editor = sharedPreferences.edit()

//
////        startActivity(Intent(this, SignIn::class.java))
//
//
//        var user = authetication.currentUser;
//        if (user != null) {
//            Firebase.auth.signOut()
//            startActivity(Intent(this, SignIn::class.java))
//            Toast.makeText(this, "Log Out Successfully", Toast.LENGTH_SHORT).show()
////            editor.clear()
////            editor.apply()
//            finish()
//        } else {
//            Toast.makeText(this, "User not loged in", Toast.LENGTH_SHORT).show()
//        }
    }
}