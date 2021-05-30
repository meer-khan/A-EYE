package com.muddasarajmal.aeye

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_demo.*

class Demo : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        webViewSetup()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun  webViewSetup(){
       webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl("https://www.youtube.com/watch?v=mN08CPrTKBw")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}