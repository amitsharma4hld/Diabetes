package com.s.diabetesfeeding.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.databinding.ActivityLoginBinding
import com.s.diabetesfeeding.ui.home.HomeActivity
import com.s.diabetesfeeding.util.*
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {
    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
            val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { data ->
            if (data != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        tv_forgetpass.setOnClickListener {
            val itn = Intent(this@LoginActivity, ForgetPassActivity::class.java)
            startActivity(itn)
        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onVerificationFailed(title: String, message: String) {
        alertDialog(title,message)
    }
    override fun onSuccess(data: Data) {
        progress_bar.hide()
      /* root_layout.snackbar("${data.display_name} is Logged in")
        val itn = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(itn)
        finish()*/
    }
    @SuppressLint("ResourceAsColor")
    override fun onFailure(message: String) {
        root_layout.snackbar("$message")
        //mc_username.strokeColor =  Color.parseColor("#000000")
        //mc_username.strokeWidth = 10
        progress_bar.hide()
    }
}