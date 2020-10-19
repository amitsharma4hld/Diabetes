package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.MainActivity
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        dashboard_webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == "http://35.194.73.39/webview/logout.php") {
                    shouldOverrideUrlLoading()
                return false
            }
                return false
            }
        }
        dashboard_webview.loadUrl("http://35.194.73.39/webview/dashboard/?doctor_id=${prefs.getSavedDoctorId()}")
        dashboard_webview.settings.javaScriptEnabled = true

    }
    fun shouldOverrideUrlLoading() {
            Coroutines.io{
                AppDatabase(this).clearAllTables()
                prefs.cearAllSharedPref()
                this?.let{
                    val intent = Intent(it, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    it.startActivity(intent)
                }
            }
    }
}