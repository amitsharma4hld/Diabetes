package com.s.diabetesfeeding.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.auth.LoginActivity
import com.s.diabetesfeeding.util.setFullScreen
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
        setContentView(R.layout.activity_main)

        login_circleone.setOnClickListener {
            wholeView.setBackgroundColor(Color.parseColor("#427182"))
            imageView.setImageDrawable(resources.getDrawable(R.drawable.db_br_login));
            text.text = getString(R.string.diabetes_breastfeeding_app)

        }

        login_circletwo.setOnClickListener {
            wholeView.setBackgroundColor(Color.parseColor("#B48B76"))
            imageView.setImageDrawable(resources.getDrawable(R.drawable.diabetesbg));
            text.text = getString(R.string.diabetes)

        }

        login_circlethree.setOnClickListener {
            wholeView.setBackgroundColor(Color.parseColor("#E58E6E"))
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ongynbg));
            text.text = getString(R.string.obgyn)

        }

        login_circlefour.setOnClickListener {
            wholeView.setBackgroundColor(Color.parseColor("#EEC54A"))
            imageView.setImageDrawable(resources.getDrawable(R.drawable.breastfeedingbg));
            text.text = getString(R.string.breastfeeding)
            login.visibility= View.VISIBLE

        }
        login.setOnClickListener {
            val itn = Intent(this, LoginActivity::class.java)
            startActivity(itn)
        }
    }
}