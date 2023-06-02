package com.tamertokbaev.momari

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient

class MyWebChromeClient : WebChromeClient() {
    override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
        Log.d("WebView", "${consoleMessage.message()} -- From line " +
                "${consoleMessage.lineNumber()} of ${consoleMessage.sourceId()}")
        return true
    }
}