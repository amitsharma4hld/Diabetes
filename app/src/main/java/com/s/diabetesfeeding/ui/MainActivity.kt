package com.s.diabetesfeeding.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.ui.auth.AuthListener
import com.s.diabetesfeeding.ui.auth.AuthViewModel
import com.s.diabetesfeeding.ui.auth.AuthViewModelFactory
import com.s.diabetesfeeding.ui.auth.LoginActivity
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.setFullScreen
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(window)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener = this
        viewModel.getLoggedInUser().observe(this, Observer { data ->
            if (data != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
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

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(data: Data) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onVerificationFailed(title: String, message: String) {
        TODO("Not yet implemented")
    }
}