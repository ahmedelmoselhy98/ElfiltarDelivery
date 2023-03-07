package com.elfiltar.elfiltartechnician.commons.helpers

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient


class ClientWebView(val context: Context?, val sec: Int, val callbackUrl: String?): WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (url.contains(callbackUrl!!)) {
            val handler = Handler()
            handler.postDelayed(Runnable {
                (context as Activity?)!!.setResult(Activity.RESULT_OK)
                (context as Activity?)!!.finish()
            }, (sec * 1000).toLong()) //5 sec
        }
    }
}