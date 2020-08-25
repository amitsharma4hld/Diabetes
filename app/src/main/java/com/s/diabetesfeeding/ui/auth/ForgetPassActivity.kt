package com.s.diabetesfeeding.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.activity_forget_pass.*

class ForgetPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_pass)
        bt_continue.setOnClickListener {
            val itn = Intent(this@ForgetPassActivity, LoginActivity::class.java)
            startActivity(itn)
        }
    }
}