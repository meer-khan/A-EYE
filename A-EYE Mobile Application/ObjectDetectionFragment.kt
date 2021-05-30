package com.muddasarajmal.aeye

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class ObjectDetectionFragment : Fragment() {
    private lateinit var mainClass:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_object_detection, container, false)
        mainClass.setDrawer()
    }
//    override fun onSupportNavigateUp(): Boolean {
//        val myNavHostFragment = mainClass.myNavHostFragment
//        val drawerLayout = mainClass.drawer_layout
//        val navController = myNavHostFragment
//        return NavigationUI.navigateUp(navController,drawerLayout)
//    }
}