package com.tamertokbaev.momari

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class MyJsInterface(private val mContext: Context, private val token: String) {

    @JavascriptInterface
    fun showUserInformation(UserInfo: String) {
        Toast.makeText(mContext, UserInfo, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun getToken(): String {
        return token
    }
}