package com.tamertokbaev.momari.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tamertokbaev.momari.MyJsInterface
import com.tamertokbaev.momari.R
import com.tamertokbaev.momari.globals.Constants


class HomeActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: BottomNavigationView = findViewById(R.id.navigation)
        val navController = findNavController(findViewById(R.id.nav_host_fragment_activity_guest))

        navigationView.setupWithNavController(navController)
        navigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
//                    Toast.makeText(this, webView.toString(), Toast.LENGTH_SHORT).show()
                    webView?.loadUrl("http://10.0.2.2:3000/profile")
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_catalog -> {
                    webView?.loadUrl("http://10.0.2.2:3000")
                    navController.navigate(R.id.navigation_catalog)
                    true
                }
                R.id.navigation_checkout -> {
                    webView?.loadUrl("http://10.0.2.2:3000/playlists")
                    navController.navigate(R.id.navigation_checkout)
                    true
                }
                R.id.navigation_books -> {
                    webView?.loadUrl("http://10.0.2.2:3000/last-released")
                    navController.navigate(R.id.navigation_books)
                    true
                }
                else -> false
            }
        }
        val preferences = getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

        webView = findViewById<WebView>(R.id.webViewMain)
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        // this will load the url of the website
        webView.loadUrl("http://10.0.2.2:3000")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(MyJsInterface(this, bearerToken!!), "Android")

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        // If the WebView can go back, go back; otherwise, exit the activity
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    fun signOut(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }


}